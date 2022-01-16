package fr.pederobien.minecraft.game.impl;

import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.dictionary.interfaces.IPlayerGroup;

public enum EGameCode implements IMinecraftCode {
	// Common codes -------------------------------------------------------------

	// Code for the name completion
	NAME__COMPLETION,

	// Code when a bad format occurs
	BAD_FORMAT,

	// Code for the strictly positive integer bad format
	STRICTLY_POSITIVE_INTEGER__BAD_FORMAT,

	// Code for player's name speaking to a team
	GAME__TEAM__PLAYER_NAME,

	// Code for the "team" command explanation ----------------------------------
	TEAM__ROOT__EXPLANATION,

	// Code for the "team new" command explanation ------------------------------
	TEAM__NEW__EXPLANATION,

	// Code when the team name is missing
	TEAM__NEW__NAME_IS_MISSING,

	// Code when the team name is already used
	TEAM__NEW__NAME_ALREADY_USED,

	// Code when the team color is missing
	TEAM__NEW__COLOR_IS_MISSING,

	// Code when the team color is missing
	TEAM__NEW__COLOR_NOT_FOUND,

	// Code when the team name is already used
	TEAM__NEW__COLOR_ALREADY_USED,

	// Code when a new team is created
	TEAM__NEW__TEAM_CREATED,

	// Code for the "team modify" command explanation ---------------------------
	TEAM__MODIFY__EXPLANATION,

	// Code for the "team modify name" command explanation ----------------------
	TEAM__MODIFY_NAME__EXPLANATION,

	// Code when the new team name is missing
	TEAM__MODIFY_NAME__NAME_IS_MISSING,

	// Code when the new team name is missing
	TEAM__MODIFY_NAME__NAME_IS_ALREADY_USED,

	// Code when the new team name is missing
	TEAM__MODIFY_NAME__TEAM_NAME_UPDATED,

	// Code for the "team modify color" command explanation ---------------------
	TEAM__MODIFY_COLOR__EXPLANATION,

	// Code when the new team color is missing
	TEAM__MODIFY_COLOR__COLOR_IS_MISSING,

	// Code when the new team color does not exist
	TEAM__MODIFY_COLOR__COLOR_NOT_FOUND,

	// Code when the new team name is missing
	TEAM__MODIFY_COLOR__COLOR_IS_ALREADY_USED,

	// Code when the new team name is missing
	TEAM__MODIFY_COLOR__TEAM_COLOR_UPDATED,

	// Code for the "team remove" command explanation ---------------------------
	TEAM__REMOVE_PLAYER__EXPLANATION,

	// Code when removing all players from a team
	TEAM__REMOVE_PLAYER__ALL_PLAYERS_REMOVED,

	// Code when the player name refers to no player
	TEAM__REMOVE_PLAYER__PLAYER_NOT_FOUND,

	// Code when the player name refers to a not registered player
	TEAM__REMOVE_PLAYER__PLAYER_NOT_REGISTERED,

	// Code when no player has been removed from a team
	TEAM__REMOVE_PLAYER__NO_PLAYER_REMOVED,

	// Code when one player has been removed from a team
	TEAM__REMOVE_PLAYER__ONE_PLAYER_REMOVED,

	// Code when one player has been removed from a team
	TEAM__REMOVE_PLAYER__SEVERAL_PLAYERS_REMOVED,

	// Code for the "team add" command explanation ------------------------------
	TEAM__ADD_PLAYER__EXPLANATION,

	// Code when the player name refers to no player
	TEAM__ADD_PLAYER__PLAYER_NOT_FOUND,

	// Code when the player name refers to a not registered player
	TEAM__ADD_PLAYER__PLAYER_ALREADY_REGISTERED,

	// Code when no player has been added to a team
	TEAM__ADD_PLAYER__NO_PLAYER_ADDED,

	// Code when one player has been added to a team
	TEAM__ADD_PLAYER__ONE_PLAYER_ADDED,

	// Code when one player has been added to a team
	TEAM__ADD_PLAYER__SEVERAL_PLAYERS_ADDED,

	// Code for the "startgame" command -----------------------------------------
	START_GAME__EXPLANATION,

	// Code when there is no game to start
	START_GAME__NO_GAME_TO_START,

	// Code when a game starts
	START_GAME__STARTING_GAME(PlayerGroup.ALL),

	// Code for the "pausegame" command -----------------------------------------
	PAUSE_GAME__EXPLANATION,

	// Code when there is no game to start
	PAUSE_GAME__NO_GAME_TO_PAUSE,

	// Code when there is no game to start
	PAUSE_GAME__GAME_NOT_STARTED,

	// Code when a game starts
	PAUSE_GAME__PAUSING_GAME(PlayerGroup.ALL),

	// Code when a game starts
	PAUSE_GAME__RESUMING_GAME(PlayerGroup.ALL),

	// Code for the "stopgame" command ------------------------------------------
	STOP_GAME__EXPLANATION,

	// Code when there is no game to start
	STOP_GAME__NO_GAME_TO_STOP,

	// Code when a game starts
	STOP_GAME__STOPPING_GAME(PlayerGroup.ALL),

	// Code for the "feature" command -------------------------------------------
	FEATURE__EXPLANATION,

	// Code for the "feature enable" command ------------------------------------
	FEATURE__ENABLE__EXPLANATION,

	// Code when the feature is enabled
	FEATURE__ENABLE__FEATURE_ENABLED,

