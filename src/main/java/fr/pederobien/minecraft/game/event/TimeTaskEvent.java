package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.game.interfaces.time.ITimeTask;

public class TimeTaskEvent extends ProjectGameEvent {
	private ITimeTask task;

	/**
	 * Creates a time task event.
	 * 
	 * @param task The task source involved in this event.
	 */
	public TimeTaskEvent(ITimeTask task) {
		this.task = task;
	}

	/**
	 * @return The time task involved in this event.
	 */
	public ITimeTask getTask() {
		return task;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",", "{", "}");
		joiner.add("gameTime=" + getTask().getGameTime());
		joiner.add("pauseTime=" + getTask().getPauseTime());
		joiner.add("totalTime=" + getTask().getTotalTime());
		return String.format("%s_%s", getName(), joiner);
	}
}
