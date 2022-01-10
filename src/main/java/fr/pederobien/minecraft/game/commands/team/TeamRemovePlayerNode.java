package fr.pederobien.minecraft.game.commands.team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.managers.PlayerManager;

public class TeamRemovePlayerNode extends TeamNode {

	/**
	 * Creates a node that removes players from a team.
	 * 
	 * @param tree The tree that contains a reference to the team to modify.
	 */
	protected TeamRemovePlayerNode(TeamCommandTree tree) {
		super(tree, "remove", EGameCode.TEAM__REMOVE_PLAYER__EXPLANATION, team -> team != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> playerNames = asList(args);
		Stream<String> players = getTree().getTeam().getPlayers().stream().map(player -> player.getName()).filter(name -> !playerNames.contains(name));

		if (getTree().getTeam().getPlayers().toList().isEmpty())
			return emptyList();

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

		String first;
		try {
			first = args[0];
		} catch (IndexOutOfBoundsException e) {
			sendSuccessful(sender, EGameCode.TEAM__REMOVE_PLAYER__NO_PLAYER_REMOVED, teamColoredName);
			return false;
		}

		// Clearing the current team from its players.
		if (first.equals("all")) {
			getTree().getTeam().getPlayers().clear();
			sendSuccessful(sender, EGameCode.TEAM__REMOVE_PLAYER__ALL_PLAYERS_REMOVED, getTree().getTeam().getColoredName(EColor.GOLD));
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
			if (!getTree().getTeam().getPlayers().toList().contains(player)) {
				send(eventBuilder(sender, EGameCode.TEAM__REMOVE_PLAYER__PLAYER_NOT_REGISTERED, name, teamColoredName));
				return false;
			}

			players.add(player);
		}

		String playerNames = concat(args);

		for (Player player : players)
			getTree().getTeam().getPlayers().remove(player);

		switch (players.size()) {
		case 1:
			sendSuccessful(sender, EGameCode.TEAM__REMOVE_PLAYER__ONE_PLAYER_REMOVED, playerNames, getTree().getTeam().getColoredName(EColor.GOLD));
			break;
		default:
			sendSuccessful(sender, EGameCode.TEAM__REMOVE_PLAYER__SEVERAL_PLAYERS_REMOVED, playerNames, getTree().getTeam().getColoredName(EColor.GOLD));
			break;
		}

		return true;
	}
}
