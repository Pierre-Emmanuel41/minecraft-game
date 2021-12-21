package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.IFeature;

public class FeatureEnableChangePostEvent extends FeatureEvent {

	/**
	 * Creates an event thrown when the enable status of the given feature has changed.
	 * 
	 * @param feature The feature whose the enable status has changed.
	 */
	public FeatureEnableChangePostEvent(IFeature feature) {
		super(feature);
	}
}
