package fr.pederobien.minecraft.game.commands.game;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;

public class StopGameNode extends GameNode {

	protected StopGameNode(GameCommandTree tree) {
		super(tree, "stopgame", EGameCode.STOP_GAME__EXPLANATION);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (getTree().getGame() == null)
			return emptyList();

		return getTree().getGame().getConfig().getStartTabExecutor().onTabComplete(sender, command, alias, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (getTree().getGame() == null) {
			send(eventBuilder(sender, EGameCode.STOP_GAME__NO_GAME_TO_STOP).build());
			return false;
		}

		if (getTree().getGame().getConfig().getStartTabExecutor().onCommand(sender, command, label, args)) {
			getTree().getGame().stop();
			send(EGameCode.STOP_GAME__STOPPING_GAME);
			return true;
		}

		return false;
	}
}
