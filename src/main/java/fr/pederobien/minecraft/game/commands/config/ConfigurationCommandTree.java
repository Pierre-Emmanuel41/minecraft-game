package fr.pederobien.minecraft.game.commands.config;

import fr.pederobien.minecraft.game.commands.team.TeamCommandTree;
import fr.pederobien.minecraft.game.event.PlayerListPlayerAddPostEvent;
import fr.pederobien.minecraft.game.event.PlayerListPlayerRemovePostEvent;
import fr.pederobien.minecraft.game.event.TeamNewPostEvent;
import fr.pederobien.minecraft.game.interfaces.IGameConfiguration;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class ConfigurationCommandTree implements IEventListener {
	private TeamCommandTree teamTree;

	private IGameConfiguration configuration;
	private ConfigurationRemoveTeamNode removeTeamNode;
	private ConfigurationFeatureNode featureNode;
	private ConfigurationRandomTeamNode randomTeamNode;
	private ConfigurationMovePlayerNode movePlayerNode;
	private ConfigurationTeamListNode teamListNode;

	public ConfigurationCommandTree() {
		teamTree = new TeamCommandTree();
		teamTree.getRoot().setAvailable(() -> configuration != null);
		removeTeamNode = new ConfigurationRemoveTeamNode(this);
		featureNode = new ConfigurationFeatureNode(this);
		randomTeamNode = new ConfigurationRandomTeamNode(this);
		movePlayerNode = new ConfigurationMovePlayerNode(this);
		teamListNode = ConfigurationTeamListNode.newInstance(this);

		EventManager.registerListener(this);
	}

	/**
	 * @return The tree used to manipulate a team.
	 */
	public TeamCommandTree getTeamTree() {
		return teamTree;
	}

	/**
	 * @return The configuration managed by this tree.
	 */
	public IGameConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * Sets the configuration managed by this tree.
	 * 
	 * @param configuration The configuration managed by this tree.
	 */
	public void setConfiguration(IGameConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * @return The node that removes a team from the current configuration.
	 */
	public ConfigurationRemoveTeamNode getRemoveTeamNode() {
		return removeTeamNode;
	}

	/**
	 * @return The node that enables/disables feature of the current configuration.
	 */
	public ConfigurationFeatureNode getFeatureNode() {
		return featureNode;
	}

	/**
	 * @return The node that dispatches players randomly in teams.
	 */
	public ConfigurationRandomTeamNode getRandomTeamNode() {
		return randomTeamNode;
	}

	/**
	 * @return The node that moves a player from one team to another one.
	 */
	public ConfigurationMovePlayerNode getMovePlayerNode() {
		return movePlayerNode;
	}

	/**
	 * @return The node that displays the team list of the current configuration.
	 */
	public ConfigurationTeamListNode getTeamListNode() {
		return teamListNode;
	}

	@EventHandler
	private void onTeamNew(TeamNewPostEvent event) {
		if (!event.getTree().equals(teamTree))
			return;

		configuration.getTeams().add(event.getTeam());

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
