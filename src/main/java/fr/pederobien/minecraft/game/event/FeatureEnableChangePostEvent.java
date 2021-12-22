package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

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

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("feature=" + getFeature().getName());
		return String.format("%s_%s", getName(), joiner);
	}
}
