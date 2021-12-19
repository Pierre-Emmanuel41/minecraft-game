package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.managers.EColor;

public class TeamColorChangePostEvent extends TeamEvent {
	private EColor oldColor;

	/**
	 * Creates an event thrown when the color of a team has changed.
	 * 
	 * @param team     The team whose the color has changed.
	 * @param oldColor The old team color.
	 */
	public TeamColorChangePostEvent(ITeam team, EColor oldColor) {
		super(team);
		this.oldColor = oldColor;
	}

	/**
	 * @return The old team color.
	 */
	public EColor getOldColor() {
		return oldColor;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("team=" + getTeam().getName());
		joiner.add("oldColor=" + oldColor);
		joiner.add("newColor=" + getTeam().getColor());
		return String.format("%s_%s", getName(), joiner.toString());
	}
}
