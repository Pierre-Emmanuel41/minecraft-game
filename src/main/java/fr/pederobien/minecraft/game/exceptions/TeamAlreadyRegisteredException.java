package fr.pederobien.minecraft.game.exceptions;

import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class TeamAlreadyRegisteredException extends TeamListException {
	private static final long serialVersionUID = 1L;
	private ITeam team;

	public TeamAlreadyRegisteredException(ITeamList list, ITeam team) {
		super(String.format("A team %s is already registered", team.getName()), list);
		this.team = team;
	}

	/**
	 * @return The registered team.
	 */
	public ITeam getTeam() {
		return team;
	}
}
