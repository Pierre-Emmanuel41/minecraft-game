package fr.pederobien.minecraft.game.commands.teams;

import java.util.function.Supplier;

import fr.pederobien.minecraft.game.commands.team.TeamCommandTree;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class TeamsRemoveNode extends TeamsNode {
	private TeamsRemoveTeamNode teamNode;
	private TeamsRemovePlayerNode playerNode;

	/**
	 * Creates a node in order to remove teams or players from a team.
	 * 
	 * @param teams    The list of teams associated to this node.
	 * @param teamTree The command tree in order create or modify a team.
	 */
	protected TeamsRemoveNode(Supplier<ITeamList> teams, TeamCommandTree teamTree) {
		super(teams, "remove", EGameCode.TEAMS__REMOVE__EXPLANATION, t -> t != null);

		add(teamNode = new TeamsRemoveTeamNode(teams));
		add(playerNode = new TeamsRemovePlayerNode(teams, teamTree));
	}

	/**
	 * @return The node that removes a team from a team list.
	 */
	public TeamsRemoveTeamNode getTeamNode() {
		return teamNode;
	}

	/**
	 * @return The node that removes players from a team.
	 */
	public TeamsRemovePlayerNode getPlayerNode() {
		return playerNode;
	}
}
