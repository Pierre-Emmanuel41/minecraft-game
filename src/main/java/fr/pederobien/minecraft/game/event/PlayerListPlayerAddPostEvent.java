package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.interfaces.IPlayerList;

public class PlayerListPlayerAddPostEvent extends PlayerListEvent {
	private Player player;

	/**
	 * Creates an event thrown when a player has been added to a players list.
	 * 
	 * @param list   The list to which a player has been added.
	 * @param player The added player.
	 */
	public PlayerListPlayerAddPostEvent(IPlayerList list, Player player) {
		super(list);
		this.player = player;
	}

	/**
	 * @return The added player.
	 */
	public Player getPlayer() {
		return player;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("list=" + getList().getName());
		joiner.add("player=" + getPlayer().getName());
		return String.format("%s_%s", getName(), joiner);
	}
}
