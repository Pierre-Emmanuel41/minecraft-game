package fr.pederobien.minecraft.game.exceptions;

import fr.pederobien.minecraft.game.interfaces.IFeatureList;

public class FeatureListException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private IFeatureList list;

	public FeatureListException(String message, IFeatureList list) {
		super(message);
		this.list = list;
	}

	/**
	 * @return The list involved in this exception.
	 */
	public IFeatureList getList() {
		return list;
	}
}
