package fr.pederobien.minecraft.game.commands.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeam;

public class GameConfigRemoveTeamNode extends GameConfigNode {

	protected GameConfigRemoveTeamNode(GameConfigCommandTree tree) {
		super(tree, "add", EGameCode.GAME_CONFIG__REMOVE_TEAM__EXPLANATION, config -> config != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> teamNames = asList(args);
		Stream<String> teams = getTree().getConfiguration().getTeams().stream().map(team -> team.getName()).filter(name -> !teamNames.contains(name));

		// Adding all to delete all registered teams.
		if (args.length == 1)
			return filter(Stream.concat(teams, Stream.of("all")), args);

		// If the first argument is all -> any team is proposed
		// Else propose not already mentioned teams
		return filter(args[0].equals("all") ? emptyStream() : teams, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String configName = getTree().getConfiguration().getName();

		// Clearing the current configuration from its teams.
		if (args[0].equals("all")) {
			getTree().getConfiguration().clear();
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__REMOVE_TEAM__ALL_PLAYERS_REMOVED).build(configName));
			return true;
		}

		List<ITeam> teams = new ArrayList<ITeam>();
		for (String name : args) {
			ITeam team = getTree().getConfiguration().getTeam(name);

			// Checking if the team name refers to a registered team.
			if (team == null) {
				send(eventBuilder(sender, EGameCode.GAME_CONFIG__REMOVE_TEAM__TEAM_NOT_FOUND, name, configName));
				return false;
			}

			teams.add(team);
		}

		String teamNames = concat(args);

		for (ITeam team : teams) {
			getTree().getConfiguration().remove(team);

			// Updating the team tree.
			getTree().getTeamTree().getExceptedNames().remove(team.getName());
			getTree().getTeamTree().getExceptedColors().remove(team.getColor());
		}

		switch (teams.size()) {
		case 0:
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__REMOVE_TEAM__NO_TEAM_REMOVED, configName));
			break;
		case 1:
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__REMOVE_TEAM__ONE_TEAM_REMOVED, teamNames, configName));
			break;
		default:
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__REMOVE_TEAM__SEVERAL_TEAMS_REMOVED, teamNames, configName));
			break;
		}

		return true;
	}
}
