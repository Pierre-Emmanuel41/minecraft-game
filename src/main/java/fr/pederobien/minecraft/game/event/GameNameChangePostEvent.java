package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.game.interfaces.IGame;

public class GameNameChangePostEvent extends GameEvent {
	private String oldName;

	/**
	 * Creates an event thrown when the name of a game has changed.
	 * 
	 * @param game    The game whose the name has changed.
	 * @param oldName The old game name.
	 */
	public GameNameChangePostEvent(IGame game, String oldName) {
		super(game);
		this.oldName = oldName;
	}

	/**
	 * @return The old game name.
	 */
	public String getOldName() {
		return oldName;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("game=" + getGame().getName());
		joiner.add("oldName=" + getOldName());
		return String.format("%s_%s", getName(), joiner);
	}

}
