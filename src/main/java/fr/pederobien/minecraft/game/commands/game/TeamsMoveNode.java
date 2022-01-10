package fr.pederobien.minecraft.game.commands.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.managers.PlayerManager;

public class TeamsMoveNode extends TeamsNode {

	/**
	 * Creates a node in order to move a player from one team to another one.
	 * 
	 * @param teams The list of teams associated to this node.
	 */
	protected TeamsMoveNode(Supplier<ITeamConfigurable> teams) {
		super(teams, "move", EGameCode.GAME_CONFIG__MOVE__EXPLANATION, t -> t != null && t.getTeams() != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			List<Player> players = new ArrayList<Player>();
			for (ITeam team : getTeams())
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
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__MOVE__PLAYER_NAME_IS_MISSING).build());
			return false;
		}

		Player player = PlayerManager.getPlayer(playerName);
		if (player == null) {
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__MOVE__PLAYER_NOT_FOUND, playerName));
			return false;
		}

		String teamName;
		try {
			teamName = args[1];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__MOVE__TEAM_NAME_IS_MISSING, playerName));
			return false;
		}

		Optional<ITeam> optTeam = getTeams().getTeam(teamName);
		if (!optTeam.isPresent()) {
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__MOVE__TEAM_NOT_FOUND, playerName, teamName));
			return false;
		}

		ITeam newTeam = optTeam.get();
		ITeam oldTeam = getTeams().movePlayer(player, optTeam.get());
		if (oldTeam != null) {
			String oldTeamColor = oldTeam.getColoredName(EColor.GOLD);
			sendSuccessful(sender, EGameCode.GAME_CONFIG__MOVE__PLAYER_MOVED_FROM_TO, playerName, oldTeamColor, newTeam.getColoredName(EColor.GOLD));
		} else
			sendSuccessful(sender, EGameCode.TEAM__ADD_PLAYER__ONE_PLAYER_ADDED, playerName, optTeam.get().getColoredName(EColor.GOLD));
		return true;
	}

	private Stream<ITeam> getOtherTeamNames(String name) {
		return getTeams().stream().filter(team -> !team.getPlayers().getPlayer(name).isPresent());
	}
}
