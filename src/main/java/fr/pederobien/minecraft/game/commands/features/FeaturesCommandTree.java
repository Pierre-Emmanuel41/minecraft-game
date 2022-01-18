package fr.pederobien.minecraft.game.commands.features;

import java.util.function.Supplier;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeRootNode;
import fr.pederobien.minecraft.game.commands.feature.FeatureCommandTree;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IFeatureConfigurable;
import fr.pederobien.minecraft.game.interfaces.IFeatureList;

public class FeaturesCommandTree {
	private Supplier<IFeatureConfigurable> configurable;
	private IMinecraftCodeRootNode root;

	private FeatureCommandTree featureTree;
	private FeaturesEnableNode enableNode;
	private FeaturesDisableNode disableNode;
	private FeaturesArgumentNode argumentNode;

	public FeaturesCommandTree(Supplier<IFeatureConfigurable> configurable) {
		this.configurable = configurable;

		root = new MinecraftCodeRootNode("features", EGameCode.FEATURES__EXPLANATION, () -> configurable.get() != null);

		featureTree = new FeatureCommandTree();
		root.add(enableNode = new FeaturesEnableNode(() -> getFeatures()));
		root.add(disableNode = new FeaturesDisableNode(() -> getFeatures()));
		root.add(argumentNode = new FeaturesArgumentNode(() -> getFeatures(), featureTree));
	}

	/**
	 * @return The list of features associated to this command tree
	 */
	public IFeatureList getFeatures() {
		IFeatureConfigurable features = configurable.get();
		return features == null ? null : features.getFeatures();
	}

	/**
	 * @return The root of this tree.
	 */
	public IMinecraftCodeRootNode getRoot() {
		return root;
	}

	/**
	 * @return The node to enable several features from the current features list.
	 */
	public FeaturesEnableNode getEnableNode() {
		return enableNode;
	}

	/**
	 * @return The node to disable several features from the current features list.
	 */
	public FeaturesDisableNode getDisableNode() {
		return disableNode;
	}

	/**
	 * @return The node to set the arguments of a features before starting it.
	 */
	public FeaturesArgumentNode getArgumentNode() {
		return argumentNode;
	}
}
