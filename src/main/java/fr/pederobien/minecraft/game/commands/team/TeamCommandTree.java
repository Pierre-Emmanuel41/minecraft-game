package fr.pederobien.minecraft.game.commands.team;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeRootNode;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeam;

public class TeamCommandTree {
	private ITeam team;
	private IMinecraftCodeRootNode root;
	private TeamNewNode newNode;
	private TeamModifyNode modifyNode;
	private TeamRemovePlayerNode removePlayerNode;
	private TeamAddPlayerNode addPlayerNode;

	public TeamCommandTree() {
		root = new MinecraftCodeRootNode("team", EGameCode.TEAM__ROOT__EXPLANATION, () -> true);
		root.add(newNode = new TeamNewNode(this));
		root.add(modifyNode = new TeamModifyNode(this));
		root.add(removePlayerNode = new TeamRemovePlayerNode(this));
		root.add(addPlayerNode = new TeamAddPlayerNode(this));
	}

	/**
	 * @return The root of this command tree.
	 */
	public IMinecraftCodeRootNode getRoot() {
		return root;
	}

	/**
	 * @return The node that creates a new team.
	 */
	public TeamNewNode getNewNode() {
		return newNode;
	}

	/**
	 * @return The node that modify the characteristics of the current team.
	 */
	public TeamModifyNode getModifyNode() {
		return modifyNode;
	}

	/**
	 * @return The node that remove players from the current team.
	 */
	public TeamRemovePlayerNode getRemovePlayerNode() {
		return removePlayerNode;
	}

	/**
	 * @return The node that add players to the current team.
	 */
	public TeamAddPlayerNode getAddPlayerNode() {
		return addPlayerNode;
	}

	/**
	 * @return The last created team or null of no team has been created.
	 */
	public ITeam getTeam() {
		return team;
	}

	/**
	 * Set the team manipulated by this tree.
	 * 
	 * @param team The new team.
	 */
	public void setTeam(ITeam team) {
		this.team = team;
	}
}
