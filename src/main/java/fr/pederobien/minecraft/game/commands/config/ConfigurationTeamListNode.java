package fr.pederobien.minecraft.game.commands.config;

import java.util.StringJoiner;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNodeWrapper;
import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.game.commands.ListNode;
import fr.pederobien.minecraft.game.commands.ListNode.ListNodeBuilder;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.game.interfaces.ITeam;

public class ConfigurationTeamListNode extends MinecraftCodeNodeWrapper {

	private ConfigurationTeamListNode(ListNode<ITeam> node) {
		super(node);
	}

	/**
	 * Creates a ConfigurationTeamListNode based on the given tree in order to display the team list.
	 * 
	 * @param tree The tree that contains the list of team to display.
	 */
	public static ConfigurationTeamListNode newInstance(ConfigurationCommandTree tree) {
		return new ConfigurationTeamListNodeBuilder(tree).build();
	}

	private static class ConfigurationTeamListNodeBuilder implements ICodeSender {
		private ListNodeBuilder<ITeam> builder;

		/**
		 * Creates a ConfigurationTeamListNodeBuilder based on the given tree.
		 * 
		 * @param tree The tree that contains the list of team to display.
		 */
		public ConfigurationTeamListNodeBuilder(ConfigurationCommandTree tree) {
			builder = ListNode.builder(() -> tree.getConfiguration().getTeams().toList());
			builder.onNoElement(sender -> send(eventBuilder(EGameCode.GAME_CONFIG__LIST__NO_TEAM_REGISTERED).build(tree.getConfiguration().getName())));
			builder.onOneElement((sender, team) -> send(eventBuilder(EGameCode.GAME_CONFIG__LIST__ONE_TEAM_REGISTERED).build(tree.getConfiguration().getName(), team)));
			builder.onSeveralElements((sender, teams) -> {
				StringJoiner joiner = new StringJoiner(", ");
				for (ITeam team : teams)
					joiner.add(team.getColoredName());
				send(eventBuilder(EGameCode.GAME_CONFIG__LIST__SEVERAL_TEAMS_REGISTERED).build(tree.getConfiguration().getName(), joiner.toString()));
			});
		}

		/**
		 * @return Creates a new ConfigurationTeamListNode.
		 */
		public ConfigurationTeamListNode build() {
			return new ConfigurationTeamListNode(builder.build(EGameCode.GAME_CONFIG__LIST__EXPLANATION));
		}
	}

}
