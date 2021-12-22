package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

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

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("feature=" + getFeature().getName());
		return String.format("%s_%s", getName(), joiner);
	}
}
