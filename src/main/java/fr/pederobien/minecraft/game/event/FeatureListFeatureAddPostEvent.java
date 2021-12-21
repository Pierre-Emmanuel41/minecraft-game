package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IFeatureList;

public class FeatureListFeatureAddPostEvent extends FeatureListEvent {
	private IFeature feature;

	/**
	 * Creates an event thrown when a feature has been added to a features list.
	 * 
	 * @param list    The list to which a feature has been added.
	 * @param feature The added feature.
	 */
	public FeatureListFeatureAddPostEvent(IFeatureList list, IFeature feature) {
		super(list);
		this.feature = feature;
	}

	/**
	 * @return The added feature.
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
