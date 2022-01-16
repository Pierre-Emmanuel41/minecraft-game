package fr.pederobien.minecraft.game.commands.feature;

import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IFeature;

public class FeatureDisableNode extends FeatureNode {

	/**
	 * Creates a node to disable a feature.
	 * 
	 * @param feature The feature associated to this node.
	 */
	protected FeatureDisableNode(Supplier<IFeature> feature) {
		super(feature, "disable", EGameCode.FEATURE__DISABLE__EXPLANATION, f -> f != null && f.isEnable());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		getFeature().setEnabled(false);
		sendSuccessful(sender, EGameCode.FEATURE__DISABLE__FEATURE_DISABLED, getFeature().getName());
		return true;
	}
}
