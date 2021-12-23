package fr.pederobien.minecraft.game.impl;

import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraft.game.event.GamePausePostEvent;
import fr.pederobien.minecraft.game.event.GameResumePostEvent;
import fr.pederobien.minecraft.game.event.GameStartPostEvent;
import fr.pederobien.minecraft.game.event.GameStopPostEvent;
import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IFeatureList;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.ITeamList;
import fr.pederobien.utils.event.EventManager;

public class Game implements IGame {
	private String name;
	private Plugin plugin;
	private TabExecutor startTabExecutor, stopTabExecutor;
	private ITeamList teams;
	private IFeatureList features;
	private EGameState state;

	/**
	 * Creates a game.
	 * 
	 * @param name   The game name.
	 * @param plugin The plugin associated to this name.
	 */
	public Game(String name, Plugin plugin) {
		this.name = name;
		state = EGameState.NOT_STARTED;
	}

	@Override
	public void start() {
		for (IFeature feature : getFeatures())
			feature.start();

		state = EGameState.STARTED;
		EventManager.callEvent(new GameStartPostEvent(this));
	}

	@Override
	public void stop() {
		for (IFeature feature : getFeatures())
			feature.stop();

		state = EGameState.NOT_STARTED;
		EventManager.callEvent(new GameStopPostEvent(this));
	}

	@Override
	public void pause() {
		for (IFeature feature : getFeatures())
			feature.pause();

		state = EGameState.PAUSED;
		EventManager.callEvent(new GamePausePostEvent(this));
	}

	@Override
	public void resume() {
		for (IFeature feature : getFeatures())
			feature.resume();

		state = EGameState.STARTED;
		EventManager.callEvent(new GameResumePostEvent(this));
	}

	@Override
	public Plugin getPlugin() {
		return plugin;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ITeamList getTeams() {
		return teams;
	}

	@Override
	public IFeatureList getFeatures() {
		return features;
	}

	@Override
	public TabExecutor getStartTabExecutor() {
		return startTabExecutor;
	}

	@Override
	public TabExecutor getStopTabExecutor() {
		return stopTabExecutor;
	}

	@Override
	public EGameState getState() {
		return state;
	}
}
