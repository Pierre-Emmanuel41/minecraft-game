package fr.pederobien.minecraft.game.event;

import java.util.StringJoiner;

import fr.pederobien.minecraft.game.interfaces.IGameConfiguration;
import fr.pederobien.minecraft.game.interfaces.ITeam;

public class ConfigurationTeamAddPostEvent extends GameConfigurationEvent {
	private ITeam team;

	/**
	 * Creates an event thrown when a team has been added to a configuration.
	 * 
	 * @param configuration The configuration to which a team has been added.
	 * @param team          The added team.
	 */
	public ConfigurationTeamAddPostEvent(IGameConfiguration configuration, ITeam team) {
		super(configuration);
		this.team = team;
	}

	/**
	 * @return The added team.
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
