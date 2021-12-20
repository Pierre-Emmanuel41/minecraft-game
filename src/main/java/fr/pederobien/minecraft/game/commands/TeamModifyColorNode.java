package fr.pederobien.minecraft.game.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.managers.EColor;

public class TeamModifyColorNode extends TeamNode {

	protected TeamModifyColorNode(TeamCommandTree tree) {
		super(tree, "color", EGameCode.TEAM__MODIFY_COLOR__EXPLANATION);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			List<String> colors = new ArrayList<String>();
			for (EColor color : EColor.values())
				if (!getTree().getExceptedColors().contains(color))
					colors.add(color.toString());
			return colors;
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		EColor color;
		try {
			color = EColor.getByColorName(args[0]);
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EGameCode.TEAM__MODIFY_COLOR__COLOR_IS_MISSING).build(getTree().getTeam().getName()));
			return false;
		}

		if (getTree().getExceptedColors().contains(color)) {
			send(eventBuilder(sender, EGameCode.TEAM__MODIFY_COLOR__COLOR_IS_ALREADY_USED).build(getTree().getTeam().getName(), color));
			return false;
		}

		EColor oldColor = getTree().getTeam().getColor();
		getTree().getTeam().setColor(color);
		send(eventBuilder(sender, EGameCode.TEAM__MODIFY_COLOR__TEAM_COLOR_UPDATED, oldColor, getTree().getTeam().getColor().getColoredColorName()));
		return true;
	}
}
