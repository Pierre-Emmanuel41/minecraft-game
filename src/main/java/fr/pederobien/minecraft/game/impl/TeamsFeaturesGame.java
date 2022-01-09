package fr.pederobien.minecraft.game.impl;

import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IFeatureConfigurable;
import fr.pederobien.minecraft.game.interfaces.IFeatureList;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;
import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class TeamsFeaturesGame extends Game implements ITeamConfigurable, IFeatureConfigurable {
	private ITeamList teams;
	private IFeatureList features;

	/**
	 * Creates a game.
	 * 
	 * @param name             The game name.
	 * @param plugin           The plugin associated to this game.
	 * @param startTabExecutor The tab executor in order to run specific treatment according to argument line before starting the
	 *                         game.
	 * @param stopTabExecutor  The tab executor in order to run specific treatment according to argument line before stopping the
	 *                         game.
	 */
	public TeamsFeaturesGame(String name, Plugin plugin, TabExecutor startTabExecutor, TabExecutor stopTabExecutor) {
		super(name, plugin, startTabExecutor, stopTabExecutor);
	}

	/**
	 * Creates a game with teams and features.
	 * 
	 * @param name   The game name.
	 * @param plugin The plugin associated to this game.
	 */
	public TeamsFeaturesGame(String name, Plugin plugin) {
		super(name, plugin);

		teams = new TeamList(name);
		features = new FeatureList(name);
	}

	@Override
	public void start() {
		for (IFeature feature : getFeatures())
			feature.start();

		super.start();
	}

	@Override
	public void stop() {
		for (IFeature feature : getFeatures())
			feature.stop();

		super.stop();
	}

	@Override
	public void pause() {
		for (IFeature feature : getFeatures())
			feature.pause();

		super.pause();
	}

	@Override
	public void resume() {
		for (IFeature feature : getFeatures())
			feature.resume();

		super.resume();
	}

	@Override
	public ITeamList getTeams() {
		return teams;
	}

	@Override
	public IFeatureList getFeatures() {
		return features;
	}
}
