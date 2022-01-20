package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.game.interfaces.ITeam;

public class TeamNameChangePostEvent extends TeamEvent {
	private String oldName;

	/**
	 * Creates a event when the name of a team has changed.
	 * 
	 * @param team    The team whose the name has changed.
	 * @param oldName The old team name.
	 */
	public TeamNameChangePostEvent(ITeam team, String oldName) {
		super(team);
		this.oldName = oldName;
	}

	/**
	 * @return The old team name.
	 */
	public String getOldName() {
		return oldName;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("oldName=" + getOldName());
		joiner.add("newName=" + getTeam().getName());
		return String.format("%s_%s", getName(), joiner.toString());
	}
}
