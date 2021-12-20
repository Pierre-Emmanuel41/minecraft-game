package fr.pederobien.minecraft.game.commands.game;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.impl.EGameState;

public class PauseGameNode extends GameNode {

	protected PauseGameNode(GameCommandTree tree) {
		super(tree, "pausegame", EGameCode.PAUSE_GAME__EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (getTree().getGame() == null) {
			send(eventBuilder(sender, EGameCode.PAUSE_GAME__NO_GAME_TO_PAUSE).build());
			return false;
		}

		if (getTree().getGame().getState() == EGameState.NOT_STARTED) {
			send(eventBuilder(sender, EGameCode.PAUSE_GAME__GAME_NOT_STARTED, getTree().getGame().getName()));
			return false;
		}

		if (getTree().getGame().getState() == EGameState.STARTED) {
			getTree().getGame().pause();
			send(EGameCode.PAUSE_GAME__PAUSING_GAME);
		} else {
			getTree().getGame().resume();
			send(EGameCode.PAUSE_GAME__RESUMING_GAME);
		}
		return true;
	}
}
