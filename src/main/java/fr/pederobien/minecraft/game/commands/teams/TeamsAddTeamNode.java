package fr.pederobien.minecraft.game.commands.teams;

import java.util.List;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.commands.team.TeamCommandTree;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamList;
import fr.pederobien.minecraft.managers.EColor;

public class TeamsAddTeamNode extends TeamsNode {
	private TeamCommandTree teamTree;

	/**
	 * Creates a node in order to add a team to a list of teams.
	 * 
	 * @param teams    The list of teams associated to this node.
	 * @param teamTree The command tree in order create or modify a team.
	 */
	protected TeamsAddTeamNode(Supplier<ITeamList> teams, TeamCommandTree teamTree) {
		super(teams, "team", EGameCode.TEAMS__ADD_TEAM__EXPLANATION, t -> t != null);
		this.teamTree = teamTree;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return teamTree.getNewNode().onTabComplete(sender, command, alias, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		teamTree.getNewNode().getExceptedNames().clear();
		teamTree.getNewNode().getExceptedColors().clear();
		getTeams().toList().forEach(team -> {
			teamTree.getNewNode().getExceptedNames().add(team.getName());
			teamTree.getNewNode().getExceptedColors().add(team.getColor());
		});

		boolean result = teamTree.getNewNode().onCommand(sender, command, label, args);
		if (result) {
			ITeam team = teamTree.getTeam();
			getTeams().add(team);
			sendSuccessful(sender, EGameCode.TEAMS__ADD_TEAM__TEAM_ADDED, team.getColoredName(EColor.GOLD), getTeams().getName());
		}
		return result;
	}
}
