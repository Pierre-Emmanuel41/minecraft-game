package fr.pederobien.minecraft.game.commands.game;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IGame;

public class GameFeatureNode extends GameNode {
	private GameFeatureEnableNode enableNode;
	private GameFeatureDisableNode disableNode;
	private GameFeatureArgumentNode argumentNode;

	protected GameFeatureNode(IGame game) {
		super(game, "feature", EGameCode.GAME_CONFIG__FEATURE__EXPLANATION, g -> g != null);
		add(enableNode = new GameFeatureEnableNode(game));
		add(disableNode = new GameFeatureDisableNode(game));
		add(argumentNode = new GameFeatureArgumentNode(game));
	}

	/**
	 * @return The node that enables features.
	 */
	public GameFeatureEnableNode getEnableNode() {
		return enableNode;
	}

	/**
	 * @return The node that disable features.
	 */
	public GameFeatureDisableNode getDisableNode() {
		return disableNode;
	}

	/**
	 * @return The node that set the start argument of a feature.
	 */
	public GameFeatureArgumentNode getArgumentNode() {
		return argumentNode;
	}
}
