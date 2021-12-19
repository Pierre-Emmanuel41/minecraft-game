package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.interfaces.ITeam;

public class TeamPlayerRemovePostEvent extends TeamEvent {
	private Player player;

	/**
	 * Creates an event thrown when a player has been removed from a team.
	 * 
	 * @param team   The team from which a player has been removed.
	 * @param player The removed player.
	 */
	public TeamPlayerRemovePostEvent(ITeam team, Player player) {
		super(team);
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
		joiner.add("team=" + getTeam().getName());
		joiner.add("player=" + player.getName());
		return String.format("%s_%s", getName(), joiner.toString());
	}
}
