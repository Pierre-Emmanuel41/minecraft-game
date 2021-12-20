package fr.pederobien.minecraft.game;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.dictionary.impl.JarXmlDictionaryParser;
import fr.pederobien.minecraft.dictionary.impl.MinecraftDictionaryContext;
import fr.pederobien.minecraft.game.impl.PlayerQuitOrJoinEventHandler;
import fr.pederobien.utils.event.EventLogger;

public class GamePlugin extends JavaPlugin {
	private static final Path DICTIONARY_FOLDER = Paths.get("resources/dictionaries");

	private static Plugin instance;

	/**
	 * @return The instance of this plugin.
	 */
	public static Plugin instance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		EventLogger.instance().register();
		PlayerQuitOrJoinEventHandler.instance().register(this);

		try {
			JarXmlDictionaryParser dictionaryParser = new JarXmlDictionaryParser(getFile().toPath());

			MinecraftDictionaryContext context = MinecraftDictionaryContext.instance();
			String[] dictionaries = new String[] { "English.xml", "French.xml" };
			for (String dictionary : dictionaries)
				context.register(dictionaryParser.parse(DICTIONARY_FOLDER.resolve(dictionary)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
