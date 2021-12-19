package fr.pederobien.minecraft.game.interfaces.time;

import java.time.LocalTime;

public interface IObsTimeLine {

	/**
	 * @return The count down associated to this observer.
	 */
	ICountDown getCountDown();

	/**
	 * Get the next time at which this observer should be notified. This time correspond to the absolute time (from the beginning of
	 * the game) at which the observer wants to be notified. If the returned time is null then this observer will never be notified
	 * again.
	 * 
	 * @return The new time at which this observer will be notified.
	 */
	LocalTime getNextTime();
}
