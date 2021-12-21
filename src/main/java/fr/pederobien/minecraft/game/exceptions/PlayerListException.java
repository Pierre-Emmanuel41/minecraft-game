package fr.pederobien.minecraft.game.exceptions;

import fr.pederobien.minecraft.game.interfaces.IPlayerList;

public class PlayerListException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private IPlayerList list;

	public PlayerListException(String message, IPlayerList list) {
		super(message);
		this.list = list;
	}

	/**
	 * @return The list involved in this exception.
	 */
	public IPlayerList getList() {
		return list;
	}
}
