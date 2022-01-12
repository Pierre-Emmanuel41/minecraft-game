package fr.pederobien.minecraft.game.commands.game;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IFeatureConfigurable;

public class FeatureEnableNode extends FeatureNode {

	/**
	 * Creates a node to enable features.
	 * 
	 * @param features The list of features associated to this node.
	 */
	protected FeatureEnableNode(Supplier<IFeatureConfigurable> features) {
		super(features, "enable", EGameCode.GAME_CONFIG__FEATURES_ENABLE__EXPLANATION, f -> f != null && f.getFeatures() != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			List<String> alreadyMentionnedFeatures = asList(args);
			Predicate<String> filter = name -> alreadyMentionnedFeatures.contains(name);
			return filter(getFeatures().stream().map(feature -> feature.getName()).filter(filter), args);
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Optional<IFeature> optFeature;
		try {
			optFeature = getFeatures().getFeature(args[0]);
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__FEATURES_ENABLE__NAME_IS_MISSING).build());
			return false;
		}

		optFeature.get().setEnabled(true);
		sendSuccessful(sender, EGameCode.GAME_CONFIG__FEATURES_ENABLE__ENABLED, optFeature.get().getName());
		return true;
	}
}
