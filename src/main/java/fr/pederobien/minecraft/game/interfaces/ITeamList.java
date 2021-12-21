package fr.pederobien.minecraft.game.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import fr.pederobien.minecraft.game.exceptions.TeamAlreadyRegisteredException;
import fr.pederobien.minecraft.managers.EColor;

public interface ITeamList extends Iterable<ITeam> {

	/**
	 * @return The name of this list.
	 */
	String getName();

	/**
	 * Creates a new team.
	 * 
	 * @param name  The team name.
	 * @param color The team color.
	 * @param add   True to add automatically the created team to this list.
	 * 
	 * @return The created team.
	 */
	ITeam create(String name, EColor color, boolean add);

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
}
