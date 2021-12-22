package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

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

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("feature=" + getFeature().getName());
		return String.format("%s_%s", getName(), joiner);
	}
}
