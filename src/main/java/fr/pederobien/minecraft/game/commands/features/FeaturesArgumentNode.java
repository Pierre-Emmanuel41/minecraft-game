package fr.pederobien.minecraft.game.commands.features;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.commands.feature.FeatureCommandTree;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IFeatureList;

public class FeaturesArgumentNode extends FeaturesNode {
	private FeatureCommandTree featureTree;

	/**
	 * Creates a node to set the argument of a feature before starting it.
	 * 
	 * @param features    The list of features associated to this node.
	 * @param featureTree The command tree to set up a feature.
	 */
	protected FeaturesArgumentNode(Supplier<IFeatureList> features, FeatureCommandTree featureTree) {
		super(features, featureTree.getArgumentNode().getLabel(), featureTree.getArgumentNode().getExplanation());
		setAvailable(() -> getFeatures() != null && getFeatures().stream().filter(f -> f.isEnable()).findFirst().isPresent());
		this.featureTree = featureTree;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return emptyList();
		case 1:
			return filter(getFeatures().stream().map(feature -> feature.getName()), args);
		default:
			Optional<IFeature> optFeature = getFeatures().getFeature(args[0]);
			if (!optFeature.isPresent())
				return emptyList();

			featureTree.setFeature(optFeature.get());
			return featureTree.getArgumentNode().onTabComplete(sender, command, alias, extract(args, 1));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EGameCode.FEATURES__ARGS__NAME_IS_MISSING).build());
			return false;
		}

		Optional<IFeature> optFeature = getFeatures().getFeature(name);
		if (!optFeature.isPresent()) {
			send(eventBuilder(sender, EGameCode.FEATURES__ARGS__FEATURE_NOT_FOUND, name));
			return false;
		}

		if (!optFeature.get().isEnable()) {
			send(eventBuilder(sender, EGameCode.FEATURES__ARGS__FEATURE_NOT_ENABLED, name));
			return false;
		}

		featureTree.setFeature(optFeature.get());
		return featureTree.getArgumentNode().onCommand(sender, command, label, args);
	}
}
