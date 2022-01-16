package fr.pederobien.minecraft.game.commands.teams;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.dictionary.impl.MinecraftMessageEvent.MinecraftMessageEventBuilder;
import fr.pederobien.minecraft.dictionary.interfaces.IPlayerGroup;
import fr.pederobien.minecraft.game.exceptions.RandomTeamNotEnoughTeam;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.impl.TeamHelper;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamList;
import fr.pederobien.minecraft.managers.EColor;

public class TeamsRandomNode extends TeamsNode {
	private IPlayerGroup group;

	/**
	 * Creates a node in order to dispatch players randomly in team.
	 * 
	 * @param teams The list of teams associated to this node.
	 */
	protected TeamsRandomNode(Supplier<ITeamList> teams) {
		super(teams, "random", EGameCode.TEAMS__RANDOM__EXPLANATION, t -> t != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(sender, EGameCode.TEAMS__RANDOM__MAX_PLAYERS_PER_TEAM));
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
				send(eventBuilder(sender, EGameCode.TEAMS__RANDOM__MAX_PLAYERS_PER_TEAM__NEGATIVE_VALUE).build());
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			maxPlayerInTeam = -1;
		} catch (NumberFormatException e) {
			send(eventBuilder(sender, EGameCode.BAD_FORMAT, getMessage(sender, EGameCode.STRICTLY_POSITIVE_INTEGER__BAD_FORMAT)));
			return false;
		}

		try {
			TeamHelper.dispatchPlayerRandomlyInTeam(getTeams(), maxPlayerInTeam);
		} catch (RandomTeamNotEnoughTeam e) {
			send(eventBuilder(sender, EGameCode.TEAMS__RANDOM__NOT_ENOUGH_TEAMS).build());
			return false;
		}

		StringJoiner joiner = new StringJoiner("\n");
		for (ITeam team : getTeams())
			if (!team.getPlayers().toList().isEmpty())
				joiner.add(team.toString());

		MinecraftMessageEventBuilder builder = eventBuilder(sender, EGameCode.TEAMS__RANDOM__PLAYERS_DISPATCHED_IN_TEAMS);
		if (group != null)
			builder.withGroup(group);

		builder.withPrefix(DEFAULT_PREFIX, EColor.GREEN).withSuffix(DEFAULT_SUFFIX, EColor.GREEN).withColor(EColor.GOLD);
		send(builder.build(joiner.toString()));
		return true;
	}

	/**
	 * Set the group of players that will receive the teams composition.
	 * 
	 * @param group The group player.
	 * 
	 * @return This node.
	 */
	public TeamsRandomNode setTeamsCompositionGroup(IPlayerGroup group) {
		this.group = group;
		return this;
	}
}
