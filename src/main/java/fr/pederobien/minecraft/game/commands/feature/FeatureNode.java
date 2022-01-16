package fr.pederobien.minecraft.game.commands.feature;

import java.util.function.Function;
import java.util.function.Supplier;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.interfaces.IFeature;

public class FeatureNode extends MinecraftCodeNode {
	private Supplier<IFeature> feature;

	/**
	 * Create a feature node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param feature     the feature associated to this node.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected FeatureNode(Supplier<IFeature> feature, String label, IMinecraftCode explanation, Function<IFeature, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(feature.get()));
		this.feature = feature;
	}

	/**
	 * Create a feature node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param feature     the feature associated to this node.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 */
	protected FeatureNode(Supplier<IFeature> feature, String label, IMinecraftCode explanation) {
		super(label, explanation);
		this.feature = feature;
	}

	/**
	 * @return The feature associated to this node.
	 */
	public IFeature getFeature() {
		return feature.get();
	}
}
