package fr.pederobien.minecraft.game.commands;

import fr.pederobien.minecraft.game.impl.EGameCode;

public class TeamModifyNode extends TeamNode {
	private TeamModifyNameNode nameNode;
	private TeamModifyColorNode colorNode;

	protected TeamModifyNode(TeamCommandTree tree) {
		super(tree, "modify", EGameCode.TEAM__MODIFY__EXPLANATION);

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
