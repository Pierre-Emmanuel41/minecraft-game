package fr.pederobien.minecraft.game.commands.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.managers.PlayerManager;

public class GameMovePlayerNode extends GameNode {

	protected GameMovePlayerNode(IGame game) {
		super(game, "movePlayer", EGameCode.GAME_CONFIG__MOVE_PLAYER__EXPLANATION, g -> g != null && g.getTeams().toList().size() > 1);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			List<Player> players = new ArrayList<Player>();
			for (ITeam team : getGame().getTeams())
				for (Player player : team.getPlayers())
					players.add(player);
			return filter(players.stream().map(player -> player.getName()), args);
		case 2:
			String name = args[0];
			return filter(check(name, e -> PlayerManager.getPlayer(e) != null, getOtherTeamNames(name).map(team -> team.getName())), args);
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String playerName;
		try {
			playerName = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__MOVE_PLAYER__PLAYER_NAME_IS_MISSING).build());
			return false;
		}

		String teamName;
		try {
			teamName = args[1];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__MOVE_PLAYER__TEAM_NAME_IS_MISSING, playerName));
			return false;
		}

		Player player = PlayerManager.getPlayer(playerName);
		if (player == null) {
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__MOVE_PLAYER__PLAYER_NOT_FOUND, playerName));
			return false;
		}

		Optional<ITeam> optTeam = getGame().getTeams().getTeam(teamName);
		if (!optTeam.isPresent()) {
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__MOVE_PLAYER__TEAM_NOT_FOUND, teamName));
			return false;
		}

		ITeam team = getGame().getTeams().movePlayer(player, optTeam.get());
		if (team != null)
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__MOVE_PLAYER__PLAYER_MOVED_FROM_TO, playerName, optTeam.get().getColoredName(), team.getColoredName()));
		else
			send(eventBuilder(sender, EGameCode.TEAM__ADD_PLAYER__ONE_PLAYER_ADDED, playerName, optTeam.get().getColoredName()));
		return true;
	}

	private Stream<ITeam> getOtherTeamNames(String name) {
		return getGame().getTeams().stream().filter(team -> !team.getPlayers().getPlayer(name).isPresent());
	}
}
