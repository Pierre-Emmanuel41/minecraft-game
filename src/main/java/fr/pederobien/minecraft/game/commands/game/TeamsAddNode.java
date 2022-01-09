package fr.pederobien.minecraft.game.commands.game;

import java.util.List;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.commands.team.TeamCommandTree;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;

public class TeamsAddNode extends TeamsNode {
	private TeamCommandTree teamTree;

	/**
	 * Creates a node that adds team in a team list.
	 * 
	 * @param teams    The list of teams associated to this node.
	 * @param teamTree The command tree in order create or modify a team.
	 */
	protected TeamsAddNode(Supplier<ITeamConfigurable> teams, TeamCommandTree teamTree) {
		super(teams, "add", EGameCode.GAME_CONFIG__TEAMS_ADD__EXPLANATION, t -> t != null && t.getTeams() != null);
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
			sendSuccessful(sender, EGameCode.GAME_CONFIG__TEAMS_ADD__TEAM_ADDED, team.getColoredName(), getTeams().getName());
		}
		return result;
	}

}
