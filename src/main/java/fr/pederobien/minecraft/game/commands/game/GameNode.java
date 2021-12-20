package fr.pederobien.minecraft.game.commands.game;

import com.google.common.base.Function;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.impl.MinecraftMessageEvent.MinecraftMessageEventBuilder;
import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.managers.EColor;

public class GameNode extends MinecraftCodeNode {
	private GameCommandTree tree;

	/**
	 * Create a team node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param tree        The tree that contains a reference to the game to modify.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected GameNode(GameCommandTree tree, String label, IMinecraftCode explanation, Function<IGame, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(tree.getGame()));
		this.tree = tree;
	}

	/**
	 * Create a team node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param tree        The tree that contains a reference to the game to modify.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 */
	protected GameNode(GameCommandTree tree, String label, IMinecraftCode explanation) {
		super(label, explanation);
		this.tree = tree;
	}

	/**
	 * @return The Tree associated to this node.
	 */
	protected GameCommandTree getTree() {
		return tree;
	}

	/**
	 * Send a message to each player with green default prefix and green default suffix.
	 * 
	 * @param code The code to send.
	 */
	protected void send(IMinecraftCode code) {
		MinecraftMessageEventBuilder builder = eventBuilder(code);
		builder.withPrefix(DEFAULT_PREFIX, EColor.GREEN).withSuffix(DEFAULT_SUFFIX, EColor.GREEN);
		send(builder.withBold(true).withGroup(PlayerGroup.ALL).build(getTree().getGame().getName()));
	}
}
