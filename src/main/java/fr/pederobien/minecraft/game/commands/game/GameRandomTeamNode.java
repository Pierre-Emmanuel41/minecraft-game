package fr.pederobien.minecraft.game.commands.game;

import java.util.List;
import java.util.StringJoiner;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.dictionary.impl.MinecraftMessageEvent.MinecraftMessageEventBuilder;
import fr.pederobien.minecraft.dictionary.interfaces.IPlayerGroup;
import fr.pederobien.minecraft.game.exceptions.RandomTeamNotEnoughPlayer;
import fr.pederobien.minecraft.game.exceptions.RandomTeamNotEnoughTeam;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.impl.TeamHelper;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.ITeam;

public class GameRandomTeamNode extends GameNode {
	private IPlayerGroup group;

	protected GameRandomTeamNode(IGame game) {
		super(game, "randomTeams", EGameCode.GAME_CONFIG__RANDOM_TEAMS__EXPLANATION, g -> g != null && g.getTeams().toList().size() > 1);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(sender, EGameCode.GAME_CONFIG__RANDOM_TEAMS__MAX_PLAYERS_PER_TEAM));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		int maxPlayerInTeam;
		try {
			maxPlayerInTeam = Integer.parseInt(args[0]);
			if (maxPlayerInTeam <= 0) {
				send(eventBuilder(sender, EGameCode.GAME_CONFIG__RANDOM_TEAMS__MAX_PLAYERS_PER_TEAM__NEGATIVE_VALUE).build());
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			maxPlayerInTeam = -1;
		} catch (NumberFormatException e) {
			send(eventBuilder(sender, EGameCode.BAD_FORMAT, EGameCode.GAME_CONFIG__RANDOM_TEAMS__MAX_PLAYERS_PER_TEAM__BAD_FORMAT));
			return false;
		}

		try {
			TeamHelper.dispatchPlayerRandomlyInTeam(getGame().getTeams(), maxPlayerInTeam);
		} catch (RandomTeamNotEnoughPlayer e) {
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__RANDOM_TEAMS__NOT_ENOUGH_PLAYERS, e.getPlayersCount()));
			return false;
		} catch (RandomTeamNotEnoughTeam e) {
			send(eventBuilder(sender, EGameCode.GAME_CONFIG__RANDOM_TEAMS__NOT_ENOUGH_TEAMS, e.getTeamsCount()));
			return false;
		}

		StringJoiner joiner = new StringJoiner("\n");
		for (ITeam team : getGame().getTeams())
			if (!team.getPlayers().toList().isEmpty())
				joiner.add(team.toString());

		MinecraftMessageEventBuilder builder = eventBuilder(sender, EGameCode.GAME_CONFIG__RANDOM_TEAMS__PLAYERS_DISPATCHED_IN_TEAMS);
		if (group != null)
			builder.withGroup(group);

		send(builder.build(joiner.toString()));
		return true;
	}

	/**
	 * Set the group of players that will receive the teams composition.
	 * 
	 * @param group The group player.
	 */
	public void setTeamsCompositionGroup(IPlayerGroup group) {
		this.group = group;
	}
}
