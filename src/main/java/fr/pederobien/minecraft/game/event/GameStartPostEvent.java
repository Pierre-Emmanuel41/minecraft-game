package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.game.interfaces.IGame;

public class GameStartPostEvent extends GameEvent {

	/**
	 * Creates a event thrown when a game is started.
	 * 
	 * @param game The started game.
	 */
	public GameStartPostEvent(IGame game) {
		super(game);
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("game=" + getGame().getName());
		return String.format("%s_%s", getName(), joiner);
	}
}
