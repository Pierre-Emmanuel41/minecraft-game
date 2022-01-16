package fr.pederobien.minecraft.game.commands.teams;

import java.util.function.Supplier;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeRootNode;
import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.game.commands.team.TeamCommandTree;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;
import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class TeamsCommandTree {
	private Supplier<ITeamConfigurable> configurable;
	private IMinecraftCodeRootNode root;
	private TeamCommandTree teamTree;
	private TeamsAddNode addNode;
	private TeamsRemoveNode removeNode;
	private TeamsModifyNode modifyNode;
	private TeamsListNode listNode;
	private TeamsMoveNode moveNode;
	private TeamsRandomNode randomNode;

	public TeamsCommandTree(Supplier<ITeamConfigurable> configurable) {
		this.configurable = configurable;

		root = new MinecraftCodeRootNode("teams", EGameCode.TEAMS__EXPLANATION, () -> configurable.get() != null);

		teamTree = new TeamCommandTree();
		root.add(addNode = new TeamsAddNode(() -> getTeams(), teamTree));
		root.add(removeNode = new TeamsRemoveNode(() -> getTeams(), teamTree));
		root.add(modifyNode = new TeamsModifyNode(() -> getTeams(), teamTree));
		root.add(listNode = TeamsListNode.newInstance(() -> getTeams()));
		root.add(moveNode = new TeamsMoveNode(() -> getTeams()));
		root.add(randomNode = new TeamsRandomNode(() -> getTeams()));

		// By default, all players receive the new teams composition
		randomNode.setTeamsCompositionGroup(PlayerGroup.ALL);
	}

	/**
	 * @return The list of teams associated to this command tree.
	 */
	public ITeamList getTeams() {
		ITeamConfigurable teams = configurable.get();
		return teams == null ? null : teams.getTeams();
	}

	/**
	 * @return The root of this tree.
	 */
	public IMinecraftCodeRootNode getRoot() {
		return root;
	}

	/**
	 * @return The node that adds team to the list of teams.
	 */
	public TeamsAddNode getAddNode() {
		return addNode;
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
	 * @return The node that displays the characteristics of each team of the list of teams.
	 */
	public TeamsListNode getListNode() {
		return listNode;
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
