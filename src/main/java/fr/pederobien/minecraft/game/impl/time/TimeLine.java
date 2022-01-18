package fr.pederobien.minecraft.game.impl.time;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraft.game.event.TimeTaskStopPostEvent;
import fr.pederobien.minecraft.game.event.TimeTaskTimeChangePostEvent;
import fr.pederobien.minecraft.game.interfaces.time.IObsTimeLine;
import fr.pederobien.minecraft.game.interfaces.time.ITimeLine;
import fr.pederobien.minecraft.game.interfaces.time.ITimeTask;
import fr.pederobien.utils.Observable;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class TimeLine implements ITimeLine, IEventListener {
	private ITimeTask timeTask;
	private Map<LocalTime, Observable<IObsTimeLine>> observers;

	public TimeLine(Plugin plugin) {
		this.timeTask = new TimeTask(plugin);
		observers = new HashMap<LocalTime, Observable<IObsTimeLine>>();

		EventManager.registerListener(this);
	}

	@Override
	public ITimeTask getTimeTask() {
		return timeTask;
	}

	@Override
	public void register(LocalTime time, IObsTimeLine obs) {
		registerObserver(time, obs);

		if (obs.getCountDown() == null)
			return;

		// Registering the observer for each count down value.
		int current = obs.getCountDown().getInitialValue();
		while (current != 0)
			registerObserver(time.minusSeconds(current--), obs);
	}

	@Override
	public void unregister(LocalTime time, IObsTimeLine obs) {
		Observable<IObsTimeLine> observable = observers.get(time);
		if (observable != null)
			observable.removeObserver(obs);

		if (obs.getCountDown() == null || obs.getCountDown().getInitialValue() == 0)
			return;

		int current = obs.getCountDown().getInitialValue();
		while (current != 0) {
			observable = observers.get(time.minusSeconds(current--));
			if (observable != null)
				observable.removeObserver(obs);
		}
	}

	@EventHandler
	private void onTimeTaskStop(TimeTaskStopPostEvent event) {
		if (!event.getTask().equals(timeTask))
			return;

		observers.clear();
	}

	@EventHandler
	private void onTimeTaskTimeChange(TimeTaskTimeChangePostEvent event) {
		if (!event.getTask().equals(timeTask))
			return;

		LocalTime currentTime = event.getTask().getGameTime();

		// Getting observers associated to the game time of the task.
		Observable<IObsTimeLine> obsOnTime = observers.get(currentTime);

		// If there are observers interested by the current game time
		if (obsOnTime != null) {
			obsOnTime.getObservers().stream().forEach(obs -> {
				// Notify the observer using onTime
				obs.getCountDown().signal(currentTime);

				LocalTime nextTime = obs.getNextTime();
				// No need to register the observer if the next time is null
				// No need to register the observer while the count down is not over
				if (nextTime == null || obs.getCountDown().getCurrentValue() != obs.getCountDown().getInitialValue())
					return;

				register(nextTime, obs);
			});
		}
	}

	/**
	 * Register the given observer for the specified time.
	 * 
	 * @param time The time at which this observer should be notified.
	 * @param obs  The observer to register.
	 */
	private void registerObserver(LocalTime time, IObsTimeLine obs) {
		Observable<IObsTimeLine> obsOnTime = observers.get(time);

		if (obsOnTime == null) {
			obsOnTime = new Observable<IObsTimeLine>();
			observers.put(time, obsOnTime);
		}
		obsOnTime.addObserver(obs);
	}
}
