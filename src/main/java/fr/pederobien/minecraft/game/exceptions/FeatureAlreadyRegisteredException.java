package fr.pederobien.minecraft.game.exceptions;

import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IFeatureList;

public class FeatureAlreadyRegisteredException extends FeatureListException {
	private static final long serialVersionUID = 1L;
	private IFeature feature;

	public FeatureAlreadyRegisteredException(IFeatureList list, IFeature feature) {
		super(String.format("A feature %s is already registered", feature.getName()), list);
		this.feature = feature;
	}

	/**
	 * @return The registered feature.
	 */
	public IFeature getFeature() {
		return feature;
	}
}
