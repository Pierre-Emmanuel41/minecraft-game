package fr.pederobien.minecraft.game.commands.feature;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeRootNode;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IFeature;

public class FeatureCommandTree {
	private IFeature feature;
	private IMinecraftCodeRootNode root;
	private FeatureEnableNode enableNode;
	private FeatureDisableNode disableNode;
	private FeatureArgumentNode argumentNode;

	public FeatureCommandTree() {
		root = new MinecraftCodeRootNode("feature", EGameCode.FEATURE__EXPLANATION, () -> getFeature() != null);
		root.add(enableNode = new FeatureEnableNode(() -> getFeature()));
		root.add(disableNode = new FeatureDisableNode(() -> getFeature()));
		root.add(argumentNode = new FeatureArgumentNode(() -> getFeature()));
	}

	/**
	 * @return The feature associated to this command tree.
	 */
	public IFeature getFeature() {
		return feature;
	}

	/**
	 * Set the feature associated to this command tree.
	 * 
	 * @param feature The new command tree feature.
	 */
	public void setFeature(IFeature feature) {
		this.feature = feature;
	}

	/**
	 * @return The root of this tree.
	 */
	public IMinecraftCodeRootNode getRoot() {
		return root;
	}

	/**
	 * @return The node to enable the current feature.
	 */
	public FeatureEnableNode getEnableNode() {
		return enableNode;
	}

	/**
	 * @return The node to disable the current feature.
	 */
	public FeatureDisableNode getDisableNode() {
		return disableNode;
	}

	/**
	 * @return The node to set the argument of a feature before starting it.
	 */
	public FeatureArgumentNode getArgumentNode() {
		return argumentNode;
	}
}
