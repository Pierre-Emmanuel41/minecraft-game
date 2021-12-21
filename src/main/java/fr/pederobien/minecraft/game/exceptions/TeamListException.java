package fr.pederobien.minecraft.game.exceptions;

import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class TeamListException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private ITeamList list;

	public TeamListException(String message, ITeamList list) {
		super(message);
		this.list = list;
	}

	/**
	 * @return The list involved in this exception.
	 */
	public ITeamList getList() {
		return list;
	}
}
