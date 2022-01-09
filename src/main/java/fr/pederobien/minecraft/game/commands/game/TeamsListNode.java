package fr.pederobien.minecraft.game.commands.game;

import java.util.StringJoiner;
import java.util.function.Supplier;

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
	 * Creates a TeamsListNode based on the given teams list in order to display the teams from the list.
	 * 
	 * @param teams The list of teams to display.
	 */
	public static TeamsListNode newInstance(Supplier<ITeamList> teams) {
		return new GameTeamListNodeBuilder(teams).build();
	}

	private static class GameTeamListNodeBuilder implements ICodeSender {
		private ListNodeBuilder<ITeam> builder;

		/**
		 * Creates a TeamsListNode based on the given teams list in order to display the teams from the list.
		 * 
		 * @param teams The list of teams to display.
		 */
		public GameTeamListNodeBuilder(Supplier<ITeamList> teams) {
			builder = ListNode.builder(() -> teams.get().toList());
			builder.onNoElement(sender -> sendSuccessful(sender, EGameCode.GAME_CONFIG__LIST__NO_TEAM_REGISTERED, teams.get().getName()));
			builder.onOneElement((sender, team) -> sendSuccessful(sender, EGameCode.GAME_CONFIG__LIST__ONE_TEAM_REGISTERED, teams.get().getName(), team));
			builder.onSeveralElements((sender, teamsList) -> {
				StringJoiner joiner = new StringJoiner(", ");
				for (ITeam team : teamsList)
					joiner.add(team.getColoredName());
				sendSuccessful(sender, EGameCode.GAME_CONFIG__LIST__SEVERAL_TEAMS_REGISTERED, teams.get().getName(), joiner.toString());
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
