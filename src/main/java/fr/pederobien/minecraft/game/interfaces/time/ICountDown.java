package fr.pederobien.minecraft.game.interfaces.time;

import java.time.LocalTime;

public interface ICountDown {

	/**
	 * @return The initial value associated to this count down.
	 */
	int getInitialValue();

	/**
	 * @return The current value associated to this count down.
	 */
	int getCurrentValue();

	/**
	 * Decrease the value of this count down. If the value equals zero then the underlying current value is reinitialized to the
	 * initial value.
	 * 
	 * @param time the current absolute time.
	 */
	void signal(LocalTime time);
}
