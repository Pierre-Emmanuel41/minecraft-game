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

	public ConfigurationCommandTree() {
		teamTree = new TeamCommandTree();
		teamTree.getRoot().setAvailable(() -> configuration != null);
		removeTeamNode = new ConfigurationRemoveTeamNode(this);
		featureNode = new ConfigurationFeatureNode(this);

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
	 * @return The node that remove a team from the current configuration.
	 */
	public ConfigurationRemoveTeamNode getRemoveTeamNode() {
		return removeTeamNode;
	}

	/**
	 * @return The node that enable/disable feature of the current configuration.
	 */
	public ConfigurationFeatureNode getFeatureNode() {
		return featureNode;
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
