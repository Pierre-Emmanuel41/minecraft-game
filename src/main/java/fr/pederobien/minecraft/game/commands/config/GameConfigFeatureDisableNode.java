package fr.pederobien.minecraft.game.commands.config;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IFeature;

public class GameConfigFeatureDisableNode extends GameConfigNode {

	protected GameConfigFeatureDisableNode(GameConfigCommandTree tree) {
		super(tree, "disable", EGameCode.GAME_CONFIG__FEATURE_DISABLE__EXPLANATION, config -> config != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			List<String> alreadyMentionnedFeatures = asList(args);
			Predicate<String> filter = name -> alreadyMentionnedFeatures.contains(name);
			return filter(getTree().getConfiguration().getFeatures().stream().map(feature -> feature.getName()).filter(filter), args);
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Optional<IFeature> optFeature;
		try {
			optFeature = getTree().getConfiguration().getFeatures().getFeature(args[0]);
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__FEATURE_DISABLE__NAME_IS_MISSING).build());
			return false;
		}

		optFeature.get().setEnabled(false);
		send(eventBuilder(sender, EGameCode.GAME_CONFIG__FEATURE_DISABLE__DISABLED, optFeature.get().getName()));
		return true;
	}

}
