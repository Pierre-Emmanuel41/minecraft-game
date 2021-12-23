package fr.pederobien.minecraft.game.commands.game;

import fr.pederobien.minecraft.game.commands.team.TeamCommandTree;
import fr.pederobien.minecraft.game.event.PlayerListPlayerAddPostEvent;
import fr.pederobien.minecraft.game.event.PlayerListPlayerRemovePostEvent;
import fr.pederobien.minecraft.game.event.TeamNewPostEvent;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class GameCommandTree implements IEventListener {
	private IGame game;
	private StartGameNode startGameNode;
	private PauseGameNode pauseGameNode;
	private StopGameNode stopGameNode;

	// Configuration nodes
	private TeamCommandTree teamTree;

	private GameRemoveTeamNode removeTeamNode;
	private GameFeatureNode featureNode;
	private GameRandomTeamNode randomTeamNode;
	private GameMovePlayerNode movePlayerNode;
	private GameTeamListNode teamListNode;

	/**
	 * Creates a game command tree without root that gathers nodes to start, pause/resume and stop a game.
	 */
	public GameCommandTree() {
		startGameNode = new StartGameNode(game);
		pauseGameNode = new PauseGameNode(game);
		stopGameNode = new StopGameNode(game);

		teamTree = new TeamCommandTree();
		teamTree.getRoot().setAvailable(() -> game != null);
		removeTeamNode = new GameRemoveTeamNode(game, teamTree);
		featureNode = new GameFeatureNode(game);
		randomTeamNode = new GameRandomTeamNode(game);
		movePlayerNode = new GameMovePlayerNode(game);
		teamListNode = GameTeamListNode.newInstance(game);

		EventManager.registerListener(this);
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

	/**
	 * @return The tree used to manipulate a team.
	 */
	public TeamCommandTree getTeamTree() {
		return teamTree;
	}

	/**
	 * @return The node that removes a team from the current configuration.
	 */
	public GameRemoveTeamNode getRemoveTeamNode() {
		return removeTeamNode;
	}

	/**
	 * @return The node that enables/disables feature of the current configuration.
	 */
	public GameFeatureNode getFeatureNode() {
		return featureNode;
	}

	/**
	 * @return The node that dispatches players randomly in teams.
	 */
	public GameRandomTeamNode getRandomTeamNode() {
		return randomTeamNode;
	}

	/**
	 * @return The node that moves a player from one team to another one.
	 */
	public GameMovePlayerNode getMovePlayerNode() {
		return movePlayerNode;
	}

	/**
	 * @return The node that displays the team list of the current configuration.
	 */
	public GameTeamListNode getTeamListNode() {
		return teamListNode;
	}

	@EventHandler
	private void onTeamNew(TeamNewPostEvent event) {
		if (!event.getTree().equals(teamTree))
			return;

		game.getTeams().add(event.getTeam());

		// Updating the team tree.
		teamTree.getExceptedNames().add(event.getTeam().getName());
		teamTree.getExceptedColors().add(event.getTeam().getColor());
	}

	@EventHandler
	private void onPlayerAdd(PlayerListPlayerAddPostEvent event) {
		teamTree.getExceptedPlayers().add(event.getPlayer());
	}

	@EventHandler
	private void onPlayerRemove(PlayerListPlayerRemovePostEvent event) {
		teamTree.getExceptedPlayers().remove(event.getPlayer());
	}
}
