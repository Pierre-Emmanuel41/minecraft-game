package fr.pederobien.minecraft.game.commands.config;

import fr.pederobien.minecraft.game.impl.EGameCode;

public class GameConfigFeatureNode extends GameConfigNode {
	private GameConfigFeatureEnableNode enableNode;
	private GameConfigFeatureDisableNode disableNode;
	private GameConfigFeatureArgumentNode argumentNode;

	protected GameConfigFeatureNode(GameConfigCommandTree tree) {
		super(tree, "feature", EGameCode.GAME_CONFIG__FEATURE__EXPLANATION, config -> config != null);
		add(enableNode = new GameConfigFeatureEnableNode(tree));
		add(disableNode = new GameConfigFeatureDisableNode(tree));
		add(argumentNode = new GameConfigFeatureArgumentNode(tree));
	}

	/**
	 * @return The node that enables features.
	 */
	public GameConfigFeatureEnableNode getEnableNode() {
		return enableNode;
	}

	/**
	 * @return The node that disable features.
	 */
	public GameConfigFeatureDisableNode getDisableNode() {
		return disableNode;
	}

	/**
	 * @return The node that set the start argument of a feature.
	 */
	public GameConfigFeatureArgumentNode getArgumentNode() {
		return argumentNode;
	}
}
