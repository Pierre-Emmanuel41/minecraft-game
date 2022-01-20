package fr.pederobien.minecraft.game.impl;

import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IFeatureConfigurable;
import fr.pederobien.minecraft.game.interfaces.IFeatureList;

public class FeaturesGame extends Game implements IFeatureConfigurable {
	private IFeatureList features;

	/**
	 * Creates a game with features.
	 * 
	 * @param name   The game name.
	 * @param plugin The plugin associated to this game.
	 */
	public FeaturesGame(String name, Plugin plugin) {
		super(name, plugin);

		features = new FeatureList(this);
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
	public IFeatureList getFeatures() {
		return features;
	}
}
