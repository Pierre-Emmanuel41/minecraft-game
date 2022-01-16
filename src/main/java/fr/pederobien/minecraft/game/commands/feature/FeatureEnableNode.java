package fr.pederobien.minecraft.game.commands.feature;

import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IFeature;

public class FeatureEnableNode extends FeatureNode {

	/**
	 * Creates a node to enable a feature.
	 * 
	 * @param feature The feature associated to this node.
	 */
	protected FeatureEnableNode(Supplier<IFeature> feature) {
		super(feature, "enable", EGameCode.FEATURE__ENABLE__EXPLANATION, f -> f != null && !f.isEnable());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		getFeature().setEnabled(true);
		sendSuccessful(sender, EGameCode.FEATURE__ENABLE__FEATURE_ENABLED, getFeature().getName());
		return true;
	}
}
