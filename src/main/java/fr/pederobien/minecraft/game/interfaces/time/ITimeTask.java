package fr.pederobien.minecraft.game.interfaces.time;

import java.time.LocalTime;

import fr.pederobien.utils.IPausable;

public interface ITimeTask extends IPausable {

	/**
	 * Gets the time since the call to the method {@link #start()}. This time is always increased whatever the state of this task.
	 * 
	 * @return The internal total time managed by this time task.
	 */
	LocalTime getTotalTime();

	/**
	 * Get the time since the call to the method {@link #start()}. This time correspond to the total time minus the pause time.
	 * 
	 * @return The game time managed by this time task.
	 */
	public LocalTime getGameTime();

	/**
	 * Get the time during which this task has been paused. Calling method {@link #pause()} a first time trigger the pause time to
	 * increase and the game time to stop. Calling a second time method pause trigger the pause time to stop and the game time to
	 * increase. </br>
	 * 
	 * Calling method {@link #stop()} will reinitialize the total time, the game time and the pause time.
	 * 
	 * @return The pause time managed by this time task.
	 */
	public LocalTime getPauseTime();
}
