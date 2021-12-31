package fr.pederobien.minecraft.game.impl;

import org.bukkit.command.TabExecutor;

import fr.pederobien.minecraft.game.event.FeatureEnableChangePostEvent;
import fr.pederobien.minecraft.game.event.FeaturePausePostEvent;
import fr.pederobien.minecraft.game.event.FeatureResumePostEvent;
import fr.pederobien.minecraft.game.event.FeatureStartPostEvent;
import fr.pederobien.minecraft.game.event.FeatureStopPostEvent;
import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.utils.event.EventManager;

public class Feature implements IFeature {
	private String name;
	private PausableState state;
	private TabExecutor startTabExecutor;
	private boolean isEnable;

	/**
	 * Creates an simple feature.
	 * 
	 * @param name The feature name.
	 */
	public Feature(String name, TabExecutor startTabExecutor) {
		this.name = name;
		this.startTabExecutor = startTabExecutor;
		state = PausableState.NOT_STARTED;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void start() {
		if (isEnable) {
			state = PausableState.STARTED;
			EventManager.callEvent(new FeatureStartPostEvent(this));
		}
	}

	@Override
	public void stop() {
		state = PausableState.NOT_STARTED;
		EventManager.callEvent(new FeatureStopPostEvent(this));
	}

	@Override
	public void pause() {
		state = PausableState.PAUSED;
		EventManager.callEvent(new FeaturePausePostEvent(this));
	}

	@Override
	public void resume() {
		state = PausableState.STARTED;
		EventManager.callEvent(new FeatureResumePostEvent(this));
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

	@Override
	public TabExecutor getStartTabExecutor() {
		return startTabExecutor;
	}

	@Override
	public PausableState getState() {
		return state;
	}
}
