package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.commands.team.TeamCommandTree;
import fr.pederobien.minecraft.game.interfaces.ITeam;

public class TeamNewPostEvent extends TeamEvent {
	private TeamCommandTree tree;

	/**
	 * Creates an event when a team command tree has created a new team.
	 * 
	 * @param team The created team.
	 * @param tree The tree that has created the team.
	 */
	public TeamNewPostEvent(ITeam team, TeamCommandTree tree) {
		super(team);
		this.tree = tree;
	}

	/**
	 * @return The tree that has created the team.
	 */
	public TeamCommandTree getTree() {
		return tree;
	}
}
