package fr.pederobien.minecraft.game.commands.feature;

import java.util.List;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IFeature;

public class FeatureArgumentNode extends FeatureNode {

	/**
	 * Creates a node to set the argument of a feature before starting it.
	 * 
	 * @param feature The feature associated to this node.
	 */
	protected FeatureArgumentNode(Supplier<IFeature> feature) {
		super(feature, "args", EGameCode.FEATURE__ARGS__EXPLANATION, f -> f != null && f.isEnable());
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return getFeature().getStartTabExecutor().onTabComplete(sender, command, alias, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return getFeature().getStartTabExecutor().onCommand(sender, command, label, args);
	}
}
