package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IFeatureList;

public class FeatureListFeatureRemovePostEvent extends FeatureListEvent {
	private IFeature feature;

	/**
	 * Creates an event thrown when a feature has been removed from a features list.
	 * 
	 * @param list    The list from which a feature has been removed.
	 * @param feature The removed feature.
	 */
	public FeatureListFeatureRemovePostEvent(IFeatureList list, IFeature feature) {
		super(list);
		this.feature = feature;
	}

	/**
	 * @return The removed feature.
	 */
	public IFeature getFeature() {
		return feature;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("list=" + getList().getName());
		joiner.add("feature=" + feature.getName());
		return String.format("%s_%s", getName(), joiner.toString());
	}
}
