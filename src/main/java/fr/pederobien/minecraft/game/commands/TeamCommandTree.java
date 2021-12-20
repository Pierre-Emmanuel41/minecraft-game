package fr.pederobien.minecraft.game.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeNode;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.managers.EColor;

public class TeamCommandTree {
	private ITeam team;
	private IMinecraftCodeNode root;
	private List<String> exceptedNames;
	private List<EColor> exceptedColors;
	private List<Player> exceptedPlayers;
	private TeamNewNode newNode;
	private TeamModifyNode modifyNode;
	private TeamRemovePlayerNode removePlayerNode;
	private TeamAddPlayerNode addPlayerNode;

	public TeamCommandTree() {
		exceptedNames = new ArrayList<String>();
		exceptedColors = new ArrayList<EColor>();
		exceptedPlayers = new ArrayList<Player>();

		root = new MinecraftCodeRootNode("team", EGameCode.TEAM__ROOT__EXPLANATION, () -> true);
		root.add(newNode = new TeamNewNode(this));
		root.add(modifyNode = new TeamModifyNode(this));
		root.add(removePlayerNode = new TeamRemovePlayerNode(this));
		root.add(addPlayerNode = new TeamAddPlayerNode(this));
	}

	/**
	 * @return The root of this command tree.
	 */
	public IMinecraftCodeNode getRoot() {
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

	/**
	 * @return The list that contains names that should not be used for another team.
	 */
	public List<String> getExceptedNames() {
		return exceptedNames;
	}

	/**
	 * @return The list that contains colors that should not be used for another team.
	 */
	public List<EColor> getExceptedColors() {
		return exceptedColors;
	}

	/**
	 * @return The list that contains players that should not be added to another team.
	 */
	public List<Player> getExceptedPlayers() {
		return exceptedPlayers;
	}
}
