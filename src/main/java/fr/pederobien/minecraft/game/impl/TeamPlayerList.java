package fr.pederobien.minecraft.game.impl;

import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamPlayerList;

public class TeamPlayerList extends PlayerList implements ITeamPlayerList {
	private ITeam team;

	/**
	 * Creates a player associated to the given team.
	 * 
	 * @param team The team to which this players list is associated.
	 */
	public TeamPlayerList(ITeam team) {
		super(team.getName());
		this.team = team;
	}

	@Override
	public ITeam getTeam() {
		return team;
	}
}
