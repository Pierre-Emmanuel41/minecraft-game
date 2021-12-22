package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.IGame;

public class GameEvent extends ProjectGameEvent {
	private IGame game;

	/**
	 * Creates a game event.
	 * 
	 * @param game The game source involved in this event.
	 */
	public GameEvent(IGame game) {
		this.game = game;
	}

	/**
	 * @return The game involved in this event.
	 */
	public IGame getGame() {
		return game;
	}
}
