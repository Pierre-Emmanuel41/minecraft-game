package fr.pederobien.minecraft.game.commands.game;

import com.google.common.base.Function;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.interfaces.IGame;

public class GameNode extends MinecraftCodeNode {
	private IGame game;

	/**
	 * Create a team node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param game        The game associated to this node.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected GameNode(IGame game, String label, IMinecraftCode explanation, Function<IGame, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(game));
		this.game = game;
	}

	/**
	 * Create a team node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param game        The game associated to this node.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 */
	protected GameNode(IGame game, String label, IMinecraftCode explanation) {
		super(label, explanation);
		this.game = game;
	}

	/**
	 * @return The Tree associated to this node.
	 */
	protected IGame getGame() {
		return game;
	}
}
