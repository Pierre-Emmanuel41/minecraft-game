package fr.pederobien.minecraft.game.commands.game;

import fr.pederobien.minecraft.game.interfaces.IGame;

public class GameCommandTree {
	private IGame game;
	private StartGameNode startGameNode;
	private PauseGameNode pauseGameNode;
	private StopGameNode stopGameNode;

	/**
	 * Creates a game command tree without root that gathers nodes to start, pause/resume and stop a game.
	 */
	public GameCommandTree() {
		startGameNode = new StartGameNode(this);
		pauseGameNode = new PauseGameNode(this);
		stopGameNode = new StopGameNode(this);
	}

	/**
	 * @return The game managed by this tree.
	 */
	public IGame getGame() {
		return game;
	}

	/**
	 * Set the game managed by this tree. It automatically stops the current game is it is running.
	 * 
	 * @param game the new game managed by this tree.
	 */
	public void setGame(IGame game) {
		if (this.game != null)
			this.game.stop();
		this.game = game;
	}

	/**
	 * @return The node that starts a game.
	 */
	public StartGameNode getStartGameNode() {
		return startGameNode;
	}

	/**
	 * @return The node that pause/resume a game.
	 */
	public PauseGameNode getPauseGameNode() {
		return pauseGameNode;
	}

	/**
	 * @return The node that stops a game.
	 */
	public StopGameNode getStopGameNode() {
		return stopGameNode;
	}
}
