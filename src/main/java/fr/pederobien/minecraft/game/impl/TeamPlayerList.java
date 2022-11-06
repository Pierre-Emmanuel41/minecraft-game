package fr.pederobien.minecraft.game.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.event.TeamPlayerListPlayerAddPostEvent;
import fr.pederobien.minecraft.game.event.TeamPlayerListPlayerAddPreEvent;
import fr.pederobien.minecraft.game.event.TeamPlayerListPlayerRemovePostEvent;
import fr.pederobien.minecraft.game.event.TeamPlayerListPlayerRemovePreEvent;
import fr.pederobien.minecraft.game.exceptions.TeamPlayerAlreadyRegisteredException;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamPlayerList;
import fr.pederobien.utils.event.EventManager;

public class TeamPlayerList extends PlayerList<ITeam> implements ITeamPlayerList {

	/**
	 * Creates a player associated to the given team.
	 * 
	 * @param team The team to which this players list is associated.
	 */
	public TeamPlayerList(ITeam team) {
		super(team, team.getName());
	}

	@Override
	public void add(Player player) {
		getLock().lock();
		try {
			if (getPlayers().get(player.getName()) != null)
				throw new TeamPlayerAlreadyRegisteredException(this, player);

			Runnable update = () -> getPlayers().put(player.getName(), player);
			EventManager.callEvent(new TeamPlayerListPlayerAddPreEvent(this, player), update, new TeamPlayerListPlayerAddPostEvent(this, player));
		} finally {
			getLock().unlock();
		}
	}

	@Override
	public boolean remove(Player player) {
		getLock().lock();
		try {
			if (getPlayers().get(player.getName()) == null)
				return false;

			Runnable update = () -> getPlayers().remove(player.getName());
			TeamPlayerListPlayerRemovePreEvent preEvent = new TeamPlayerListPlayerRemovePreEvent(this, player);
			EventManager.callEvent(preEvent, update, new TeamPlayerListPlayerRemovePostEvent(this, player));

			return !preEvent.isCancelled();
		} finally {
			getLock().unlock();
		}
	}

	@Override
	public Player remove(String name) {
		getLock().lock();
		try {
			Optional<Player> optPlayer = getPlayer(name);
			if (!optPlayer.isPresent())
				return null;

			Runnable update = () -> getPlayers().remove(optPlayer.get().getName());
			TeamPlayerListPlayerRemovePreEvent preEvent = new TeamPlayerListPlayerRemovePreEvent(this, optPlayer.get());
			EventManager.callEvent(preEvent, update, new TeamPlayerListPlayerRemovePostEvent(this, optPlayer.get()));

			return preEvent.isCancelled() ? null : optPlayer.get();
		} finally {
			getLock().unlock();
		}
	}

	@Override
	public void clear() {
		getLock().lock();
		try {
			Set<String> names = new HashSet<String>(getPlayers().keySet());
			for (String name : names)
				EventManager.callEvent(new TeamPlayerListPlayerRemovePostEvent(this, getPlayers().remove(name)));
		} finally {
			getLock().unlock();
		}
	}
}
