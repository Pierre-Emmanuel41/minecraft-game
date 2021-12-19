package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.time.ITimeTask;

public class TimeTaskStopPostEvent extends TimeTaskEvent {

	/**
	 * Creates an event thrown when a time task has been stopped.
	 * 
	 * @param task The stopped task.
	 */
	public TimeTaskStopPostEvent(ITimeTask task) {
		super(task);
	}
}
