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
import java.util.stream.Stream;

import fr.pederobien.minecraft.game.event.TeamListTeamAddPostEvent;
import fr.pederobien.minecraft.game.event.TeamListTeamRemovePostEvent;
import fr.pederobien.minecraft.game.exceptions.TeamAlreadyRegisteredException;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamList;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.utils.event.EventManager;

public class TeamList implements ITeamList {
	private String name;
	private Map<String, ITeam> teams;
	private Lock lock;

	public TeamList(String name) {
		this.name = name;
		teams = new LinkedHashMap<String, ITeam>();
	}

	@Override
	public Iterator<ITeam> iterator() {
		return teams.values().iterator();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ITeam create(String name, EColor color, boolean add) {
		return null;
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
				team.clear();
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
	public Stream<ITeam> stream() {
		return teams.values().stream();
	}

	@Override
	public List<ITeam> toList() {
		return new ArrayList<ITeam>(teams.values());
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
			return teams.remove(name);
		} finally {
			lock.unlock();
		}
	}
}
