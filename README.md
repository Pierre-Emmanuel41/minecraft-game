# Presentation

This project proposes several tools to simplify the development of minecraft game plugins. It contains the projects [minecraft-command-tree](https://github.com/Pierre-Emmanuel41/minecraft-command-tree) in order to creates game configuration commands whose the returned messages are translated according to the player's nationality than run the command. It also contains several classes that modelling a game and the configuration of a game.

# Download

First you need to download this project on your computer. But according to the Minecraft API version there is on the server, you should download this project by specifying the branch associated to the associated version if supported. To do so, you can use the following command line :

```git
git clone -b 1.0_MC_1.13.2-SNAPSHOT https://github.com/Pierre-Emmanuel41/minecraft-game.git --recursive
```

and then double click on the deploy.bat file. This will deploy this project and all its dependencies on your computer. Which means it generates the folder associated to this project and its dependencies in your .m2 folder. Once this has been done, you can add the project as maven dependency on your maven project :

```xml
<dependency>
	<groupId>fr.pederobien.minecraft</groupId>
	<artifactId>game</artifactId>
	<version>1.0_MC_1.13.2-SNAPSHOT</version>
	<scope>provided</code>
</dependency>
```

The jar plugin should be present on the minecraft server in order to be used by several other plugins. That is why it is declared as <code>provided</code> for the dependency scope.

To see how to develop minecraft commands, please see [this tutorial](https://github.com/Pierre-Emmanuel41/minecraft-command-tree/blob/master/Tutorial.md) and for language sensitive commands, please see [this tutorial](https://github.com/Pierre-Emmanuel41/minecraft-command-tree/blob/master/Tutorial_language.md) from the minecraft-command-tree project.

To see the tools proposed by this project, please have a look to [that presentation](https://github.com/Pierre-Emmanuel41/minecraft-command-tree/blob/master/Presentation.md)