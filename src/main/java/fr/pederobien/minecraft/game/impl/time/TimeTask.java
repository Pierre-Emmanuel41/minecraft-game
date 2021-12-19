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
	private State state;
	int taskId;

	public TimeTask(Plugin plugin) {
		this.plugin = plugin;
		totalTime = gameTime = pauseTime = LocalTime.of(0, 0, 0);

		state = State.NOT_STARTED;
	}

	@Override
	public void start() {
		if (state != State.NOT_STARTED)
			return;

		state = State.STARTED;
		taskId = BukkitManager.getScheduler().runTaskTimer(plugin, this, 0, 20).getTaskId();
		EventManager.callEvent(new TimeTaskStartPostEvent(this));
	}

	@Override
	public void stop() {
		if (state != State.STARTED || state != State.PAUSED)
			return;

		state = State.NOT_STARTED;
		BukkitManager.getScheduler().cancelTask(taskId);
		EventManager.callEvent(new TimeTaskStartPostEvent(this));
	}

	@Override
	public void pause() {
		if (state != State.NOT_STARTED)
			return;

		state = State.PAUSED;
		EventManager.callEvent(new TimeTaskPausePostEvent(this));
	}

	@Override
	public void resume() {
		if (state != State.PAUSED)
			return;

		state = State.STARTED;
		EventManager.callEvent(new TimeTaskResumePostEvent(this));
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
		if (state == State.NOT_STARTED)
			return;

		totalTime = totalTime.plusSeconds(1);

		if (state == State.PAUSED)
			pauseTime = pauseTime.plusSeconds(1);
		else
			gameTime = gameTime.plusSeconds(1);

		EventManager.callEvent(new TimeTaskTimeChangePostEvent(this));
	}

	private enum State {
		NOT_STARTED, STARTED, PAUSED;
	}
}
