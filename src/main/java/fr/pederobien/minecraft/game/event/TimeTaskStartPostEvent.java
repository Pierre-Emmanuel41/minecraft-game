package fr.pederobien.minecraft.game.event;

import fr.pederobien.minecraft.game.interfaces.time.ITimeTask;

public class TimeTaskStartPostEvent extends TimeTaskEvent {

	/**
	 * Creates an event thrown when a time task has been started.
	 * 
	 * @param task The started task.
	 */
	public TimeTaskStartPostEvent(ITimeTask task) {
		super(task);
	}
}
