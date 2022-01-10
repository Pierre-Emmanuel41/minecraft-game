package fr.pederobien.minecraft.game.commands.game;

import java.util.function.Supplier;

import fr.pederobien.minecraft.game.commands.team.TeamCommandTree;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;

public class TeamsAddNode extends TeamsNode {
	private TeamsAddTeamNode teamNode;
	private TeamsAddPlayerNode playerNode;

	/**
	 * Creates a node that adds team in a team list.
	 * 
	 * @param teams    The list of teams associated to this node.
	 * @param teamTree The command tree in order create or modify a team.
	 */
	protected TeamsAddNode(Supplier<ITeamConfigurable> teams, TeamCommandTree teamTree) {
		super(teams, "add", EGameCode.GAME_CONFIG__TEAMS_ADD__EXPLANATION, t -> t != null && t.getTeams() != null);
		add(teamNode = new TeamsAddTeamNode(teams, teamTree));
		add(playerNode = new TeamsAddPlayerNode(teams, teamTree));
	}

	/**
	 * @return The node that adds a team to the list of teams.
	 */
	public TeamsAddTeamNode getTeamNode() {
		return teamNode;
	}

	/**
	 * @return The node that adds players to a team.
	 */
	public TeamsAddPlayerNode getPlayerNode() {
		return playerNode;
	}
}
