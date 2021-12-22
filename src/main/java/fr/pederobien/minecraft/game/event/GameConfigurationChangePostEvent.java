package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.IGameConfiguration;

public class GameConfigurationChangePostEvent extends GameEvent {
	private IGameConfiguration oldConfiguration;

	/**
	 * Creates an event thrown when the configuration of a game as changed.
	 * 
	 * @param game             The game involved in this event.
	 * @param oldConfiguration The old game configuration.
	 */
	public GameConfigurationChangePostEvent(IGame game, IGameConfiguration oldConfiguration) {
		super(game);
		this.oldConfiguration = oldConfiguration;
	}

	/**
	 * @return The old game configuration.
	 */
	public IGameConfiguration getOldConfiguration() {
		return oldConfiguration;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("game=" + getGame().getName());
		joiner.add("oldConfiguration=" + oldConfiguration.getName());
		joiner.add("newConfiguration=" + getGame().getConfig().getName());
		return String.format("%s_%s", getName(), joiner);
	}
}
