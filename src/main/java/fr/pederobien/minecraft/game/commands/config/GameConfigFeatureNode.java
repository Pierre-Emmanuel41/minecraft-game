package fr.pederobien.minecraft.game.commands.config;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IFeature;

public class GameConfigFeatureNode extends GameConfigNode {

	protected GameConfigFeatureNode(GameConfigCommandTree tree) {
		super(tree, "feature", EGameCode.GAME_CONFIG__FEATURE__EXPLANATION, config -> config != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return filter(getTree().getConfiguration().getFeatures().stream().map(feature -> feature.getName()), args);
		case 1:
			return asList(getMessage(sender, EGameCode.ENABLE__COMPLETION), getMessage(sender, EGameCode.DISABLE__COMPLETION));
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
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__FEATURE__NAME_IS_MISSING).build());
			return false;
		}

		boolean isEnable;
		if (getMessage(sender, EGameCode.ENABLE__COMPLETION) == args[1])
			isEnable = true;
		else if (getMessage(sender, EGameCode.ENABLE__COMPLETION) == args[1])
			isEnable = false;
		else {
			send(eventBuilder(sender, EGameCode.ENABLE_DISABLE__BAD_FORMAT).build());
			return false;
		}

		optFeature.get().setEnabled(isEnable);
		send(eventBuilder(sender, isEnable ? EGameCode.GAME_CONFIG__FEATURE__ENABLE : EGameCode.GAME_CONFIG__FEATURE__DISABLE, optFeature.get().getName()));
		return true;
	}
}
