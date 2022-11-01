package fr.pederobien.minecraft.game.interfaces;

public interface ITeamPlayerList extends IPlayerList {

	/**
	 * @return The team to which this player list is associated.
	 */
	ITeam getTeam();

}
