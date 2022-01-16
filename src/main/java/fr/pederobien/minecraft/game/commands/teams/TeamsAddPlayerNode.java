package fr.pederobien.minecraft.game.commands.teams;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.commands.team.TeamAddPlayerNode;
import fr.pederobien.minecraft.game.commands.team.TeamCommandTree;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class TeamsAddPlayerNode extends TeamsNode {
	private TeamCommandTree teamTree;

	protected TeamsAddPlayerNode(Supplier<ITeamList> teams, TeamCommandTree teamTree) {
		super(teams, "player", EGameCode.TEAM__ADD_PLAYER__EXPLANATION, t -> t != null);
		this.teamTree = teamTree;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return emptyList();
		case 1:
			return filter(getTeams().stream().map(team -> team.getName()), args);
		default:
			Optional<ITeam> optTeam = getTeams().getTeam(args[0]);
			if (!optTeam.isPresent())
				return emptyList();

			updateExceptedList();
			teamTree.setTeam(optTeam.get());
			return teamTree.getAddPlayerNode().onTabComplete(sender, command, alias, extract(args, 1));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Optional<ITeam> optTeam;
		try {
			optTeam = getTeams().getTeam(args[0]);
			if (!optTeam.isPresent()) {
				send(eventBuilder(sender, EGameCode.TEAMS__MODIFY__TEAM_NOT_FOUND, args[0]));
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

		updateExceptedList();
		teamTree.setTeam(optTeam.get());
		return teamTree.getAddPlayerNode().onCommand(sender, command, label, extract(args, 1));
	}

	/**
	 * @return The node that add players to the current team.
	 */
	public TeamAddPlayerNode getAddPlayerNode() {
		return teamTree.getAddPlayerNode();
	}

	private void updateExceptedList() {
		teamTree.getAddPlayerNode().getExceptedPlayers().clear();
		getTeams().toList().forEach(t -> {
			for (Player player : t.getPlayers().toList())
				teamTree.getAddPlayerNode().getExceptedPlayers().add(player);
		});
	}
}
