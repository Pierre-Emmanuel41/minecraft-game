package fr.pederobien.minecraft.game.impl;

import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;
import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class TeamsGame extends Game implements ITeamConfigurable {
	private ITeamList teams;

	/**
	 * Creates a game with teams.
	 * 
	 * @param name   The game name.
	 * @param plugin The plugin associated to this game.
	 */
	public TeamsGame(String name, Plugin plugin) {
		super(name, plugin);

		teams = new TeamList(name);
	}

	@Override
	public ITeamList getTeams() {
		return teams;
	}
}
