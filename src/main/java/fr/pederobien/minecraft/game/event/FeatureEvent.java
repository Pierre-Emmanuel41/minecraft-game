package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.IFeature;

public class FeatureEvent extends GameEvent {
	private IFeature feature;

	/**
	 * Creates a feature event
	 * 
	 * @param feature The feature source involved in this event.
	 */
	public FeatureEvent(IFeature feature) {
		this.feature = feature;
	}

	/**
	 * @return The feature involved in this event.
	 */
	public IFeature getFeature() {
		return feature;
	}
}
