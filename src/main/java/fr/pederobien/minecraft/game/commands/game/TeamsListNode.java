package fr.pederobien.minecraft.game.commands.game;

import java.util.StringJoiner;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNodeWrapper;
import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.game.commands.ListNode;
import fr.pederobien.minecraft.game.commands.ListNode.ListNodeBuilder;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.ITeamList;

public class TeamsListNode extends MinecraftCodeNodeWrapper {

	private TeamsListNode(ListNode<ITeam> node) {
		super(node);
	}

	/**
	 * Creates a ConfigurationTeamListNode based on the given tree in order to display the team list.
	 * 
	 * @param tree The tree that contains the list of team to display.
	 */
	public static TeamsListNode newInstance(ITeamList teams) {
		return new GameTeamListNodeBuilder(teams).build();
	}

	private static class GameTeamListNodeBuilder implements ICodeSender {
		private ListNodeBuilder<ITeam> builder;

		/**
		 * Creates a ConfigurationTeamListNodeBuilder based on the given tree.
		 * 
		 * @param tree The tree that contains the list of team to display.
		 */
		public GameTeamListNodeBuilder(ITeamList teams) {
			builder = ListNode.builder(() -> teams.toList());
			builder.onNoElement(sender -> sendSuccessful(sender, EGameCode.GAME_CONFIG__LIST__NO_TEAM_REGISTERED, teams.getName()));
			builder.onOneElement((sender, team) -> sendSuccessful(sender, EGameCode.GAME_CONFIG__LIST__ONE_TEAM_REGISTERED, teams.getName(), team));
			builder.onSeveralElements((sender, teamsList) -> {
				StringJoiner joiner = new StringJoiner(", ");
				for (ITeam team : teamsList)
					joiner.add(team.getColoredName());
				sendSuccessful(sender, EGameCode.GAME_CONFIG__LIST__SEVERAL_TEAMS_REGISTERED, teams.getName(), joiner.toString());
			});
		}

		/**
		 * @return Creates a new ConfigurationTeamListNode.
		 */
		public TeamsListNode build() {
			return new TeamsListNode(builder.build(EGameCode.GAME_CONFIG__LIST__EXPLANATION));
		}
	}
}
