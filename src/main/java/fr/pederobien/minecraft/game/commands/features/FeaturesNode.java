package fr.pederobien.minecraft.game.commands.features;

import java.util.function.Function;
import java.util.function.Supplier;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.interfaces.IFeatureList;

public class FeaturesNode extends MinecraftCodeNode {
	private Supplier<IFeatureList> features;

	/**
	 * Create a features node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected FeaturesNode(Supplier<IFeatureList> features, String label, IMinecraftCode explanation, Function<IFeatureList, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(features.get()));
		this.features = features;
	}

	/**
	 * Create a features node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 */
	protected FeaturesNode(Supplier<IFeatureList> features, String label, IMinecraftCode explanation) {
		super(label, explanation);
		this.features = features;
	}

	/**
	 * @return The list of features associated to this node.
	 */
	public IFeatureList getFeatures() {
		return features.get();
	}
}
