package fr.pederobien.minecraft.game.commands;

import com.google.common.base.Function;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.interfaces.ITeam;

public class TeamNode extends MinecraftCodeNode {
	private TeamCommandTree tree;

	/**
	 * Create a team node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param tree        The tree that contains a reference to the team to modify.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected TeamNode(TeamCommandTree tree, String label, IMinecraftCode explanation, Function<ITeam, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(tree.getTeam()));
	}

	/**
	 * Create a team node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param tree        The tree that contains a reference to the team to modify.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 */
	protected TeamNode(TeamCommandTree tree, String label, IMinecraftCode explanation) {
		super(label, explanation);
	}

	/**
	 * @return The Tree associated to this node.
	 */
	protected TeamCommandTree getTree() {
		return tree;
	}
}
