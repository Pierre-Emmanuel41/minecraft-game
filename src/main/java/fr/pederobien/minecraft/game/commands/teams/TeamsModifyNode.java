package fr.pederobien.minecraft.game.commands.teams;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.commands.team.TeamCommandTree;
import fr.pederobien.minecraft.game.commands.team.TeamModifyColorNode;
import fr.pederobien.minecraft.game.commands.team.TeamModifyNameNode;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class TeamsModifyNode extends TeamsNode {
	private TeamCommandTree teamTree;

	/**
	 * Creates a node that modify characteristics of a team.
	 * 
	 * @param teams    The list of teams associated to this node.
	 * @param teamTree The command tree in order create or modify a team.
	 */
	protected TeamsModifyNode(Supplier<ITeamList> teams, TeamCommandTree teamTree) {
		super(teams, "modify", EGameCode.TEAMS__MODIFY__EXPLANATION, t -> t != null);
		this.teamTree = teamTree;

		add(teamTree.getModifyNode().getNameNode());
		add(teamTree.getModifyNode().getColorNode());
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
			return super.onTabComplete(sender, command, alias, extract(args, 1));
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
		return super.onCommand(sender, command, label, extract(args, 1));
	}

	/**
	 * @return The node that modifies the name of a team.
	 */
	public TeamModifyNameNode getNameNode() {
		return teamTree.getModifyNode().getNameNode();
	}

	/**
	 * @return The node that modifies the color of a team.
	 */
	public TeamModifyColorNode getColorNode() {
		return teamTree.getModifyNode().getColorNode();
	}

	private void updateExceptedList() {
		teamTree.getModifyNode().getNameNode().getExceptedNames().clear();
		teamTree.getModifyNode().getColorNode().getExceptedColors().clear();
		teamTree.getAddPlayerNode().getExceptedPlayers().clear();
		getTeams().toList().forEach(t -> {
			teamTree.getModifyNode().getNameNode().getExceptedNames().add(t.getName());
			teamTree.getModifyNode().getColorNode().getExceptedColors().add(t.getColor());
			for (Player player : t.getPlayers().toList())
				teamTree.getAddPlayerNode().getExceptedPlayers().add(player);
		});
	}
}
