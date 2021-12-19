package fr.pederobien.minecraft.game.interfaces.time;

import java.time.LocalTime;

public interface ITimeLine {

	/**
	 * @return The internal time task associated to this time line.
	 */
	ITimeTask getTimeTask();

	/**
	 * Register the given observer for the specific time. It is possible to add several observers for the same time. It that case,
	 * each observer are notified. The observer is also registered for each count down value if it is not null.
	 * 
	 * @param time The time at which the observer is notified.
	 * @param obs  The observer to notify.
	 */
	void register(LocalTime time, IObsTimeLine obs);

	/**
	 * Remove the specified observer for the given time. It unregister the observer for each count down value if it is not null.
	 * 
	 * @param time The used as key to remove the observer.
	 * @param obs  The observer to remove.
	 */
	void unregister(LocalTime time, IObsTimeLine obs);
}
