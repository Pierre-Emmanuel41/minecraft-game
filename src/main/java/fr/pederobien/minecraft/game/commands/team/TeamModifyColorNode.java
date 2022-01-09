package fr.pederobien.minecraft.game.commands.team;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.managers.EColor;

public class TeamModifyColorNode extends TeamNode {
	private List<EColor> exceptedColors;

	/**
	 * Creates a node that modifies the color of a team.
	 * 
	 * @param tree The tree that contains a reference to the team to modify.
	 */
	protected TeamModifyColorNode(TeamCommandTree tree) {
		super(tree, "color", EGameCode.TEAM__MODIFY_COLOR__EXPLANATION, team -> team != null);
		exceptedColors = new ArrayList<EColor>();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			List<String> colors = new ArrayList<String>();
			for (EColor color : EColor.values())
				if (!exceptedColors.contains(color))
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
			send(eventBuilder(sender, EGameCode.TEAM__MODIFY_COLOR__COLOR_IS_MISSING, getTree().getTeam().getName()));
			return false;
		}

		if (color == null) {
			send(eventBuilder(sender, EGameCode.TEAM__NEW__COLOR_NOT_FOUND, args[0]));
			return false;
		}

		if (exceptedColors.contains(color)) {
			send(eventBuilder(sender, EGameCode.TEAM__MODIFY_COLOR__COLOR_IS_ALREADY_USED, getTree().getTeam().getName(), color));
			return false;
		}

		EColor oldColor = getTree().getTeam().getColor();
		getTree().getTeam().setColor(color);
		sendSuccessful(sender, EGameCode.TEAM__MODIFY_COLOR__TEAM_COLOR_UPDATED, oldColor.getColoredColorName(), getTree().getTeam().getColor().getColoredColorName());
		return true;
	}

	/**
	 * @return The list of colors that should not be used for a team.
	 */
	public List<EColor> getExceptedColors() {
		return exceptedColors;
	}
}
