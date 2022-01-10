package fr.pederobien.minecraft.game.commands.team;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.managers.PlayerManager;

public class TeamAddPlayerNode extends TeamNode {
	private List<Player> exceptedPlayers;

	/**
	 * Creates a node that adds players to a team.
	 * 
	 * @param tree The tree that contains a reference to the team to modify.
	 */
	protected TeamAddPlayerNode(TeamCommandTree tree) {
		super(tree, "add", EGameCode.TEAM__ADD_PLAYER__EXPLANATION, team -> team != null);
		exceptedPlayers = new ArrayList<Player>();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		Predicate<Player> filter;
		switch (args.length) {
		case 0:
			return emptyList();
		default:
			List<String> alreadyMentionned = asList(args);
			filter = player -> !exceptedPlayers.contains(player) && !alreadyMentionned.contains(player.getName());
			return filter(PlayerGroup.ALL.toStream().filter(filter).map(player -> player.getName()), args);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String teamColoredName = getTree().getTeam().getColoredName();

		List<Player> players = new ArrayList<Player>();
		for (String name : args) {
			Player player = PlayerManager.getPlayer(name);

			// Checking if the player name refers to an existing player.
			if (player == null) {
				send(eventBuilder(sender, EGameCode.TEAM__ADD_PLAYER__PLAYER_NOT_FOUND, name, teamColoredName));
				return false;
			}

			// Checking if the player is registered in the current team.
			// Checking if player is in the "black player list".
			if (getTree().getTeam().getPlayers().toList().contains(player) || exceptedPlayers.contains(player)) {
				send(eventBuilder(sender, EGameCode.TEAM__ADD_PLAYER__PLAYER_ALREADY_REGISTERED, name, teamColoredName));
				return false;
			}

			players.add(player);
		}

		String playerNames = concat(args);

		for (Player player : players)
			getTree().getTeam().getPlayers().add(player);

		switch (args.length) {
		case 0:
			sendSuccessful(sender, EGameCode.TEAM__ADD_PLAYER__NO_PLAYER_ADDED, teamColoredName);
			break;
		case 1:
			sendSuccessful(sender, EGameCode.TEAM__ADD_PLAYER__ONE_PLAYER_ADDED, playerNames, teamColoredName);
			break;
		default:
			sendSuccessful(sender, EGameCode.TEAM__ADD_PLAYER__SEVERAL_PLAYERS_ADDED, playerNames, teamColoredName);
			break;
		}
		return true;
	}

	/**
	 * @return The list of players that should not be added in a team.
	 */
	public List<Player> getExceptedPlayers() {
		return exceptedPlayers;
	}
}
