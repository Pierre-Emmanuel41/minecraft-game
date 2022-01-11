package fr.pederobien.minecraft.game.commands.game;

import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.utils.IPausable.PausableState;

public class PauseGameNode extends GameNode {

	/**
	 * Creates a node to pause/resume a game.
	 * 
	 * @param game The game associated to this node.
	 */
	protected PauseGameNode(Supplier<IGame> game) {
		super(game, "pausegame", EGameCode.PAUSE_GAME__EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (getGame() == null) {
			send(eventBuilder(sender, EGameCode.PAUSE_GAME__NO_GAME_TO_PAUSE).build());
			return false;
		}

		if (getGame().getState() == PausableState.NOT_STARTED) {
			send(eventBuilder(sender, EGameCode.PAUSE_GAME__GAME_NOT_STARTED, getGame().getName()));
			return false;
		}

		if (getGame().getState() == PausableState.STARTED) {
			getGame().pause();
			sendSuccessful(sender, EGameCode.PAUSE_GAME__PAUSING_GAME, getGame().getName());
		} else {
			getGame().resume();
			sendSuccessful(sender, EGameCode.PAUSE_GAME__RESUMING_GAME, getGame().getName());
		}
		return true;
	}
}
