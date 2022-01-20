package fr.pederobien.minecraft.game.commands.teams;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamList;
import fr.pederobien.minecraft.managers.EColor;

public class TeamsRemoveTeamNode extends TeamsNode {

	/**
	 * Creates a node in order to remove teams from a team list.
	 * 
	 * @param teams    The list of teams associated to this node.
	 * @param teamTree The command tree in order create or modify a team.
	 */
	protected TeamsRemoveTeamNode(Supplier<ITeamList> teams) {
		super(teams, "team", EGameCode.TEAMS__REMOVE_TEAM__EXPLANATION, t -> t != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> teamNames = asList(args);
		Stream<String> teams = getTeams().stream().map(team -> team.getName()).filter(name -> !teamNames.contains(name));

		// Adding all to delete all registered teams.
		if (args.length == 1)
			return filter(Stream.concat(teams, Stream.of("all")), args);

		// If the first argument is all -> any team is proposed
		// Else propose not already mentioned teams
		return filter(args[0].equals("all") ? emptyStream() : teams, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		// Clearing the current configuration from its teams.
		if (args[0].equals("all")) {
			getTeams().clear();
			sendSuccessful(sender, EGameCode.TEAMS__REMOVE_TEAM__ALL_PLAYERS_REMOVED, getTeams().getName());
			return true;
		}

		List<ITeam> teams = new ArrayList<ITeam>();
		for (String name : args) {
			Optional<ITeam> optTeam = getTeams().getTeam(name);

			// Checking if the team name refers to a registered team.
			if (!optTeam.isPresent()) {
				send(eventBuilder(sender, EGameCode.TEAMS__REMOVE_TEAM__TEAM_NOT_FOUND, name, getTeams().getName()));
				return false;
			}

			teams.add(optTeam.get());
		}

		String teamNames = concat(teams.stream().map(chat -> chat.getColoredName(EColor.GOLD)).collect(Collectors.toList()), ", ");

		for (ITeam team : teams)
			getTeams().remove(team);

		switch (teams.size()) {
		case 0:
			sendSuccessful(sender, EGameCode.TEAMS__REMOVE_TEAM__NO_TEAM_REMOVED, getTeams().getName());
			break;
		case 1:
			sendSuccessful(sender, EGameCode.TEAMS__REMOVE_TEAM__ONE_TEAM_REMOVED, teamNames, getTeams().getName());
			break;
		default:
			sendSuccessful(sender, EGameCode.TEAMS__REMOVE_TEAM__SEVERAL_TEAMS_REMOVED, teamNames, getTeams().getName());
			break;
		}

		return true;
	}
}
