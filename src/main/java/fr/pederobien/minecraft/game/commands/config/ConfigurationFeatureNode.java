package fr.pederobien.minecraft.game.commands.config;

import fr.pederobien.minecraft.game.impl.EGameCode;

public class ConfigurationFeatureNode extends ConfigurationNode {
	private ConfigurationFeatureEnableNode enableNode;
	private ConfigurationFeatureDisableNode disableNode;
	private ConfigurationFeatureArgumentNode argumentNode;

	protected ConfigurationFeatureNode(ConfigurationCommandTree tree) {
		super(tree, "feature", EGameCode.GAME_CONFIG__FEATURE__EXPLANATION, config -> config != null);
		add(enableNode = new ConfigurationFeatureEnableNode(tree));
		add(disableNode = new ConfigurationFeatureDisableNode(tree));
		add(argumentNode = new ConfigurationFeatureArgumentNode(tree));
	}

	/**
	 * @return The node that enables features.
	 */
	public ConfigurationFeatureEnableNode getEnableNode() {
		return enableNode;
	}

	/**
	 * @return The node that disable features.
	 */
	public ConfigurationFeatureDisableNode getDisableNode() {
		return disableNode;
	}

	/**
	 * @return The node that set the start argument of a feature.
	 */
	public ConfigurationFeatureArgumentNode getArgumentNode() {
		return argumentNode;
	}
}
