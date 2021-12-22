package fr.pederobien.minecraft.game.commands;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;

public class ListNode<T> extends MinecraftCodeNode {
	private ListNodeBuilder<T> builder;

	private ListNode(ListNodeBuilder<T> builder) {
		super("list", builder.explanation, () -> true);
		this.builder = builder;
	}

	/**
	 * Creates a ListNodeBuilder in order to display a specific list. The <code>List&lt;U&gt;</code> parameter refers to the list of
	 * string to display.
	 * 
	 * @param onListRequest Supplier in order to get a list to display.
	 * 
	 * @return A new instance of a ListNodeBuilder.
	 */
	public static <U> ListNodeBuilder<U> builder(Supplier<List<U>> onListRequest) {
		return new ListNodeBuilder<U>(onListRequest);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		List<T> list = builder.onListRequest.get();
		switch (list.size()) {
		case 0:
			builder.onNoElement.accept(sender);
			break;
		case 1:
			builder.onOneElement.accept(sender, list.get(0));
			break;
		default:
			builder.onSeveralElements.accept(sender, list);
			break;
		}
		return true;
	}

	public static class ListNodeBuilder<T> {
		private Supplier<List<T>> onListRequest;
		private Consumer<CommandSender> onNoElement;
		private BiConsumer<CommandSender, T> onOneElement;
		private BiConsumer<CommandSender, List<T>> onSeveralElements;
		private IMinecraftCode explanation;

		/**
		 * Creates a ListNodeBuilder in order to display a list. The <code>List&lt;T&gt;</code> parameter refers to the list of elements
		 * to display.
		 * 
		 * @param onListRequest Supplier in order to get a list to display.
		 * 
		 * @return A new instance of a ListNodeBuilder.
		 */
		private ListNodeBuilder(Supplier<List<T>> onListRequest) {
			this.onListRequest = onListRequest;
		}

		/**
		 * @return The supplier that provides a list of String to display.
		 */
		public Supplier<List<T>> getOnListRequest() {
			return onListRequest;
		}

		/**
		 * Set the action to perform when there is no element in the list. The {@link CommandSender} refers to the entity that run the
		 * command.
		 * 
		 * @param onNoElement The action to perform.
		 */
		public ListNodeBuilder<T> onNoElement(Consumer<CommandSender> onNoElement) {
			this.onNoElement = onNoElement;
			return this;
		}

		/**
		 * @return The action to perform when there is no element in the list.
		 */
		public Consumer<CommandSender> getOnNoElement() {
			return onNoElement;
		}

		/**
		 * Set the action to perform when there is one element in the list. The {@link CommandSender} refers to the entity that run the
		 * command, the <code>T</code> parameter refers to the element in the list.
		 * 
		 * @param onOneElement The action to perform.
		 */
		public ListNodeBuilder<T> onOneElement(BiConsumer<CommandSender, T> onOneElement) {
			this.onOneElement = onOneElement;
			return this;
		}

		/**
		 * @return The action to perform when there is one element in the list.
		 */
		public BiConsumer<CommandSender, T> getOnOneElement() {
			return onOneElement;
		}

		/**
		 * Set the action to perform when there are several elements in the list. The {@link CommandSender} refers to the entity that run
		 * the command, the <code>List&lt;T&gt;</code> parameter refers to list of elements.
		 * 
		 * @param onSeveralElements The action to perform.
		 */
		public ListNodeBuilder<T> onSeveralElements(BiConsumer<CommandSender, List<T>> onSeveralElements) {
			this.onSeveralElements = onSeveralElements;
			return this;
		}

		/**
		 * @return The action to perform when there are several elements in the list.
		 */
		public BiConsumer<CommandSender, List<T>> getOnSeveralElements() {
			return onSeveralElements;
		}

		/**
		 * Creates a new {@link ListNode} based on the previous parameter.
		 * 
		 * @param explanation The node explanation.
		 * 
		 * @return A new node that display the list.
		 * 
		 * @throws IllegalArgumentException if one parameter is null.
		 */
		public ListNode<T> build(IMinecraftCode explanation) {
			if (onNoElement == null || onOneElement == null || onSeveralElements == null || explanation == null)
				throw new IllegalArgumentException();

			this.explanation = explanation;
			return new ListNode<T>(this);
		}

		/**
		 * @return The node explanation.
		 */
		public IMinecraftCode getExplanation() {
			return explanation;
		}
	}
}