	// Code for the "feature disable" command -----------------------------------
	FEATURE__DISABLE__EXPLANATION,

	// Code when the feature is disable
	FEATURE__DISABLE__FEATURE_DISABLED,

	// Code for the "feature args" command --------------------------------------
	FEATURE__ARGS__EXPLANATION,

	// Code for the "features" command ------------------------------------------
	FEATURES__EXPLANATION,

	// Code for the "features enable" command -----------------------------------
	FEATURES__ENABLE__EXPLANATION,

	// Code when the feature does not exist
	FEATURES__ENABLE__FEATURE_NOT_FOUND,

	// Code when no feature has been enabled
	FEATURES__ENABLE__NO_FEATURE_ENABLED,

	// Code when one feature has been enabled
	FEATURES__ENABLE__ONE_FEATURE_ENABLED,

	// Code when several feature have been enabled
	FEATURES__ENABLE__SEVERAL_FEATURES_ENABLED,

	// Code for the "features disable" command ----------------------------------
	FEATURES__DISABLE__EXPLANATION,

	// Code when the feature does not exist
	FEATURES__DISABLE__FEATURE_NOT_FOUND,

	// Code when no feature has been disabled
	FEATURES__DISABLE__NO_FEATURE_DISABLED,

	// Code when one feature has been disabled
	FEATURES__DISABLE__ONE_FEATURE_DISABLED,

	// Code when several feature have been disabled
	FEATURES__DISABLE__SEVERAL_FEATURES_DISABLED,

	// Code for the "features args" command -------------------------------------
	FEATURES__ARGS__NAME_IS_MISSING,

	// Code when the feature does not exist
	FEATURES__ARGS__FEATURE_NOT_FOUND,

	// Code when the feature is not enabled
	FEATURES__ARGS__FEATURE_NOT_ENABLED,

	// Code for the "teams" command ---------------------------------------------
	TEAMS__EXPLANATION,

	// Code for the "teams add" command -----------------------------------------
	TEAMS__ADD__EXPLANATION,

	// Code for the "teams add team" command ------------------------------------
	TEAMS__ADD_TEAM__EXPLANATION,

	// Code when a team has been added
	TEAMS__ADD_TEAM__TEAM_ADDED,

	// Code for the "teams remove" command --------------------------------------
	TEAMS__REMOVE__EXPLANATION,

	// Code for the "teams remove team" command ---------------------------------
	TEAMS__REMOVE_TEAM__EXPLANATION,

	// Code when removing all teams from a configuration
	TEAMS__REMOVE_TEAM__ALL_PLAYERS_REMOVED,

	// Code when the player name refers to no player
	TEAMS__REMOVE_TEAM__TEAM_NOT_FOUND,

	// Code when no team has been removed from a game.
	TEAMS__REMOVE_TEAM__NO_TEAM_REMOVED,

	// Code when one team has been removed from a game.
	TEAMS__REMOVE_TEAM__ONE_TEAM_REMOVED,

	// Code when several teams have been removed from a game.
	TEAMS__REMOVE_TEAM__SEVERAL_TEAMS_REMOVED,

	// Code for the "teams modify" command --------------------------------------
	TEAMS__MODIFY__EXPLANATION,

	// Code when the team to modify does not exist
	TEAMS__MODIFY__TEAM_NOT_FOUND,

	// Code when the "teams random" command -------------------------------------
	TEAMS__RANDOM__EXPLANATION,

	// Code to get the max number of players per team
	TEAMS__RANDOM__MAX_PLAYERS_PER_TEAM,

	// Code when the max player per team value is negative
	TEAMS__RANDOM__MAX_PLAYERS_PER_TEAM__NEGATIVE_VALUE,

	// Code when there are not enough teams to create dispatch players
	TEAMS__RANDOM__NOT_ENOUGH_TEAMS,

	// Code when there are not enough teams to create dispatch players
	TEAMS__RANDOM__PLAYERS_DISPATCHED_IN_TEAMS,

	// Code for the "teams move" command ----------------------------------------
	TEAMS__MOVE__EXPLANATION,

	// Code when the player name is missing
	TEAMS__MOVE__PLAYER_NAME_IS_MISSING,

	// Code when the team name is missing
	TEAMS__MOVE__TEAM_NAME_IS_MISSING,

	// Code when the player name refers to no player
	TEAMS__MOVE__PLAYER_NOT_FOUND,

	// Code when the team name refers to no team
	TEAMS__MOVE__TEAM_NOT_FOUND,

	// Code when a player is moved from a team to another one
	TEAMS__MOVE__PLAYER_MOVED_FROM_TO,

	// Code for the "teams list" command ----------------------------------------
	TEAMS__LIST__EXPLANATION,

	// Code when there is no list in configuration
	TEAMS__LIST__NO_TEAM_REGISTERED,

	// Code when there is no list in configuration
	TEAMS__LIST__ONE_TEAM_REGISTERED,

	// Code when there is no list in configuration
	TEAMS__LIST__SEVERAL_TEAMS_REGISTERED,

	;

	private IPlayerGroup group;

	private EGameCode() {
		this(PlayerGroup.OPERATORS);
	}

	private EGameCode(IPlayerGroup group) {
		this.group = group;
	}

	@Override
	public String value() {
		return name();
	}

	@Override
	public IPlayerGroup getGroup() {
		return this.group;
	}

	@Override
	public void setGroup(IPlayerGroup group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return String.format("value=%s,group=%s", value(), getGroup());
	}
}
