package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.IFeature;

public class FeatureStopPostEvent extends FeatureEvent {

	/**
	 * Creates an event thrown when a feature is stopped.
	 * 
	 * @param feature The stopped feature.
	 */
	public FeatureStopPostEvent(IFeature feature) {
		super(feature);
	}
}
