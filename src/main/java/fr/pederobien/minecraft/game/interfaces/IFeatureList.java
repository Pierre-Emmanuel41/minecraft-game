package fr.pederobien.minecraft.game.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import fr.pederobien.minecraft.game.exceptions.FeatureAlreadyRegisteredException;

public interface IFeatureList extends Iterable<IFeature> {

	/**
	 * @return The name of this list.
	 */
	String getName();

	/**
	 * Appends the given feature to this list.
	 * 
	 * @param feature The feature to add.
	 * 
	 * @throws FeatureAlreadyRegisteredException If a feature is already registered for the feature name.
	 */
	void add(IFeature feature);

	/**
	 * Removes the feature associated to the given name.
	 * 
	 * @param name The feature name to remove.
	 * 
	 * @return The removed feature if registered, null otherwise.
	 */
	IFeature remove(String name);

	/**
	 * Removes the given feature from this list.
	 * 
	 * @param feature The feature to remove.
	 * 
	 * @return True if the feature was registered, false otherwise.
	 */
	boolean remove(IFeature feature);

	/**
	 * Removes all registered features. It also clear each registered players.
	 */
	void clear();

	/**
	 * Get the feature associated to the given name.
	 * 
	 * @param name The feature name.
	 * 
	 * @return An optional that contains the feature if registered, an empty optional otherwise.
	 */
	Optional<IFeature> getFeature(String name);

	/**
	 * @return a sequential {@code Stream} over the elements in this collection.
	 */
	Stream<IFeature> stream();

	/**
	 * @return A copy of the underlying list.
	 */
	List<IFeature> toList();
}
