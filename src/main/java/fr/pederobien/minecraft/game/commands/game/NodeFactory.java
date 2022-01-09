package fr.pederobien.minecraft.game.commands.game;

import java.util.function.Supplier;

import fr.pederobien.minecraft.game.interfaces.IFeatureConfigurable;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;

public class NodeFactory {

	/**
	 * Creates a node in order to modify the list of teams of the given configurable but also to modify the characteristics of a team
	 * registered in the list of teams.
	 * 
	 * @param configurable The configurable object that contains a reference to the list of teams to modify.
	 * 
	 * @return A node.
	 */
	public static GameTeamsNode teams(Supplier<ITeamConfigurable> configurable) {
		return new GameTeamsNode(configurable);
	}

	/**
	 * Creates a node in order to set up features of the given configurable but also to modify the characteristics of a feature
	 * registered in the list of features.
	 * 
	 * @param configurable The configurable object that contains a reference to the list of features to modify.
	 * 
	 * @return A node.
	 */
	public static GameFeatureNode features(Supplier<IFeatureConfigurable> configurable) {
		return new GameFeatureNode(configurable);
	}
}
