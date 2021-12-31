package fr.pederobien.minecraft.game.commands.team;

import fr.pederobien.minecraft.game.impl.EGameCode;

public class TeamModifyNode extends TeamNode {
	private TeamModifyNameNode nameNode;
	private TeamModifyColorNode colorNode;

	/**
	 * Creates a node that modifies a team.
	 * 
	 * @param tree The tree that contains a reference to the team to modify.
	 */
	protected TeamModifyNode(TeamCommandTree tree) {
		super(tree, "modify", EGameCode.TEAM__MODIFY__EXPLANATION, team -> team != null);

		add(nameNode = new TeamModifyNameNode(tree));
		add(colorNode = new TeamModifyColorNode(tree));
	}

	/**
	 * @return The node that modify the name of the current team.
	 */
	public TeamModifyNameNode getNameNode() {
		return nameNode;
	}

	/**
	 * @return The node that modify the color of the current team.
	 */
	public TeamModifyColorNode getColorNode() {
		return colorNode;
	}
}
