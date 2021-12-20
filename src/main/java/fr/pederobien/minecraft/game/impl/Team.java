package fr.pederobien.minecraft.game.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.event.TeamColorChangePostEvent;
import fr.pederobien.minecraft.game.event.TeamEvent;
import fr.pederobien.minecraft.game.event.TeamNameChangePostEvent;
import fr.pederobien.minecraft.game.event.TeamPlayerAddPostEvent;
import fr.pederobien.minecraft.game.event.TeamPlayerRemovePostEvent;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.managers.MessageManager;
import fr.pederobien.minecraft.managers.TeamManager;
import fr.pederobien.utils.event.EventManager;

public class Team implements ITeam, ICodeSender {
	private String name;
	private EColor color;
	private Lock lock;
	private List<Player> players, quitPlayers;
	private boolean clone;
	private org.bukkit.scoreboard.Team team;

	/**
	 * Creates a team with the give name and the specified color. The color cannot be null.
	 * 
	 * @param name  The name of the team.
	 * @param color The color of the team.
	 * 
	 * @return The created team.
	 */
	public static ITeam of(String name, EColor color) {
		return new Team(name, color, false);
	}

	/**
	 * Creates a team with the given name, its color is {@link EColor#RESET}.
	 * 
	 * @param name The name of the team.
	 * 
	 * @return The created team.
	 * 
	 * @see #of(String, EColor)
	 */
	public static ITeam of(String name) {
		return of(name, EColor.RESET);
	}

	private Team(String name, EColor color, boolean clone) {
		this.name = name;
		this.color = color;
		this.clone = clone;

		lock = new ReentrantLock(true);
		players = new ArrayList<Player>();
		quitPlayers = new ArrayList<Player>();

		PlayerQuitOrJoinEventHandler.instance().registerQuitEventHandler(this, event -> onPlayerQuitEvent(event));
		PlayerQuitOrJoinEventHandler.instance().registerJoinEventHandler(this, event -> onPlayerJoinEvent(event));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		if (this.name == name)
			return;

		String oldName = this.name;
		this.name = name;
		synchronizeWithServerTeam(team -> team.setDisplayName(name), new TeamNameChangePostEvent(this, oldName));
	}

	@Override
	public String getColoredName() {
		return getColor().getInColor(getName());
	}

	@Override
	public EColor getColor() {
		return color;
	}

	@Override
	public void setColor(EColor color) {
		if (this.color == color)
			return;

		EColor oldColor = this.color;
		this.color = color;
		synchronizeWithServerTeam(team -> team.setDisplayName(name), new TeamColorChangePostEvent(this, oldColor));
	}

	@Override
	public void add(Player player) {
		addPlayer(player);
		synchronizeWithServerTeam(team -> team.addEntry(player.getName()), new TeamPlayerAddPostEvent(this, player));
	}

	@Override
	public void remove(Player player) {
		removePlayer(player);
		synchronizeWithServerTeam(team -> team.addEntry(player.getName()), new TeamPlayerRemovePostEvent(this, player));
	}

	@Override
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	@Override
	public void sendMessage(Player sender, String message) {
		for (Player player : players)
			MessageManager.sendMessage(player, getPrefix(sender, player) + message);
	}

	@Override
	public void sendMessage(Player sender, IMinecraftCode code, Object... args) {
		for (Player player : players)
			MessageManager.sendMessage(player, getPrefix(sender, player) + getMessage(player, code, args));
	}

	@Override
	public void clear() {
		for (Player player : players)
			synchronizeWithServerTeam(team -> team.removeEntry(player.getName()), new TeamPlayerRemovePostEvent(this, player));
		players.clear();
	}

	@Override
	public ITeam clone() {
		ITeam team = new Team(getName(), getColor(), true);
		for (Player player : getPlayers())
			team.add(player);
		return team;
	}

	@Override
	public boolean isCreatedOnServer() {
		Optional<org.bukkit.scoreboard.Team> optTeam = getServerTeam();
		if (optTeam.isPresent()) {
			team = optTeam.get();
			return true;
		}
		return false;
	}

	@Override
	public Optional<org.bukkit.scoreboard.Team> getServerTeam() {
		return TeamManager.getTeam(getName());
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",", "[", "]");
		for (Player player : players)
			joiner.add(player.getName());
		return String.format("name=%s, players=%s", getColoredName(), getColor().getInColor(joiner.toString()));
	}

	/**
	 * Update the server team with the consumer <code>team</code> and throw the given event.
	 * 
	 * @param consumer The consumer used to update the server team.
	 * @param event    The event to throw in order to notify observers.
	 */
	private void synchronizeWithServerTeam(Consumer<org.bukkit.scoreboard.Team> consumer, TeamEvent event) {
		if (team != null && isCreatedOnServer() && !clone)
			consumer.accept(team);
		EventManager.callEvent(event);
	}

	private String getPrefix(Player sender, Player player) {
		String playerName = player.equals(sender) ? getMessage(sender, EGameCode.GAME__TEAM__PLAYER_NAME) : sender.getName();
		return color.getInColor(String.format("[%s -> %s] ", playerName, getName()));
	}

	private void onPlayerQuitEvent(PlayerQuitEvent event) {
		lock.lock();
		try {
			Iterator<Player> iterator = players.iterator();
			while (iterator.hasNext()) {
				Player player = iterator.next();
				if (player.getName().equals(event.getPlayer().getName())) {
					quitPlayers.add(player);
					iterator.remove();
				}
			}
		} finally {
			lock.unlock();
		}
	}

	private void onPlayerJoinEvent(PlayerJoinEvent event) {
		Iterator<Player> iterator = quitPlayers.iterator();
		while (iterator.hasNext()) {
			Player player = iterator.next();
			if (player.getName().equals(event.getPlayer().getName())) {
				iterator.remove();
				add(event.getPlayer());
			}
		}
	}

	/**
	 * Thread safe operation that adds a player to the list of players.
	 * 
	 * @param player The player to add.
	 */
	private void addPlayer(Player player) {
		lock.lock();
		try {
			players.add(player);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Thread safe operation that removes a player from the list of players.
	 * 
	 * @param player The player to remove.
	 */
	private void removePlayer(Player player) {
		lock.lock();
		try {
			players.remove(player);
		} finally {
			lock.unlock();
		}
	}
}
