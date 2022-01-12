# Tutorial

This project proposes a layer for the development of new game for minecraft.

# 1) Modelling a game

A game is modelled by a list of teams that contains players, a set of parameters used while the game is in progress, and finally a <code>Game</code> object in order to be started, paused/resumed and stopped.

### 1.1) Team

The team interface proposed by bukkit can be not so easy to manipulate, that is why, a team is modelled as follow:

```java
public interface ITeam {

	/**
	 * @return The name of this team.
	 */
	String getName();

	/**
	 * Set the name of this team.
	 * 
	 * @param name The new team name.
	 */
	void setName(String name);

	/**
	 * @return The name of this team using {@link EColor#getInColor(String)} with parameters String equals {@link #getName()}.
	 */
	String getColoredName();

	/**
	 * @return The color of this team. The default value is {@link EColor#RESET}.
	 */
	EColor getColor();

	/**
	 * Set the color of this team. If this team has been created on the server and is not a clone, then the "server team" is also
	 * updated.
	 * 
	 * @param color The new color of this team.
	 */
	void setColor(EColor color);

	/**
	 * Send the given message to each player registered in this room.
	 * 
	 * @param sender The player who send the message.
	 */
	void sendMessage(Player sender, String message);

	/**
	 * For each player in this team, send the message associated to the given code.
	 * 
	 * @param sender The player who send the message to the team.
	 * @param code   Used as key to get the right message in the right dictionary.
	 * @param args   Some arguments (optional) used for dynamic messages.
	 */
	void sendMessage(Player sender, IMinecraftCode code, Object... args);

	/**
	 * @return The list of teams for this team.
	 */
	IPlayerList getPlayers();

	/**
	 * Clone this team. The returned team will have the same name, the same color and the same players than the original team. One
	 * thing differ slightly is the behaviour. Because the returned team is a clone, the associated server team is not updated if
	 * there are modifications.
	 * 
	 * @return The clone of this team.
	 */
	ITeam clone();

	/**
	 * @return If this team has been created on this server.
	 */
	boolean isCreatedOnServer();

	/**
	 * Get an optional of {@link Team}. The team correspond to the team on the server associated to this {@link ITeam}. If this team
	 * is not created on the server, or has been created then removed, the optional is empty.
	 * 
	 * @return An optional that contains the server team if created on server, an empty optional otherwise.
	 * 
	 * @see #isCreatedOnServer()
	 */
	Optional<Team> getServerTeam();
}
```
whose the default implementation can is [here](https://github.com/Pierre-Emmanuel41/minecraft-game/blob/master/src/main/java/fr/pederobien/minecraft/game/impl/Team.java).

### 1.2) Game

A game can be configured through the <code>IGame</code> interface that looks like :

```java
public interface IGame extends IPausable {

	/**
	 * @return The name of this game.
	 */
	String getName();

	/**
	 * @return The plugin associated to this game.
	 */
	Plugin getPlugin();

	/**
	 * @return The tab executor in order to run specific treatment according to argument line before starting the game.
	 */
	TabExecutor getStartTabExecutor();

	/**
	 * @return The tab executor in order to run specific treatment according to argument line before stopping the game.
	 */
	TabExecutor getStopTabExecutor();

	/**
	 * @return The state in which this game is.
	 */
	EGameState getState();
}
```

This interface can be extends by other game interfaces in order to add parameters. The default implementation of a game can be found [here](https://github.com/Pierre-Emmanuel41/minecraft-game/blob/master/src/main/java/fr/pederobien/minecraft/game/impl/Game.java).  

There are three implementations of an IGame : <code>TeamsGame</code>, <code>FeaturesGame</code> and <code>TeamsFeaturesGame</code> in order to add teams management, features management or both. The interface IFeature can be used for adding new behaviors during the game. The default feature implementation is :

```java
public class Feature implements IFeature {
	private String name;
	private TabExecutor startTabExecutor;
	private boolean isEnable;

	/**
	 * Creates an simple feature.
	 * 
	 * @param name The feature name.
	 */
	public Feature(String name, TabExecutor startTabExecutor) {
		this.name = name;
		this.startTabExecutor = startTabExecutor;
	}

	@Override
	public void start() {
		if (isEnable)
			EventManager.callEvent(new FeatureStartPostEvent(this));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void stop() {
		EventManager.callEvent(new FeatureStopPostEvent(this));
	}

	@Override
	public void pause() {
		EventManager.callEvent(new FeaturePausePostEvent(this));
	}

	@Override
	public void resume() {
		EventManager.callEvent(new FeatureResumePostEvent(this));
	}

	@Override
	public boolean isEnable() {
		return isEnable;
	}

	@Override
	public void setEnabled(boolean isEnable) {
		if (this.isEnable == isEnable)
			return;

		this.isEnable = isEnable;
		EventManager.callEvent(new FeatureEnableChangePostEvent(this));
	}

	@Override
	public TabExecutor getStartTabExecutor() {
		return startTabExecutor;
	}
}
```

### 1.4) Time

The package <code>game.interfaces.time</code> proposes objects associated to the time elapsed during the game in order to perform actions punctually or periodically during the game. The two objects are <code>ITimeTask</code> and <code>ITimeLine</code>.  

The time task, whose the default implementation is [TimeTask](https://github.com/Pierre-Emmanuel41/minecraft-game/blob/master/src/main/java/fr/pederobien/minecraft/game/impl/time/TimeTask.java) , gather the time elapsed since the beginning of the game, the time elapsed while the game was running and the time while the game was paused.  
The time line, whose the default implementation is [TimeLine](https://github.com/Pierre-Emmanuel41/minecraft-game/blob/master/src/main/java/fr/pederobien/minecraft/game/impl/time/TimeLine.java), is based on a time task and is used to perform action punctually or periodically while the game is in progress.

# 2) Commands

From the <code>GamePlugin</code> class, the developer can have access to the <code>GameCommandTree</code>. This tree gather nodes defined as completer and executor for the commands <code>startgame</code>, <code>pausegame</code> and <code>stopgame</code>.

The <code>TeamCommandTree</code> gather commands in order to create new teams, to modify team characteristics or to add/remove players from a team. Those command are not registered for minecraft commands. The root is "team" which means that when registered as commands argument for a game the user has access to the following tree:

<code>team</code> - To create a new team.</br>  
&ensp;<code>new</code> - To create/modify a team.</br>
&ensp;<code>modify</code> - To modify the characteristics of a team.</br>
&ensp;&ensp;<code>name</code> - To modify the name of a team.</br>
&ensp;&ensp;<code>color</code> - To modify the color of a team.</br>
&ensp;<code>remove</code> - To remove players from a team.</br>
&ensp;<code>add</code> - To add players to a team.</br>

Finally, the <code>NodeFactory</code> gather nodes in order to add/remove/modify teams of a game, to move player(s) from a team to another one and to setup features of a game. Those nodes are <code>GameTeamsNode</code> and <code>GameFeaturesNode</code>.

To set up the teams of a game :  
<code>teams</code> - To modify a list of teams of a game. In that case, the game should implements the <code>ITeamConfigurable</code> interface.</br>
&ensp;<code>add</code> - To add a team to a game or to add a player to a team.</br>
&ensp;&ensp;<code>team</code> - To add a team to a game.</br>
&ensp;&ensp;<code>player</code> - To add players to a team.</br>
&ensp;<code>remove</code> - To remove a team from a game or to add a player from a team.</br>
&ensp;&ensp;<code>team</code> - To remove a team from a game.</br>
&ensp;&ensp;<code>player</code> - To remove players from to a team.</br>
&ensp;<code>modify</code> - To modify the characteristics of a team.</br>
&ensp;&ensp;<code>name</code> - To modify the name of a team.</br>
&ensp;&ensp;<code>color</code> - To modify the color of a team.</br>
&ensp;<code>list</code> - To display the list of team.</br>
&ensp;<code>move</code> - To move a player from a team to another one.</br>
&ensp;<code>random</code> - To create random teams.</br>
<br/>
To set up features of a game :  
<code>features</code> - To setup a feature.</br>
&ensp;<code>enable</code> - To enable a feature.</br>
&ensp;<code>disable</code> - To disable a feature.</br>
&ensp;<code>args</code> - To set the argument before starting a feature.</br>