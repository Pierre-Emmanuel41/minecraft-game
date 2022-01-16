package fr.pederobien.minecraft.game.commands.features;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IFeatureList;

public class FeaturesDisableNode extends FeaturesNode {

	/**
	 * Creates a node to disable several features.
	 * 
	 * @param features The list of features associated to this node.
	 */
	protected FeaturesDisableNode(Supplier<IFeatureList> features) {
		super(features, "disable", EGameCode.FEATURES__DISABLE__EXPLANATION);
		setAvailable(() -> getFeatures() != null && getFeatures().stream().filter(f -> f.isEnable()).findAny().isPresent());
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			List<String> alreadyMentionnedFeatures = asList(args);
			Predicate<String> filter = name -> !alreadyMentionnedFeatures.contains(name);
			return filter(getFeatures().stream().map(feature -> feature.getName()).filter(filter), args);
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		List<IFeature> features = new ArrayList<IFeature>();
		for (String name : args) {
			Optional<IFeature> optFeature = getFeatures().getFeature(name);
			if (!optFeature.isPresent()) {
				send(eventBuilder(sender, EGameCode.FEATURES__DISABLE__FEATURE_NOT_FOUND, name));
				return false;
			}

			features.add(optFeature.get());
		}

		String featureNames = concat(args);
		for (IFeature feature : features)
			feature.setEnabled(false);

		switch (features.size()) {
		case 0:
			sendSuccessful(sender, EGameCode.FEATURES__DISABLE__NO_FEATURE_DISABLED, getFeatures().getName());
			break;
		case 1:
			sendSuccessful(sender, EGameCode.FEATURES__DISABLE__ONE_FEATURE_DISABLED, getFeatures().getName(), featureNames);
			break;
		default:
			sendSuccessful(sender, EGameCode.FEATURES__DISABLE__SEVERAL_FEATURES_DISABLED, getFeatures().getName(), featureNames);
			break;
		}
		return true;
	}
}
