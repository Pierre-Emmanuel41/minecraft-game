package fr.pederobien.minecraft.game.commands.config;

import com.google.common.base.Function;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.interfaces.IGameConfiguration;

public class GameConfigNode extends MinecraftCodeNode {
	private GameConfigCommandTree tree;

	/**
	 * Create a team node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param tree        The tree that contains a reference to the game configuration to modify.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected GameConfigNode(GameConfigCommandTree tree, String label, IMinecraftCode explanation, Function<IGameConfiguration, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(tree.getConfiguration()));
		this.tree = tree;
	}

	/**
	 * Create a team node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param tree        The tree that contains a reference to the game configuration to modify.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 */
	protected GameConfigNode(GameConfigCommandTree tree, String label, IMinecraftCode explanation) {
		super(label, explanation);
		this.tree = tree;
	}

	/**
	 * @return The Tree associated to this node.
	 */
	protected GameConfigCommandTree getTree() {
		return tree;
	}
}
