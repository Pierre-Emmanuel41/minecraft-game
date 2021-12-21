package fr.pederobien.minecraft.game.interfaces;

import fr.pederobien.utils.IPausable;

public interface IFeature extends IPausable {

	/**
	 * @return The name of this feature.
	 */
	String getName();

	/**
	 * @return If this feature is enabled.
	 */
	boolean isEnable();

	/**
	 * Enable or disable this feature.
	 * 
	 * @param isEnable True in order to enable, false to disable.
	 */
	void setEnabled(boolean isEnable);

}
