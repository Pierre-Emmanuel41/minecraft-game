package fr.pederobien.minecraft.game.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.event.TeamListTeamAddPostEvent;
import fr.pederobien.minecraft.game.event.TeamListTeamRemovePostEvent;
import fr.pederobien.minecraft.game.event.TeamNameChangePostEvent;
import fr.pederobien.minecraft.game.exceptions.TeamAlreadyRegisteredException;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamList;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class TeamList implements ITeamList, IEventListener {
	private IGame game;
	private Map<String, ITeam> teams;
	private Lock lock;

	/**
	 * Creates a list of teams associated to a game.
	 * 
	 * @param game The game associated to this list.
	 */
	public TeamList(IGame game) {
		this.game = game;
		teams = new LinkedHashMap<String, ITeam>();
		lock = new ReentrantLock(true);

		EventManager.registerListener(this);
	}

	@Override
	public Iterator<ITeam> iterator() {
		return teams.values().iterator();
	}

	@Override
	public String getName() {
		return game.getName();
	}

	@Override
	public void add(ITeam team) {
		addTeam(team);
		EventManager.callEvent(new TeamListTeamAddPostEvent(this, team));
	}

	@Override
	public ITeam remove(String name) {
		ITeam team = removeTeam(name);
		if (team != null)
			EventManager.callEvent(new TeamListTeamRemovePostEvent(this, team));
		return team;
	}

	@Override
	public boolean remove(ITeam team) {
		return remove(team.getName()) != null;
	}

	@Override
	public void clear() {
		lock.lock();
		try {
			Set<String> names = new HashSet<String>(teams.keySet());
			for (String name : names) {
				ITeam team = teams.remove(name);
				team.getPlayers().clear();
				EventManager.callEvent(new TeamListTeamRemovePostEvent(this, team));
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Optional<ITeam> getTeam(String name) {
		return Optional.ofNullable(teams.get(name));
	}

	@Override
	public Optional<ITeam> getTeam(EColor color) {
		lock.lock();
		try {
			for (ITeam team : teams.values())
				if (team.getColor().equals(color))
					return Optional.of(team);
			return Optional.empty();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Optional<ITeam> getTeam(Player player) {
		lock.lock();
		try {
			for (ITeam team : teams.values())
				if (team.getPlayers().toList().contains(player))
					return Optional.of(team);
			return Optional.empty();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Stream<ITeam> stream() {
		return teams.values().stream();
	}

	@Override
	public List<ITeam> toList() {
		return new ArrayList<ITeam>(teams.values());
	}

	@Override
	public ITeam movePlayer(Player player, ITeam team) {
		ITeam origin = null;
		for (ITeam t : teams.values())
			if (t.getPlayers().toList().contains(player)) {
				origin = t;
				break;
			}
		if (origin != null)
			origin.getPlayers().remove(player);

		team.getPlayers().add(player);
		return origin;
	}

	@EventHandler
	private void onTeamNameChange(TeamNameChangePostEvent event) {
		Optional<ITeam> optOldTeam = getTeam(event.getOldName());
		if (!optOldTeam.isPresent())
			return;

		Optional<ITeam> optNewTeam = getTeam(event.getTeam().getName());
		if (optNewTeam.isPresent())
			throw new TeamAlreadyRegisteredException(this, optNewTeam.get());

		lock.lock();
		try {
			teams.remove(event.getOldName());
			teams.put(event.getTeam().getName(), event.getTeam());
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Thread safe operation that adds a team to the teams list.
	 * 
	 * @param team The team to add.
	 * 
	 * @throws TeamAlreadyRegisteredException if a team is already registered for the team name.
	 */
	private void addTeam(ITeam team) {
		lock.lock();
		try {
			if (teams.get(team.getName()) != null)
				throw new TeamAlreadyRegisteredException(this, team);

			teams.put(team.getName(), team);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Thread safe operation that removes a teams from the teams list.
	 * 
	 * @param team The team to remove.
	 * 
	 * @return The team associated to the given name if registered, null otherwise.
	 */
	private ITeam removeTeam(String name) {
		lock.lock();
		try {
			ITeam team = teams.remove(name);
			if (team != null)
				team.getPlayers().clear();
			return team;
		} finally {
			lock.unlock();
		}
	}
}
