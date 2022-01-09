package fr.pederobien.minecraft.game.commands.game;

import java.util.function.Supplier;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IFeatureConfigurable;

public class GameFeatureNode extends MinecraftCodeNode {
	private FeatureEnableNode enableNode;
	private FeatureDisableNode disableNode;
	private FeatureArgumentNode argumentNode;

	/**
	 * Creates a node in order to modify a feature configurable object.
	 * 
	 * @param configurable The configurable object associated to this node.
	 */
	protected GameFeatureNode(Supplier<IFeatureConfigurable> configurable) {
		super("feature", EGameCode.GAME_CONFIG__FEATURE__EXPLANATION, () -> configurable.get() != null);
		add(enableNode = new FeatureEnableNode(configurable));
		add(disableNode = new FeatureDisableNode(configurable));
		add(argumentNode = new FeatureArgumentNode(configurable));
	}

	/**
	 * @return The node that enables features.
	 */
	public FeatureEnableNode getEnableNode() {
		return enableNode;
	}

	/**
	 * @return The node that disable features.
	 */
	public FeatureDisableNode getDisableNode() {
		return disableNode;
	}

	/**
	 * @return The node that set the start argument of a feature.
	 */
	public FeatureArgumentNode getArgumentNode() {
		return argumentNode;
	}
}
