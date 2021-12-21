package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class TeamListTeamRemovePostEvent extends TeamListEvent {
	private ITeam team;

	/**
	 * Creates an event thrown when a team has been removed from a teams list.
	 * 
	 * @param lite The list from which a team has been removed.
	 * @param team The removed team.
	 */
	public TeamListTeamRemovePostEvent(ITeamList list, ITeam team) {
		super(list);
		this.team = team;
	}

	/**
	 * @return The removed team.
	 */
	public ITeam getTeam() {
		return team;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("list=" + getList().getName());
		joiner.add("team=" + team.getName());
		return String.format("%s_%s", getName(), joiner.toString());
	}
}
