package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.game.interfaces.IGame;

public class GamePausePostEvent extends GameEvent {

	/**
	 * Creates a event thrown when a game is paused.
	 * 
	 * @param game The paused game.
	 */
	public GamePausePostEvent(IGame game) {
		super(game);
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("game=" + getGame().getName());
		return String.format("%s_%s", getName(), joiner);
	}
}
