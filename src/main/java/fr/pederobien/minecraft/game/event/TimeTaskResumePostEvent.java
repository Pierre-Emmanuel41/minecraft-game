package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.time.ITimeTask;

public class TimeTaskResumePostEvent extends TimeTaskEvent {

	/**
	 * Creates an event thrown when a time task has been resumed.
	 * 
	 * @param task The resumed task.
	 */
	public TimeTaskResumePostEvent(ITimeTask task) {
		super(task);
	}
}
