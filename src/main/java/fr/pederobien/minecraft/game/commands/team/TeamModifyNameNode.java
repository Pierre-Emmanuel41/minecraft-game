package fr.pederobien.minecraft.game.commands.team;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;

public class TeamModifyNameNode extends TeamNode {

	protected TeamModifyNameNode(TeamCommandTree tree) {
		super(tree, "name", EGameCode.TEAM__MODIFY_NAME__EXPLANATION, team -> team != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(sender, EGameCode.NAME__COMPLETION));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EGameCode.TEAM__MODIFY_NAME__NAME_IS_MISSING).build(getTree().getTeam().getName()));
			return false;
		}

		if (getTree().getExceptedNames().contains(name)) {
			send(eventBuilder(sender, EGameCode.TEAM__MODIFY_NAME__NAME_IS_ALREADY_USED).build(getTree().getTeam().getName(), name));
			return false;
		}

		String oldName = getTree().getTeam().getColoredName();
		getTree().getTeam().setName(name);
		send(eventBuilder(sender, EGameCode.TEAM__MODIFY_NAME__TEAM_NAME_UPDATED, oldName, getTree().getTeam().getColoredName()));
		return true;
	}
}
