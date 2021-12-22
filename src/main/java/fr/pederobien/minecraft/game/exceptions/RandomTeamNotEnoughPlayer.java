package fr.pederobien.minecraft.game.exceptions;

public class RandomTeamNotEnoughPlayer extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private int playersCount;

	public RandomTeamNotEnoughPlayer(int playersCount) {
		super(String.format("There is not enough players (%s)", playersCount));
		this.playersCount = playersCount;
	}

	/**
	 * @return The number of players currently logged into the server.
	 */
	public int getPlayersCount() {
		return playersCount;
	}
}
