package fr.pederobien.minecraft.game.commands.team;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.impl.Team;
import fr.pederobien.minecraft.managers.EColor;

public class TeamNewNode extends TeamNode {
	private List<String> exceptedNames;
	private List<EColor> exceptedColors;

	/**
	 * Creates a node that creates a new team.
	 * 
	 * @param tree The tree that contains a reference to the team to modify.
	 */
	protected TeamNewNode(TeamCommandTree tree) {
		super(tree, "new", EGameCode.TEAM__NEW__EXPLANATION, team -> true);
		exceptedNames = new ArrayList<String>();
		exceptedColors = new ArrayList<EColor>();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(sender, EGameCode.NAME__COMPLETION));
		case 2:
			List<String> colors = new ArrayList<String>();
			for (EColor color : EColor.values())
				if (!exceptedColors.contains(color))
					colors.add(color.toString());
			return filter(colors.stream(), args);
		}
		return emptyList();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String name;
		try {
			name = args[0];
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EGameCode.TEAM__NEW__NAME_IS_MISSING).build());
			return false;
		}

		if (exceptedNames.contains(name)) {
			send(eventBuilder(sender, EGameCode.TEAM__NEW__NAME_ALREADY_USED, name));
			return false;
		}

		EColor color;
		try {
			color = EColor.getByColorName(args[1]);
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EGameCode.TEAM__NEW__COLOR_IS_MISSING).build());
			return false;
		}

		if (color == null) {
			send(eventBuilder(sender, EGameCode.TEAM__NEW__COLOR_NOT_FOUND, args[1]));
			return false;
		}

		if (exceptedColors.contains(color)) {
			send(eventBuilder(sender, EGameCode.TEAM__NEW__COLOR_ALREADY_USED).build(color.getColoredColorName()));
			return false;
		}

		getTree().setTeam(Team.of(name, color));
		sendSuccessful(sender, EGameCode.TEAM__NEW__TEAM_CREATED, getTree().getTeam().getColoredName());
		return true;
	}

	/**
	 * @return The list of names that should not be used for a team.
	 */
	public List<String> getExceptedNames() {
		return exceptedNames;
	}

	/**
	 * @return The list of colors that should no be used for a team.
	 */
	public List<EColor> getExceptedColors() {
		return exceptedColors;
	}
}
