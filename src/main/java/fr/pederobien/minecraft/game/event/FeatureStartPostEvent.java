package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.IFeature;

public class FeatureStartPostEvent extends FeatureEvent {

	/**
	 * Creates an even thrown when a feature is started.
	 * 
	 * @param feature The started feature.
	 */
	public FeatureStartPostEvent(IFeature feature) {
		super(feature);
	}
}
