package fr.pederobien.minecraft.game.commands.game;

import java.util.function.Function;
import java.util.function.Supplier;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;
import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class TeamsNode extends MinecraftCodeNode {
	private Supplier<ITeamConfigurable> teams;

	/**
	 * Create a teams node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param features    The list of teams to configure.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected TeamsNode(Supplier<ITeamConfigurable> teams, String label, IMinecraftCode explanation, Function<ITeamConfigurable, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(teams.get()));
		this.teams = teams;
	}

	/**
	 * @return The list of teams associated to this node.
	 */
	public ITeamList getTeams() {
		ITeamConfigurable configurable = teams.get();
		return configurable == null ? null : configurable.getTeams();
	}
}
