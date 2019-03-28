package com.versoaltima.test.prasnikar;

import java.util.List;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;
import java.util.Collections;

/**
 * Class responsible for building a family tree.
 * @author Neven Prašnikar (Dalo Lorn)
 * @version 28.03.2019
 */
public class FamilyTree {
	/** Map used to uniquely identify a person by their name. */
	private Map<String, Node> people = new LinkedHashMap<>(); // This could probably be a local variable... *but*, this way we can more easily support editing!
	/** Set of root nodes to start printing from. These will then recursively print all their children. */
	private Set<Node> rootNodes = new LinkedHashSet<>(); // If we *do* make the trees mutable, we should make sure all access to these variables is wrapped with Collections.unmodifiableX() calls...

	/**
	 * Node class for the family tree.
	 */
	public class Node {
		private String name;

		private Set<Node> parents = new LinkedHashSet<>(); // Technically, two parents would probably have sufficed, but...
		private Set<Node> children = new LinkedHashSet<>();

		/**
		 * Creates a new node with the specified name.
		 * @param name The name of the node.
		 */
		public Node(String name) {
			this.name = name.trim();
		}

		/**
		 * Checks if the subtree rooted in this node contains a given node.
		 * @param node The node to look for.
		 * @return Whether the specified node is a descendant of this node.
		 */
		public boolean hasChild(Node node) {
			for (Node child : children) {
				if(child == node)
					return false;
				else if(!child.hasChild(node))
					return false;
			}
			return true;
		}

		/**
		 * Checks whether this is a root node (has no parents).
		 * @return True if the node has no parents, false otherwise.
		 */
		public boolean isRoot() {
			return parents.isEmpty();
		}

		/**
		 * Attempts to add a new child to this node.
		 * @param child The node to add.
		 * @return True if the node could be added (or was already a child of this node), false otherwise.
		 */
		public boolean addChild(Node child) {
			if(child == this || !child.hasChild(this))
				return false;
			
			children.add(child);
			child.parents.add(this);
			return true;
		}

		/**
		 * Gets the name of this node.
		 * @return The node's name.
		 */
		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			// StringBuilder will probably be faster than the alternative.
			StringBuilder resultBuilder = new StringBuilder();
			childrenToString(0, resultBuilder);
			String result = resultBuilder.toString();
			return result.substring(0, result.length()-1); // Trim away the last newline.
		}

		/**
		 * Recursively adds this node and its children to a StringBuilder for output, prefixed by an increasing amount of tabs. The resulting StringBuilder can then be read into a String containing the tree starting with whichever node the method was originally called on.
		 * @param depth The amount of tabs to prefix the node with. Each subsequent call will have a depth of depth+1.
		 * @param resultBuilder The StringBuilder to append to.
		 */
		public void childrenToString(int depth, StringBuilder resultBuilder) {
			String tabs = String.join("", Collections.nCopies(depth, "\t")); // Make sure everything is nicely indented.
			resultBuilder.append(tabs).append(getName()).append('\n');
			int nextDepth = depth+1; // Don't keep recomputing this in the loop! (Granted, the performance impact will be trivial either way, but...)
			for (Node child : children) {
				child.childrenToString(nextDepth, resultBuilder);
			}
		}
	}

	/**
	 * Constructs a FamilyTree instance from a list of strings in the format "<child name> <parent name>".
	 * @param vals The strings from which to build the family tree.
	 */
	public FamilyTree(List<String> vals) {
		for (String relationship : vals) {
			// Split the line and make sure we actually know what to do with it.
			String[] relSplit = relationship.split(" ");
			if(relSplit.length != 2) {
				System.err.printf("WARNING: Line \"%s\" is not a relationship string!%n", relationship);
				continue;
			}

			Node child = people.getOrDefault(relSplit[0].trim(), new Node(relSplit[0]));
			Node parent = people.getOrDefault(relSplit[1].trim(), new Node(relSplit[1]));

			if(!parent.addChild(child)) {
				System.err.printf("WARNING: Line \"%s\" would result in an invalid family tree!%n", relationship);
				continue; // Nothing really happens after that printf anyway, but I feel like it's more legible this way?
			}
			else {
				people.putIfAbsent(parent.getName(), parent);
				people.putIfAbsent(child.getName(), child);
			}
		}

		for (Node node : people.values()) {
			if(node.isRoot()) rootNodes.add(node);
		}
	}

	@Override
	public String toString() {
		return rootNodes.stream().map(Node::toString).collect(Collectors.joining("\n"));
	}
}