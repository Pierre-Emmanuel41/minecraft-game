package fr.pederobien.minecraft.game.commands.game;

import java.util.function.Function;
import java.util.function.Supplier;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.interfaces.IFeatureConfigurable;
import fr.pederobien.minecraft.game.interfaces.IFeatureList;

public class FeatureNode extends MinecraftCodeNode {
	private Supplier<IFeatureConfigurable> features;

	/**
	 * Create a feature node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param features    The list of features to configure.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected FeatureNode(Supplier<IFeatureConfigurable> features, String label, IMinecraftCode explanation, Function<IFeatureConfigurable, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(features.get()));
		this.features = features;
	}

	/**
	 * Create a feature node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param features    The list of features to configure.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 */
	protected FeatureNode(Supplier<IFeatureConfigurable> features, String label, IMinecraftCode explanation) {
		super(label, explanation);
		this.features = features;
	}

	/**
	 * @return The feature list associated to this node.
	 */
	public IFeatureList getFeatures() {
		IFeatureConfigurable configurable = features.get();
		return configurable == null ? null : configurable.getFeatures();
	}
}
