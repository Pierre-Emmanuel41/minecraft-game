package fr.pederobien.minecraft.game.commands.game;

import java.util.List;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IGame;

public class StopGameNode extends GameNode {

	/**
	 * Creates a node to stop a game.
	 * 
	 * @param game The game to stop.
	 */
	protected StopGameNode(Supplier<IGame> game) {
		super(game, "stopgame", EGameCode.STOP_GAME__EXPLANATION);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (getGame() == null)
			return emptyList();

		return getGame().getStopTabExecutor().onTabComplete(sender, command, alias, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (getGame() == null) {
			send(eventBuilder(sender, EGameCode.STOP_GAME__NO_GAME_TO_STOP).build());
			return false;
		}

		if (getGame().getStopTabExecutor().onCommand(sender, command, label, args)) {
			getGame().stop();
			sendSuccessful(sender, EGameCode.STOP_GAME__STOPPING_GAME, getGame().getName());
			return true;
		}

		return false;
	}
}
