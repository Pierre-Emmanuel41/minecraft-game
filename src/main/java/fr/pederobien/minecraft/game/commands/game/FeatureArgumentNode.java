package fr.pederobien.minecraft.game.commands.game;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IFeatureList;

public class FeatureArgumentNode extends FeatureNode {

	/**
	 * Creates a node to set feature arguments before starting.
	 * 
	 * @param features The list of features associated to this node.
	 */
	protected FeatureArgumentNode(IFeatureList features) {
		super(features, "args", EGameCode.GAME_CONFIG__FEATURE_ARGS__EXPLANATION, f -> f != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return filter(getFeatures().stream().map(feature -> feature.getName()), args);
		case 1:

			Optional<IFeature> optFeature = getFeatures().getFeature(args[0]);
			if (!optFeature.isPresent() || !optFeature.get().isEnable())
				return emptyList();
			else
				return optFeature.get().getStartTabExecutor().onTabComplete(sender, command, alias, args);
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__FEATURE_ARGS__NAME_IS_MISSING).build());
			return false;
		}
		Optional<IFeature> optFeature = getFeatures().getFeature(name);

		if (!optFeature.isPresent()) {
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__FEATURE_ARGS__FEATURE_NOT_REGISTERED, name, getFeatures().getName()));
			return false;
		}

		return optFeature.get().getStartTabExecutor().onCommand(sender, command, label, args);
	}
}
