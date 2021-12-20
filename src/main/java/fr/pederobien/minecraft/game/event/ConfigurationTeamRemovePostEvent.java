package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.game.interfaces.IGameConfiguration;
import fr.pederobien.minecraft.game.interfaces.ITeam;

public class ConfigurationTeamRemovePostEvent extends GameConfigurationEvent {
	private ITeam team;

	/**
	 * Creates an event thrown when a team has been removed from a configuration.
	 * 
	 * @param configuration The configuration from which a team has been removed.
	 * @param team          The removed team.
	 */
	public ConfigurationTeamRemovePostEvent(IGameConfiguration configuration, ITeam team) {
		super(configuration);
		this.team = team;
	}

	/**
	 * @return The removed team.
	 */
	public ITeam getTeam() {
		return team;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		joiner.add("configuration=" + getConfiguration().getName());
		joiner.add("team=" + team.getName());
		return String.format("%s_%s", getName(), joiner.toString());
	}
}
