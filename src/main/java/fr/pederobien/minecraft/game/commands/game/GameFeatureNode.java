package fr.pederobien.minecraft.game.commands.game;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IFeatureConfigurable;

public class GameFeatureNode extends FeatureNode {
	private FeatureEnableNode enableNode;
	private FeatureDisableNode disableNode;
	private FeatureArgumentNode argumentNode;

	/**
	 * Creates a node in order to modify a feature configurable object.
	 * 
	 * @param configurable The configurable object associated to this node.
	 */
	protected GameFeatureNode(IFeatureConfigurable configurable) {
		super(configurable.getFeatures(), "feature", EGameCode.GAME_CONFIG__FEATURE__EXPLANATION, f -> f != null);
		add(enableNode = new FeatureEnableNode(getFeatures()));
		add(disableNode = new FeatureDisableNode(getFeatures()));
		add(argumentNode = new FeatureArgumentNode(getFeatures()));
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
