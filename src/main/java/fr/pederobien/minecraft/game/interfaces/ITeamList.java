package fr.pederobien.minecraft.game.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.exceptions.TeamAlreadyRegisteredException;

public interface ITeamList extends Iterable<ITeam> {

	/**
	 * @return The name of this list.
	 */
	String getName();

	/**
	 * Appends the given team to this list.
	 * 
	 * @param team The team to add.
	 * 
	 * @throws TeamAlreadyRegisteredException If a team is already registered for the team name.
	 */
	void add(ITeam team);

	/**
	 * Removes the team associated to the given name.
	 * 
	 * @param name The team name to remove.
	 * 
	 * @return The removed team if registered, null otherwise.
	 */
	ITeam remove(String name);

	/**
	 * Removes the given team from this list.
	 * 
	 * @param team The team to remove.
	 * 
	 * @return True if the team was registered, false otherwise.
	 */
	boolean remove(ITeam team);

	/**
	 * Removes all registered teams. It also clear each registered players.
	 */
	void clear();

	/**
	 * Get the team associated to the given name.
	 * 
	 * @param name The team name.
	 * 
	 * @return An optional that contains the team if registered, an empty optional otherwise.
	 */
	Optional<ITeam> getTeam(String name);

	/**
	 * @return a sequential {@code Stream} over the elements in this collection.
	 */
	Stream<ITeam> stream();

	/**
	 * @return A copy of the underlying list.
	 */
	List<ITeam> toList();

	/**
	 * Appends or moves the specified player in the given team. If the player was registered in a team, then the players is removed
	 * from it.
	 * 
	 * @param player The player to move.
	 * @param team   The new player team.
	 * 
	 * @return The old team player if it was registered in a team, null otherwise.
	 */
	ITeam movePlayer(Player player, ITeam team);
}
