package fr.pederobien.minecraft.game.impl.time;

import java.time.LocalTime;

import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraft.game.event.TimeTaskPausePostEvent;
import fr.pederobien.minecraft.game.event.TimeTaskResumePostEvent;
import fr.pederobien.minecraft.game.event.TimeTaskStartPostEvent;
import fr.pederobien.minecraft.game.event.TimeTaskTimeChangePostEvent;
import fr.pederobien.minecraft.game.interfaces.time.ITimeTask;
import fr.pederobien.minecraft.managers.BukkitManager;
import fr.pederobien.utils.event.EventManager;

public class TimeTask implements ITimeTask, Runnable {
	private Plugin plugin;
	private LocalTime totalTime, gameTime, pauseTime;
	private PausableState state;
	int taskId;

	public TimeTask(Plugin plugin) {
		this.plugin = plugin;
		totalTime = gameTime = pauseTime = LocalTime.of(0, 0, 0);

		state = PausableState.NOT_STARTED;
	}

	@Override
	public void start() {
		if (state != PausableState.NOT_STARTED)
			return;

		state = PausableState.STARTED;
		taskId = BukkitManager.getScheduler().runTaskTimer(plugin, this, 0, 20).getTaskId();
		EventManager.callEvent(new TimeTaskStartPostEvent(this));
	}

	@Override
	public void stop() {
		if (state != PausableState.STARTED || state != PausableState.PAUSED)
			return;

		state = PausableState.NOT_STARTED;
		BukkitManager.getScheduler().cancelTask(taskId);
		EventManager.callEvent(new TimeTaskStartPostEvent(this));
		totalTime = gameTime = pauseTime = LocalTime.of(0, 0, 0);
	}

	@Override
	public void pause() {
		if (state != PausableState.NOT_STARTED)
			return;

		state = PausableState.PAUSED;
		EventManager.callEvent(new TimeTaskPausePostEvent(this));
	}

	@Override
	public void resume() {
		if (state != PausableState.PAUSED)
			return;

		state = PausableState.STARTED;
		EventManager.callEvent(new TimeTaskResumePostEvent(this));
	}

	@Override
	public PausableState getState() {
		return state;
	}

	@Override
	public LocalTime getTotalTime() {
		return totalTime;
	}

	@Override
	public LocalTime getGameTime() {
		return gameTime;
	}

	@Override
	public LocalTime getPauseTime() {
		return pauseTime;
	}

	@Override
	public void run() {
		if (state == PausableState.NOT_STARTED)
			return;

		totalTime = totalTime.plusSeconds(1);

		if (state == PausableState.PAUSED)
			pauseTime = pauseTime.plusSeconds(1);
		else
			gameTime = gameTime.plusSeconds(1);

		EventManager.callEvent(new TimeTaskTimeChangePostEvent(this));
	}
}
