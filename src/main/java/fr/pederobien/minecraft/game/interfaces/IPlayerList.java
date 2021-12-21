package fr.pederobien.minecraft.game.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.exceptions.PlayerAlreadyRegisteredException;

public interface IPlayerList extends Iterable<Player> {

	/**
	 * @return The name of this player list.
	 */
	String getName();

	/**
	 * Appends the given player to this list.
	 * 
	 * @param player The player to add.
	 * 
	 * @throws PlayerAlreadyRegisteredException If a player is already registered for the player name.
	 */
	void add(Player player);

	/**
	 * Removes the player associated to the given name.
	 * 
	 * @param name The player name to remove.
	 * 
	 * @return The removed player if registered, null otherwise.
	 */
	Player remove(String name);

	/**
	 * Removes the given player from this list.
	 * 
	 * @param player The player to remove.
	 * 
	 * @return True if the player was registered, false otherwise.
	 */
	boolean remove(Player player);

	/**
	 * Removes all registered players. It also clear each registered players.
	 */
	void clear();

	/**
	 * Get the player associated to the given name.
	 * 
	 * @param name The player name.
	 * 
	 * @return An optional that contains the player if registered, an empty optional otherwise.
	 */
	Optional<Player> getPlayer(String name);

	/**
	 * @return a sequential {@code Stream} over the elements in this collection.
	 */
	Stream<Player> stream();

	/**
	 * @return A copy of the underlying list.
	 */
	List<Player> toList();
}
