package fr.pederobien.minecraft.game.exceptions;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.game.interfaces.IPlayerList;

public class PlayerAlreadyRegisteredException extends PlayerListException {
	private static final long serialVersionUID = 1L;
	private Player player;

	public PlayerAlreadyRegisteredException(IPlayerList list, Player player) {
		super(String.format("A player %s is already registered", player.getName()), list);
		this.player = player;
	}

	/**
	 * @return The registered player.
	 */
	public Player getPlayer() {
		return player;
	}
}
