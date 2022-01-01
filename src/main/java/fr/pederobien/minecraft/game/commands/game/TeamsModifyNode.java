package fr.pederobien.minecraft.game.commands.game;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.commands.team.TeamCommandTree;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class TeamsModifyNode extends TeamsNode {
	private TeamCommandTree teamTree;

	/**
	 * Creates a node that modify characteristics of a team.
	 * 
	 * @param teams    The list of teams associated to this node.
	 * @param teamTree The command tree in order create or modify a team.
	 */
	protected TeamsModifyNode(ITeamList teams, TeamCommandTree teamTree) {
		super(teams, "modify", EGameCode.GAME_CONFIG__TEAMS_MODIFY__EXPLANATION);
		this.teamTree = teamTree;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return filter(getTeams().stream().map(team -> team.getName()), args);

		case 2:
			Optional<ITeam> optTeam = getTeams().getTeam(args[0]);
			if (!optTeam.isPresent())
				return emptyList();

			teamTree.setTeam(optTeam.get());
			return teamTree.getModifyNode().onTabComplete(sender, command, alias, extract(args, 1));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ITeam team;
		try {
			Optional<ITeam> optTeam = getTeams().getTeam(args[0]);
			if (!optTeam.isPresent()) {
				return false;
			}
			team = optTeam.get();
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

		teamTree.setTeam(team);
		return teamTree.getModifyNode().onCommand(sender, command, label, extract(args, 1));
	}
}