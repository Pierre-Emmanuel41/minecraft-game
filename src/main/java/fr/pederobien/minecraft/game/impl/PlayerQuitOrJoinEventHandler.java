package fr.pederobien.minecraft.game.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.pederobien.minecraft.managers.EventListener;

public class PlayerQuitOrJoinEventHandler extends EventListener {
	private Map<Object, Consumer<PlayerQuitEvent>> quitEventListener;
	private Map<Object, Consumer<PlayerJoinEvent>> joinEventListener;

	private PlayerQuitOrJoinEventHandler() {
		quitEventListener = new HashMap<Object, Consumer<PlayerQuitEvent>>();
		joinEventListener = new HashMap<Object, Consumer<PlayerJoinEvent>>();
	}

	/**
	 * @return The unique instance of this event handler. This event handler is not registered in order to catch
	 *         {@link PlayerQuitEvent} and {@link PlayerJoinEvent}.
	 */
	public static PlayerQuitOrJoinEventHandler instance() {
		return SingletonHolder.HANDLER;
	}

	private static class SingletonHolder {
		private static final PlayerQuitOrJoinEventHandler HANDLER = new PlayerQuitOrJoinEventHandler();
	}

	/**
	 * Register an action to perform when a player quit the server.
	 * 
	 * @param handler  The object used as reference for the action to perform.
	 * @param consumer The action to execute when a player quit the server.
	 */
	public void registerQuitEventHandler(Object handler, Consumer<PlayerQuitEvent> consumer) {
		quitEventListener.put(handler, consumer);
	}

	/**
	 * Register an action to perform when a player join the server.
	 * 
	 * @param handler  The object used as reference for the action to perform.
	 * @param consumer The action to execute when a player join the server.
	 */
	public void registerJoinEventHandler(Object handler, Consumer<PlayerJoinEvent> consumer) {
		joinEventListener.put(handler, consumer);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onPlayerQuitEvent(PlayerQuitEvent event) {
		quitEventListener.entrySet().forEach(entry -> entry.getValue().accept(event));
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	private void onPlayerJoinEvent(PlayerJoinEvent event) {
		joinEventListener.entrySet().forEach(entry -> entry.getValue().accept(event));
	}
}
