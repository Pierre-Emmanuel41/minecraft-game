package fr.pederobien.minecraft.game.impl;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import fr.pederobien.minecraft.game.event.FeatureEnableChangePostEvent;
import fr.pederobien.minecraft.game.event.FeaturePausePostEvent;
import fr.pederobien.minecraft.game.event.FeatureResumePostEvent;
import fr.pederobien.minecraft.game.event.FeatureStartPostEvent;
import fr.pederobien.minecraft.game.event.FeatureStopPostEvent;
import fr.pederobien.minecraft.game.interfaces.IFeature;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.utils.event.EventManager;

public class Feature implements IFeature {
	private String name;
	private IGame game;
	private PausableState state;
	private TabExecutor startTabExecutor;
	private boolean isEnable;

	/**
	 * Creates an simple feature.
	 * 
	 * @param name             The feature name.
	 * @param game             The feature game.
	 * @param startTabExecutor The tab executor in order to run specific treatment according to argument line before starting the
	 *                         feature.
	 */
	public Feature(String name, IGame game, TabExecutor startTabExecutor) {
		this.name = name;
		this.game = game;
		this.startTabExecutor = startTabExecutor;
		state = PausableState.NOT_STARTED;
	}

	/**
	 * Creates an simple feature.
	 * 
	 * @param name             The feature name.
	 * @param game             The feature game.
	 * @param startTabExecutor The tab executor in order to run specific treatment according to argument line before starting the
	 *                         feature.
	 */
	public Feature(String name, IGame game) {
		this(name, game, new DefaultStartTabExecutor());
	}

	@Override
	public IGame getGame() {
		return game;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void start() {
		if (isEnable) {
			if (state == PausableState.STARTED || state == PausableState.PAUSED)
				return;

			state = PausableState.STARTED;
			EventManager.callEvent(new FeatureStartPostEvent(this));
		}
	}

	@Override
	public void stop() {
		if (state == PausableState.NOT_STARTED)
			return;

		state = PausableState.NOT_STARTED;
		EventManager.callEvent(new FeatureStopPostEvent(this));
	}

	@Override
	public void pause() {
		if (state == PausableState.PAUSED || state == PausableState.NOT_STARTED)
			return;

		state = PausableState.PAUSED;
		EventManager.callEvent(new FeaturePausePostEvent(this));
	}

	@Override
	public void resume() {
		if (state == PausableState.STARTED || state == PausableState.NOT_STARTED)
			return;

		state = PausableState.STARTED;
		EventManager.callEvent(new FeatureResumePostEvent(this));
	}

	@Override
	public boolean isEnable() {
		return isEnable;
	}

	@Override
	public void setEnabled(boolean isEnable) {
		if (this.isEnable == isEnable)
			return;

		this.isEnable = isEnable;
		EventManager.callEvent(new FeatureEnableChangePostEvent(this));
	}

	@Override
	public TabExecutor getStartTabExecutor() {
		return startTabExecutor;
	}

	@Override
	public PausableState getState() {
		return state;
	}

	private static class DefaultStartTabExecutor implements TabExecutor {

		@Override
		public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
			return new ArrayList<String>();
		}

		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			return true;
		}
	}
}
