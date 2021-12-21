package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.interfaces.IPlayerList;

public class PlayerListPlayerRemovePostEvent extends PlayerListEvent {
	private Player player;

	/**
	 * Creates an event thrown when a player has been removed from a players list.
	 * 
	 * @param list   The list from which a player has been removed.
	 * @param player The removed player.
	 */
	public PlayerListPlayerRemovePostEvent(IPlayerList list, Player player) {
		super(list);
	}

	/**
	 * @return The removed player.
	 */
	public Player getPlayer() {
		return player;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("list=" + getList().getName());
		joiner.add("player=" + player.getName());
		return String.format("%s_%s", getName(), joiner.toString());
	}
}
