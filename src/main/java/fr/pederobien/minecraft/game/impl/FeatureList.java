package fr.pederobien.minecraft.game.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import fr.pederobien.minecraft.game.event.FeatureListFeatureAddPostEvent;
import fr.pederobien.minecraft.game.event.FeatureListFeatureRemovePostEvent;
import fr.pederobien.minecraft.game.exceptions.FeatureAlreadyRegisteredException;
import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IFeatureList;
import fr.pederobien.utils.event.EventManager;

public class FeatureList implements IFeatureList {
	private String name;
	private Map<String, IFeature> features;
	private Lock lock;

	public FeatureList(String name) {
		this.name = name;
		features = new LinkedHashMap<String, IFeature>();
		lock = new ReentrantLock(true);
	}

	@Override
	public Iterator<IFeature> iterator() {
		return features.values().iterator();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void add(IFeature feature) {
		addFeature(feature);
		EventManager.callEvent(new FeatureListFeatureAddPostEvent(this, feature));
	}

	@Override
	public IFeature remove(String name) {
		IFeature feature = removeFeature(name);
		if (feature != null)
			EventManager.callEvent(new FeatureListFeatureRemovePostEvent(this, feature));
		return feature;
	}

	@Override
	public boolean remove(IFeature feature) {
		return remove(feature.getName()) != null;
	}

	@Override
	public void clear() {
		lock.lock();
		try {
			Set<String> names = new HashSet<String>(features.keySet());
			for (String name : names)
				EventManager.callEvent(new FeatureListFeatureRemovePostEvent(this, features.remove(name)));
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Optional<IFeature> getFeature(String name) {
		return Optional.ofNullable(features.get(name));
	}

	@Override
	public Stream<IFeature> stream() {
		return features.values().stream();
	}

	@Override
	public List<IFeature> toList() {
		return new ArrayList<IFeature>(features.values());
	}

	/**
	 * Thread safe operation that adds a feature to the features list.
	 * 
	 * @param feature The feature to add.
	 * 
	 * @throws FeatureAlreadyRegisteredException if a feature is already registered for the feature name.
	 */
	private void addFeature(IFeature feature) {
		lock.lock();
		try {
			if (features.get(feature.getName()) != null)
				throw new FeatureAlreadyRegisteredException(this, feature);

			features.put(feature.getName(), feature);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Thread safe operation that removes a features from the features list.
	 * 
	 * @param feature The feature to remove.
	 * 
	 * @return The feature associated to the given name if registered, null otherwise.
	 */
	private IFeature removeFeature(String name) {
		lock.lock();
		try {
			return features.remove(name);
		} finally {
			lock.unlock();
		}
	}
}
