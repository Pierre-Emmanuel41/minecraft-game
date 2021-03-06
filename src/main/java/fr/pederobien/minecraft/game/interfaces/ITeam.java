package fr.pederobien.minecraft.game.interfaces;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.managers.EColor;

public interface ITeam {

	/**
	 * @return The name of this team.
	 */
	String getName();

	/**
	 * Set the name of this team.
	 * 
	 * @param name The new team name.
	 */
	void setName(String name);

	/**
	 * @return The name of this team using {@link EColor#getInColor(String)} with parameters String equals {@link #getName()}.
	 */
	String getColoredName();

	/**
	 * Get the name of this team, in the team color and specify which color to use after?
	 * 
	 * @param next The color after to used after.
	 * 
	 * @return The name of this team using {@link EColor#getInColor(String, EColor)} with parameters String equals {@link #getName()}.
	 */
	String getColoredName(EColor next);

	/**
	 * @return The color of this team. The default value is {@link EColor#RESET}.
	 */
	EColor getColor();

	/**
	 * Set the color of this team. If this team has been created on the server and is not a clone, then the "server team" is also
	 * updated.
	 * 
	 * @param color The new color of this team.
	 */
	void setColor(EColor color);

	/**
	 * Send the given message to each player registered in this room.
	 * 
	 * @param sender The player who send the message.
	 */
	void sendMessage(Player sender, String message);

	/**
	 * For each player in this team, send the message associated to the given code.
	 * 
	 * @param sender The player who send the message to the team.
	 * @param code   Used as key to get the right message in the right dictionary.
	 * @param args   Some arguments (optional) used for dynamic messages.
	 */
	void sendMessage(Player sender, IMinecraftCode code, Object... args);

	/**
	 * @return The list of teams for this team.
	 */
	IPlayerList getPlayers();

	/**
	 * Clone this team. The returned team will have the same name, the same color and the same players than the original team. One
	 * thing differ slightly is the behaviour. Because the returned team is a clone, the associated server team is not updated if
	 * there are modifications.
	 * 
	 * @return The clone of this team.
	 */
	ITeam clone();

	/**
	 * Creates the equivalent of this team on the server.
	 */
	void createOnserver();

	/**
	 * Removes this team from the server teams list.
	 */
	void removeFromServer();

	/**
	 * @return If this team has been created on this server.
	 */
	boolean isCreatedOnServer();

	/**
	 * Get an optional of {@link Team}. The team correspond to the team on the server associated to this {@link ITeam}. If this team
	 * is not created on the server, or has been created then removed, the optional is empty.
	 * 
	 * @return An optional that contains the server team if created on server, an empty optional otherwise.
	 * 
	 * @see #isCreatedOnServer()
	 */
	Optional<Team> getServerTeam();
}
