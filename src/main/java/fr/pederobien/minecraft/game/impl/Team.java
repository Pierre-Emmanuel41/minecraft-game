package fr.pederobien.minecraft.game.impl;

import java.util.ArrayList;
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
import fr.pederobien.minecraft.game.event.PlayerListPlayerAddPostEvent;
import fr.pederobien.minecraft.game.event.PlayerListPlayerRemovePostEvent;
import fr.pederobien.minecraft.game.event.TeamColorChangePostEvent;
import fr.pederobien.minecraft.game.event.TeamEvent;
import fr.pederobien.minecraft.game.event.TeamNameChangePostEvent;
import fr.pederobien.minecraft.game.interfaces.IPlayerList;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.managers.MessageManager;
import fr.pederobien.minecraft.managers.TeamManager;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class Team implements ITeam, IEventListener, ICodeSender {
	private String name;
	private EColor color;
	private Lock lock;
	private IPlayerList players;
	private List<Player> quitPlayers;
	private boolean clone, isCreatedOnServer;
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
		players = new PlayerList(name);
		quitPlayers = new ArrayList<Player>();

		PlayerQuitOrJoinEventHandler.instance().registerQuitEventHandler(this, event -> onPlayerQuitEvent(event));
		PlayerQuitOrJoinEventHandler.instance().registerJoinEventHandler(this, event -> onPlayerJoinEvent(event));
		EventManager.registerListener(this);
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
	public String getColoredName(EColor next) {
		return getColor().getInColor(getName(), next);
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
		synchronizeWithServerTeam(team -> team.setColor(color.getChatColor()), new TeamColorChangePostEvent(this, oldColor));
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
	public IPlayerList getPlayers() {
		return players;
	}

	@Override
	public ITeam clone() {
		ITeam team = new Team(getName(), getColor(), true);
		for (Player player : getPlayers())
			team.getPlayers().add(player);
		return team;
	}

	@Override
	public void createOnserver() {
		if (isCreatedOnServer)
			return;

		team = TeamManager.createTeam(getName(), getColor().getChatColor(), getPlayers().stream());
		isCreatedOnServer = true;
	}

	@Override
	public void removeFromServer() {
		if (!isCreatedOnServer)
			return;

		TeamManager.removeTeam(team.getName());
		team = null;
		isCreatedOnServer = false;
	}

	@Override
	public boolean isCreatedOnServer() {
		return isCreatedOnServer;
	}

	@Override
	public Optional<org.bukkit.scoreboard.Team> getServerTeam() {
		return TeamManager.getTeam(getName());
	}

	@Override
	public String toString() {
		StringJoiner players = new StringJoiner(" ", "[", "]");
		for (Player player : getPlayers())
			players.add(player.getName());
		return getColor().getInColor(getName() + " " + players.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof ITeam))
			return false;

		ITeam other = (ITeam) obj;
		if (!name.equals(other.getName()))
			return false;

		if (getPlayers().toList().size() != other.getPlayers().toList().size())
			return false;

		List<Player> myPlayers = getPlayers().toList(), otherPlayers = other.getPlayers().toList();
		myPlayers.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
		otherPlayers.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
		for (int i = 0; i < getPlayers().toList().size(); i++)
			if (!myPlayers.get(i).equals(otherPlayers.get(i)))
				return false;

		return true;
	}

	@EventHandler
	private void onPlayerAdd(PlayerListPlayerAddPostEvent event) {
		if (!event.getList().equals(players))
			return;

		updateServerTeam(team -> team.addEntry(event.getPlayer().getName()));
	}

	@EventHandler
	private void onPlayerRemove(PlayerListPlayerRemovePostEvent event) {
		if (!event.getList().equals(players))
			return;

		updateServerTeam(team -> team.removeEntry(event.getPlayer().getName()));
	}

	/**
	 * Update the server team with the consumer <code>team</code>.
	 * 
	 * @param consumer The consumer used to update the server team.
	 */
	private void updateServerTeam(Consumer<org.bukkit.scoreboard.Team> consumer) {
		if (isCreatedOnServer() && !clone)
			consumer.accept(team);
	}

	/**
	 * Update the server team with the consumer <code>team</code> and throw the given event.
	 * 
	 * @param consumer The consumer used to update the server team.
	 * @param event    The event to throw in order to notify observers.
	 */
	private void synchronizeWithServerTeam(Consumer<org.bukkit.scoreboard.Team> consumer, TeamEvent event) {
		updateServerTeam(consumer);
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
				getPlayers().add(event.getPlayer());
			}
		}
	}
}
