package fr.pederobien.minecraft.game.exceptions;

import fr.pederobien.minecraft.game.interfaces.ITeamPlayerList;

public class TeamPlayerListException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private ITeamPlayerList list;

	public TeamPlayerListException(String message, ITeamPlayerList list) {
		super(message);
		this.list = list;
	}

	/**
	 * @return The list involved in this exception.
	 */
	public ITeamPlayerList getList() {
		return list;
	}
}
