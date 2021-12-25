package fr.pederobien.minecraft.game;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.dictionary.exceptions.MessageRegisteredException;
import fr.pederobien.dictionary.impl.JarXmlDictionaryParser;
import fr.pederobien.minecraft.dictionary.impl.MinecraftDictionaryContext;
import fr.pederobien.minecraft.game.commands.game.GameCommandTree;
import fr.pederobien.minecraft.game.impl.PlayerQuitOrJoinEventHandler;
import fr.pederobien.utils.event.EventLogger;

public class GamePlugin extends JavaPlugin {
	private static final Path DICTIONARY_FOLDER = Paths.get("resources/dictionaries");

	private static Plugin instance;
	private static GameCommandTree gameTree;

	/**
	 * @return The instance of this plugin.
	 */
	public static Plugin instance() {
		return instance;
	}

	/**
	 * Get the tree used to start, pause/resume and stop a game.
	 * 
	 * @return The game tree associated to this plugin.
	 */
	public static GameCommandTree getGameTree() {
		return gameTree;
	}

	@Override
	public void onEnable() {
		instance = this;
		gameTree = new GameCommandTree();

		EventLogger.instance().newLine(false).timeStamp(false).register();
		PlayerQuitOrJoinEventHandler.instance().register(this);

		registerDictionaries();
		registerTabExecutors();
	}

	private void registerDictionaries() {
		try {
			JarXmlDictionaryParser dictionaryParser = new JarXmlDictionaryParser(getFile().toPath());

			MinecraftDictionaryContext context = MinecraftDictionaryContext.instance();
			String[] dictionaries = new String[] { "English.xml", "French.xml" };
			for (String dictionary : dictionaries)
				try {
					context.register(dictionaryParser.parse(DICTIONARY_FOLDER.resolve(dictionary)));
				} catch (MessageRegisteredException e) {
					e.printStackTrace();
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void registerTabExecutors() {
		PluginCommand startgame = getCommand(gameTree.getStartGameNode().getLabel());
		startgame.setTabCompleter(gameTree.getStartGameNode());
		startgame.setExecutor(gameTree.getStartGameNode());

		PluginCommand pausegame = getCommand(gameTree.getPauseGameNode().getLabel());
		pausegame.setTabCompleter(gameTree.getPauseGameNode());
		pausegame.setExecutor(gameTree.getPauseGameNode());

		PluginCommand stopgame = getCommand(gameTree.getStopGameNode().getLabel());
		stopgame.setTabCompleter(gameTree.getStopGameNode());
		stopgame.setExecutor(gameTree.getStopGameNode());
	}
}
