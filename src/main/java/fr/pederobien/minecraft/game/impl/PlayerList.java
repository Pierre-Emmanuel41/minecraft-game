package fr.pederobien.minecraft.game.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.interfaces.IPlayerList;

public abstract class PlayerList<T> implements IPlayerList<T> {
	private T parent;
	private String name;
	private Map<String, Player> players;
	private Lock lock;

	/**
	 * Creates a list of players.
	 * 
	 * @param parent The object to which this players list is attached.
	 * @param name   The players list name.
	 */
	public PlayerList(T parent, String name) {
		this.parent = parent;
		this.name = name;
		players = new LinkedHashMap<String, Player>();
		lock = new ReentrantLock(true);
	}

	@Override
	public Iterator<Player> iterator() {
		return players.values().iterator();
	}

	@Override
	public T getParent() {
		return parent;
	}

	@Override
	public String getName() {
		return name;
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
	 * @return The lock associated to this list.
	 */
	protected Lock getLock() {
		return lock;
	}

	/**
	 * @return The underlying map that stores players.
	 */
	public Map<String, Player> getPlayers() {
		return players;
	}
}
