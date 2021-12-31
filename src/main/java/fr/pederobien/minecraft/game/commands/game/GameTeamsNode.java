package fr.pederobien.minecraft.game.commands.game;

import fr.pederobien.minecraft.game.commands.team.TeamCommandTree;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;

public class GameTeamsNode extends TeamsNode {
	private TeamCommandTree teamTree;
	private TeamsAddNode addNode;
	private TeamsListNode listNode;
	private TeamsRemoveNode removeNode;
	private TeamsModifyNode modifyNode;
	private TeamsRandomNode randomNode;
	private TeamsMoveNode moveNode;

	/**
	 * Creates a node in order to modify a team configurable object.
	 * 
	 * @param configurable The configurable object associated to this node.
	 */
	protected GameTeamsNode(ITeamConfigurable configurable) {
		super(configurable.getTeams(), "teams", EGameCode.GAME_CONFIG__TEAMS__EXPLANATION);

		teamTree = new TeamCommandTree();
		add(addNode = new TeamsAddNode(getTeams(), teamTree));
		add(listNode = TeamsListNode.newInstance(getTeams()));
		add(removeNode = new TeamsRemoveNode(getTeams(), teamTree));
		add(modifyNode = new TeamsModifyNode(getTeams(), teamTree));
		add(randomNode = new TeamsRandomNode(getTeams()));
		add(moveNode = new TeamsMoveNode(getTeams()));
	}

	/**
	 * @return The node that adds team to the list of teams.
	 */
	public TeamsAddNode getAddNode() {
		return addNode;
	}

	/**
	 * @return The node that displays the characteristics of each team of the list of teams.
	 */
	public TeamsListNode getListNode() {
		return listNode;
	}

	/**
	 * @return The node that removes teams from the list of teams.
	 */
	public TeamsRemoveNode getRemoveNode() {
		return removeNode;
	}

	/**
	 * @return The node that modifies the characteristics of a team.
	 */
	public TeamsModifyNode getModifyNode() {
		return modifyNode;
	}

	/**
	 * @return The node that dispatches players randomly in team.
	 */
	public TeamsRandomNode getRandomNode() {
		return randomNode;
	}

	/**
	 * @return The node that moves a player from one team to another one.
	 */
	public TeamsMoveNode getMoveNode() {
		return moveNode;
	}
}
