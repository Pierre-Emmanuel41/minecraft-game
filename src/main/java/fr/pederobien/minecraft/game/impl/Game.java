package fr.pederobien.minecraft.game.impl;

import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.IGameConfiguration;

public class Game implements IGame {
	private String name;
	private IGameConfiguration configuration;

	/**
	 * Creates a game.
	 * 
	 * @param name          The game name.
	 * @param configuration The game configuration.
	 */
	public Game(String name, IGameConfiguration configuration) {
		this.name = name;
		this.configuration = configuration;
	}

	/**
	 * Creates a game.
	 * 
	 * @param name The game name.
	 */
	public Game(String name) {
		this.name = name;
	}

	@Override
	public void start() {
		if (configuration == null)
			throw new IllegalStateException("There is no configuration associated to this game");
	}

	@Override
	public void stop() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IGameConfiguration getConfig() {
		return configuration;
	}

	@Override
	public void setConfig(IGameConfiguration configuration) {
		this.configuration = configuration;
	}
}
