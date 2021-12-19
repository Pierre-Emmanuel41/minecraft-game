package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.time.ITimeTask;

public class TimeTaskPausePostEvent extends TimeTaskEvent {

	/**
	 * Creates an event thrown when a time task has been paused.
	 * 
	 * @param task The paused time task.
	 */
	public TimeTaskPausePostEvent(ITimeTask task) {
		super(task);
	}
}
