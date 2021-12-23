package fr.pederobien.minecraft.game.commands.game;

import java.util.StringJoiner;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNodeWrapper;
import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.game.commands.ListNode;
import fr.pederobien.minecraft.game.commands.ListNode.ListNodeBuilder;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.ITeam;

public class GameTeamListNode extends MinecraftCodeNodeWrapper {

	private GameTeamListNode(ListNode<ITeam> node) {
		super(node);
	}

	/**
	 * Creates a ConfigurationTeamListNode based on the given tree in order to display the team list.
	 * 
	 * @param tree The tree that contains the list of team to display.
	 */
	public static GameTeamListNode newInstance(IGame game) {
		return new ConfigurationTeamListNodeBuilder(game).build();
	}

	private static class ConfigurationTeamListNodeBuilder implements ICodeSender {
		private ListNodeBuilder<ITeam> builder;

		/**
		 * Creates a ConfigurationTeamListNodeBuilder based on the given tree.
		 * 
		 * @param tree The tree that contains the list of team to display.
		 */
		public ConfigurationTeamListNodeBuilder(IGame game) {
			builder = ListNode.builder(() -> game.getTeams().toList());
			builder.onNoElement(sender -> send(eventBuilder(EGameCode.GAME_CONFIG__LIST__NO_TEAM_REGISTERED).build(game.getName())));
			builder.onOneElement((sender, team) -> send(eventBuilder(EGameCode.GAME_CONFIG__LIST__ONE_TEAM_REGISTERED).build(game.getName(), team)));
			builder.onSeveralElements((sender, teams) -> {
				StringJoiner joiner = new StringJoiner(", ");
				for (ITeam team : teams)
					joiner.add(team.getColoredName());
				send(eventBuilder(EGameCode.GAME_CONFIG__LIST__SEVERAL_TEAMS_REGISTERED).build(game.getName(), joiner.toString()));
			});
		}

		/**
		 * @return Creates a new ConfigurationTeamListNode.
		 */
		public GameTeamListNode build() {
			return new GameTeamListNode(builder.build(EGameCode.GAME_CONFIG__LIST__EXPLANATION));
		}
	}

}
