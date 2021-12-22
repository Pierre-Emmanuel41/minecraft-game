package fr.pederobien.minecraft.game.exceptions;

public class RandomTeamNotEnoughTeam extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private int teamsCount;

	public RandomTeamNotEnoughTeam(int teamsCount) {
		super(String.format("There is not enough teams (%s)", teamsCount));
		this.teamsCount = teamsCount;
	}

	/**
	 * @return The number of team.
	 */
	public int getTeamsCount() {
		return teamsCount;
	}
}
