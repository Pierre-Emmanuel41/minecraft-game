package fr.pederobien.minecraft.game.commands.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.commands.team.TeamCommandTree;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.ITeam;

public class GameRemoveTeamNode extends GameNode {
	private TeamCommandTree teamTree;

	protected GameRemoveTeamNode(IGame game, TeamCommandTree teamTree) {
		super(game, "remove", EGameCode.GAME_CONFIG__REMOVE_TEAM__EXPLANATION, g -> g != null && g.getTeams().toList().size() > 1);
		this.teamTree = teamTree;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> teamNames = asList(args);
		Stream<String> teams = getGame().getTeams().stream().map(team -> team.getName()).filter(name -> !teamNames.contains(name));

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
			getGame().getTeams().clear();
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__REMOVE_TEAM__ALL_PLAYERS_REMOVED).build(getGame().getName()));
			return true;
		}

		List<ITeam> teams = new ArrayList<ITeam>();
		for (String name : args) {
			Optional<ITeam> optTeam = getGame().getTeams().getTeam(name);

			// Checking if the team name refers to a registered team.
			if (!optTeam.isPresent()) {
				send(eventBuilder(sender, EGameCode.GAME_CONFIG__REMOVE_TEAM__TEAM_NOT_FOUND, name, getGame().getName()));
				return false;
			}

			teams.add(optTeam.get());
		}

		String teamNames = concat(args);

		for (ITeam team : teams) {
			getGame().getTeams().remove(team);

			// Updating the team tree.
			teamTree.getExceptedNames().remove(team.getName());
			teamTree.getExceptedColors().remove(team.getColor());
		}

		switch (teams.size()) {
		case 0:
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__REMOVE_TEAM__NO_TEAM_REMOVED, getGame().getName()));
			break;
		case 1:
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__REMOVE_TEAM__ONE_TEAM_REMOVED, teamNames, getGame().getName()));
			break;
		default:
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__REMOVE_TEAM__SEVERAL_TEAMS_REMOVED, teamNames, getGame().getName()));
			break;
		}

		return true;
	}
}
