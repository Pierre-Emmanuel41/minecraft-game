package fr.pederobien.minecraft.game.commands.game;

import java.util.List;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IGame;

public class StartGameNode extends GameNode {

	/**
	 * Creates a node to start a game.
	 * 
	 * @param game The game associated to this node.
	 */
	protected StartGameNode(Supplier<IGame> game) {
		super(game, "startgame", EGameCode.START_GAME__EXPLANATION);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (getGame() == null)
			return emptyList();

		return getGame().getStartTabExecutor().onTabComplete(sender, command, alias, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (getGame() == null) {
			send(eventBuilder(sender, EGameCode.START_GAME__NO_GAME_TO_START).build());
			return false;
		}

		if (getGame().getStartTabExecutor().onCommand(sender, command, label, args)) {
			getGame().start();

			sendSuccessful(sender, EGameCode.START_GAME__STARTING_GAME, getGame().getName());
			return true;
		}

		return false;
	}
}
