package fr.pederobien.minecraft.game.impl;

import fr.pederobien.minecraft.game.event.FeatureEnableChangePostEvent;
import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.utils.event.EventManager;

public class Feature implements IFeature {
	private String name;
	private boolean isEnable;

	/**
	 * Creates an simple feature.
	 * 
	 * @param name The feature name.
	 */
	public Feature(String name) {
		this.name = name;
	}

	@Override
	public void start() {

	}

	@Override
	public String getName() {
		return name;
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
	public boolean isEnable() {
		return isEnable;
	}

	@Override
	public void setEnabled(boolean isEnable) {
		if (this.isEnable == isEnable)
			return;

		this.isEnable = isEnable;
		EventManager.callEvent(new FeatureEnableChangePostEvent(this));
	}
}
