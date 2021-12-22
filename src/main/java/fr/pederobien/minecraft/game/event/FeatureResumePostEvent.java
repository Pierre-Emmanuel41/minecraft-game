package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.IFeature;

public class FeatureResumePostEvent extends FeatureEvent {

	/**
	 * Creates an event thrown when a feature is resumed.
	 * 
	 * @param feature The resumed feature.
	 */
	public FeatureResumePostEvent(IFeature feature) {
		super(feature);
	}
}
