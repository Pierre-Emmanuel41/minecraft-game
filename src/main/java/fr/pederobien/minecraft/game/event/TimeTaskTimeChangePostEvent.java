package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.time.ITimeTask;

public class TimeTaskTimeChangePostEvent extends TimeTaskEvent {

	/**
	 * Creates an event thrown when the time of a time task has changed.
	 * 
	 * @param task The time task involved in this event.
	 */
	public TimeTaskTimeChangePostEvent(ITimeTask task) {
		super(task);
	}
}
