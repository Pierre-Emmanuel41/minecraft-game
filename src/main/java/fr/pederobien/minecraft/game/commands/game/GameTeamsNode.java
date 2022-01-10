package fr.pederobien.minecraft.game.commands.game;

import java.util.function.Supplier;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.game.commands.team.TeamCommandTree;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;

public class GameTeamsNode extends MinecraftCodeNode {
	private TeamCommandTree teamTree;
	private TeamsAddNode addNode;
	private TeamsListNode listNode;
	private TeamsRemoveNode removeNode;
	private TeamsModifyNode modifyNode;
	private TeamsMoveNode moveNode;
	private TeamsRandomNode randomNode;

	/**
	 * Creates a node in order to modify a team configurable object.
	 * 
	 * @param configurable The configurable object associated to this node.
	 */
	protected GameTeamsNode(Supplier<ITeamConfigurable> configurable) {
		super("teams", EGameCode.GAME_CONFIG__TEAMS__EXPLANATION, () -> configurable.get() != null);

		teamTree = new TeamCommandTree();
		add(addNode = new TeamsAddNode(configurable, teamTree));
		add(removeNode = new TeamsRemoveNode(configurable, teamTree));
		add(modifyNode = new TeamsModifyNode(configurable, teamTree));
		add(listNode = TeamsListNode.newInstance(configurable));
		add(moveNode = new TeamsMoveNode(configurable));
		add(randomNode = new TeamsRandomNode(configurable));

		// By default, all players receive the new teams composition
		randomNode.setTeamsCompositionGroup(PlayerGroup.ALL);
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
	 * @return The node that moves a player from one team to another one.
	 */
	public TeamsMoveNode getMoveNode() {
		return moveNode;
	}

	/**
	 * @return The node that dispatches players randomly in team.
	 */
	public TeamsRandomNode getRandomNode() {
		return randomNode;
	}
}
