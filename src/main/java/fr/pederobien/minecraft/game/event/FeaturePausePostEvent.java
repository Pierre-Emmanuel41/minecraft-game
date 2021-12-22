package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.IFeature;

public class FeaturePausePostEvent extends FeatureEvent {

	/**
	 * Creates an event thrown when a feature is paused.
	 * 
	 * @param feature The paused feature.
	 */
	public FeaturePausePostEvent(IFeature feature) {
		super(feature);
	}
}
