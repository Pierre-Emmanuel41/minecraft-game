package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class TeamListTeamAddPostEvent extends TeamListEvent {
	private ITeam team;

	/**
	 * Creates an event thrown when a team has been added to a teams list.
	 * 
	 * @param list The list to which a team has been added.
	 * @param team The added team.
	 */
	public TeamListTeamAddPostEvent(ITeamList list, ITeam team) {
		super(list);
		this.team = team;
	}

	/**
	 * @return The added team.
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
