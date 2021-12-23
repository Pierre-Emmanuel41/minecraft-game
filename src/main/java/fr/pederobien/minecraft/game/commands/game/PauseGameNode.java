package fr.pederobien.minecraft.game.commands.game;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.impl.EGameState;
import fr.pederobien.minecraft.game.interfaces.IGame;

public class PauseGameNode extends GameNode {

	protected PauseGameNode(IGame game) {
		super(game, "pausegame", EGameCode.PAUSE_GAME__EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (getGame() == null) {
			send(eventBuilder(sender, EGameCode.PAUSE_GAME__NO_GAME_TO_PAUSE).build());
			return false;
		}

		if (getGame().getState() == EGameState.NOT_STARTED) {
			send(eventBuilder(sender, EGameCode.PAUSE_GAME__GAME_NOT_STARTED, getGame().getName()));
			return false;
		}

		if (getGame().getState() == EGameState.STARTED) {
			getGame().pause();
			send(EGameCode.PAUSE_GAME__PAUSING_GAME);
		} else {
			getGame().resume();
			send(EGameCode.PAUSE_GAME__RESUMING_GAME);
		}
		return true;
	}
}
