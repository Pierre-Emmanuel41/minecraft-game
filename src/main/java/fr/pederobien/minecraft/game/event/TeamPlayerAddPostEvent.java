package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.interfaces.ITeam;

public class TeamPlayerAddPostEvent extends TeamEvent {
	private Player player;

	/**
	 * Creates an event thrown when a player has been added to a team.
	 * 
	 * @param team   The team to which a player has been added.
	 * @param player The added player.
	 */
	public TeamPlayerAddPostEvent(ITeam team, Player player) {
		super(team);
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
		joiner.add("team=" + getTeam().getName());
		joiner.add("player=" + player.getName());
		return String.format("%s_%s", getName(), joiner.toString());
	}
}
