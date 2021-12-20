package fr.pederobien.minecraft.game.commands.team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.managers.PlayerManager;

public class TeamRemovePlayerNode extends TeamNode {

	protected TeamRemovePlayerNode(TeamCommandTree tree) {
		super(tree, "remove", EGameCode.TEAM__REMOVE_PLAYER__EXPLANATION, team -> team != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> playerNames = asList(args);
		Stream<String> players = getTree().getTeam().getPlayers().stream().map(player -> player.getName()).filter(name -> !playerNames.contains(name));

		// Adding all to delete all registered players.
		if (args.length == 1)
			return filter(Stream.concat(players, Stream.of("all")), args);

		// If the first argument is all -> any player is proposed
		// Else propose not already mentioned players
		return filter(args[0].equals("all") ? emptyStream() : players, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String teamColoredName = getTree().getTeam().getColoredName();

		// Clearing the current team from its players.
		if (args[0].equals("all")) {
			getTree().getTeam().clear();
			send(eventBuilder(sender, EGameCode.TEAM__REMOVE_PLAYER__ALL_PLAYERS_REMOVED).build(teamColoredName));
			return true;
		}

		List<Player> players = new ArrayList<Player>();
		for (String name : args) {
			Player player = PlayerManager.getPlayer(name);

			// Checking if the player name refers to an existing player.
			if (player == null) {
				send(eventBuilder(sender, EGameCode.TEAM__REMOVE_PLAYER__PLAYER_NOT_FOUND, name, teamColoredName));
				return false;
			}

			// Checking if the player is registered in the current team.
			if (!getTree().getTeam().getPlayers().contains(player)) {
				send(eventBuilder(sender, EGameCode.TEAM__REMOVE_PLAYER__PLAYER_NOT_REGISTERED, name, teamColoredName));
				return false;
			}

			players.add(player);
		}

		String playerNames = concat(args);

		for (Player player : players)
			getTree().getTeam().remove(player);

		switch (players.size()) {
		case 0:
			send(eventBuilder(sender, EGameCode.TEAM__REMOVE_PLAYER__NO_PLAYER_REMOVED, teamColoredName));
			break;
		case 1:
			send(eventBuilder(sender, EGameCode.TEAM__REMOVE_PLAYER__ONE_PLAYER_REMOVED, playerNames, teamColoredName));
			break;
		default:
			send(eventBuilder(sender, EGameCode.TEAM__REMOVE_PLAYER__SEVERAL_PLAYERS_REMOVED, playerNames, teamColoredName));
			break;
		}

		return true;
	}
}
