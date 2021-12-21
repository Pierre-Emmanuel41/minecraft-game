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

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.event.PlayerListPlayerAddPostEvent;
import fr.pederobien.minecraft.game.event.PlayerListPlayerRemovePostEvent;
import fr.pederobien.minecraft.game.exceptions.PlayerAlreadyRegisteredException;
import fr.pederobien.minecraft.game.interfaces.IPlayerList;
import fr.pederobien.utils.event.EventManager;

public class PlayerList implements IPlayerList {
	private String name;
	private Map<String, Player> players;
	private Lock lock;

	public PlayerList(String name) {
		this.name = name;
		players = new LinkedHashMap<String, Player>();
	}

	@Override
	public Iterator<Player> iterator() {
		return players.values().iterator();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void add(Player player) {
		addPlayer(player);
		EventManager.callEvent(new PlayerListPlayerAddPostEvent(this, player));
	}

	@Override
	public Player remove(String name) {
		Player player = removePlayer(name);
		if (player != null)
			EventManager.callEvent(new PlayerListPlayerRemovePostEvent(this, player));
		return player;
	}

	@Override
	public boolean remove(Player player) {
		return remove(player.getName()) != null;
	}

	@Override
	public void clear() {
		lock.lock();
		try {
			Set<String> names = new HashSet<String>(players.keySet());
			for (String name : names)
				EventManager.callEvent(new PlayerListPlayerRemovePostEvent(this, players.remove(name)));
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Optional<Player> getPlayer(String name) {
		return Optional.ofNullable(players.get(name));
	}

	@Override
	public Stream<Player> stream() {
		return players.values().stream();
	}

	@Override
	public List<Player> toList() {
		return new ArrayList<Player>(players.values());
	}

	/**
	 * Thread safe operation that adds a player to the players list.
	 * 
	 * @param player The player to add.
	 * 
	 * @throws PlayerAlreadyRegisteredException if a player is already registered for the player name.
	 */
	private void addPlayer(Player player) {
		lock.lock();
		try {
			if (players.get(player.getName()) != null)
				throw new PlayerAlreadyRegisteredException(this, player);

			players.put(player.getName(), player);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Thread safe operation that removes a players from the players list.
	 * 
	 * @param player The player to remove.
	 * 
	 * @return The player associated to the given name if registered, null otherwise.
	 */
	private Player removePlayer(String name) {
		lock.lock();
		try {
			return players.remove(name);
		} finally {
			lock.unlock();
		}
	}
}
