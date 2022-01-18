package fr.pederobien.minecraft.game.impl.time;

import java.time.LocalTime;
import java.util.function.Consumer;

import fr.pederobien.minecraft.game.interfaces.time.ICountDown;

public class CountDown implements ICountDown {
	private int initial, current;
	private Consumer<Integer> countDownAction;
	private Consumer<LocalTime> onTimeAction;

	/**
	 * Creates a count down associated to an initial value.
	 * 
	 * @param initialValue    The initial value of this count down.
	 * @param countDownAction Action to run while the count down is decreasing.
	 * @param onTimeAction    Action to run when the count down is reached.
	 */
	public CountDown(int initialValue, Consumer<Integer> countDownAction, Consumer<LocalTime> onTimeAction) {
		this.initial = current = initialValue == 0 ? 0 : initialValue - 1;
		this.countDownAction = countDownAction;
		this.onTimeAction = onTimeAction;
	}

	@Override
	public int getInitialValue() {
		return initial;
	}

	@Override
	public int getCurrentValue() {
		return current;
	}

	@Override
	public void signal(LocalTime time) {
		if (current == 0) {
			onTimeAction.accept(time);
			current = initial;
		} else {
			countDownAction.accept(current);
			current--;
		}
	}
}
