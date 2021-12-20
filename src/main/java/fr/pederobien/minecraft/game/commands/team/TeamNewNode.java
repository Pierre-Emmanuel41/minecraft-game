package fr.pederobien.minecraft.game.commands.team;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.event.TeamNewPostEvent;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.impl.Team;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.utils.event.EventManager;

public class TeamNewNode extends TeamNode {

	protected TeamNewNode(TeamCommandTree tree) {
		super(tree, "new", EGameCode.TEAM__NEW__EXPLANATION, team -> true);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return asList(getMessage(sender, EGameCode.NAME__COMPLETION));
		case 1:
			List<String> colors = new ArrayList<String>();
			for (EColor color : EColor.values())
				if (!getTree().getExceptedColors().contains(color))
					colors.add(color.toString());
			return colors;
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

		if (getTree().getExceptedNames().contains(name)) {
			send(eventBuilder(sender, EGameCode.TEAM__NEW__NAME_ALREADY_USED).build(name));
			return false;
		}

		EColor color;
		try {
			color = EColor.getByColorName(args[1]);
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EGameCode.TEAM__NEW__COLOR_IS_MISSING).build());
			return false;
		}

		if (getTree().getExceptedColors().contains(color)) {
			send(eventBuilder(sender, EGameCode.TEAM__NEW__COLOR_ALREADY_USED).build(color.getColoredColorName()));
			return false;
		}

		getTree().setTeam(Team.of(name, color));
		send(eventBuilder(sender, EGameCode.TEAM__NEW__TEAM_CREATED, getTree().getTeam().getColoredName()));
		EventManager.callEvent(new TeamNewPostEvent(getTree().getTeam(), getTree()));
		return true;
	}
}
