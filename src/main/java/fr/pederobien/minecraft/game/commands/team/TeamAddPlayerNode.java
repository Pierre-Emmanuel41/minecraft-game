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

	protected TeamAddPlayerNode(TeamCommandTree tree) {
		super(tree, "add", EGameCode.TEAM__ADD_PLAYER__EXPLANATION, team -> team != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			List<String> playerNames = asList(args);
			Predicate<Player> filter = player -> !getTree().getExceptedPlayers().contains(player) && playerNames.contains(player.getName());
			return filter(PlayerGroup.ALL.toStream().filter(filter).map(player -> player.getName()), args);
		default:
			return emptyList();
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
			if (getTree().getTeam().getPlayers().toList().contains(player)) {
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
			send(eventBuilder(sender, EGameCode.TEAM__ADD_PLAYER__NO_PLAYER_ADDED, teamColoredName));
			break;
		case 1:
			send(eventBuilder(sender, EGameCode.TEAM__ADD_PLAYER__ONE_PLAYER_ADDED, playerNames, teamColoredName));
			break;
		default:
			send(eventBuilder(sender, EGameCode.TEAM__ADD_PLAYER__SEVERAL_PLAYERS_ADDED, playerNames, teamColoredName));
			break;
		}
		return true;
	}
}
