package gad.binomilia;

import java.util.ArrayList;

public class BinomialTreeNode {
	private int element;
	private int rank;
	public static ArrayList<BinomialTreeNode> children;
	public static BinomialTreeNode parent;

	public BinomialTreeNode(int element) {
		this.element = element;
	}

	public int min() {
		return element;
	}

	public int rank() {
		if (children.isEmpty()) {
			rank = 0;
		} else {
			rank = children.size();
		}
		return rank;
	}

	public BinomialTreeNode getChildWithRank(int rank) {
		return children.get(children.size() - 1 - rank);
	}

	public static BinomialTreeNode merge(BinomialTreeNode a, BinomialTreeNode b) {
		if (a.element < b.element) {
			// a is new root
			a.addChild(b);
			b.addParent(a);
			return a;
		} else {
			// b is new root
			b.addChild(a);
			a.addParent(b);
			return b;
		}
	}

	public static void addParent(BinomialTreeNode node) {
		parent = node;
	}

	public void addChild(BinomialTreeNode node) {
		node.addParent(this);
		children.add(node);
	}

	public static ArrayList<BinomialTreeNode> getChildren() {
		return children;
	}

	public int dotNode(StringBuilder sb, int idx) {
		sb.append(String.format("\t\t%d [label=\"%d\"];%n", idx, element));
		int rank = rank();
		int next = idx + 1;
		for (int i = 0; i < rank; i++) {
			next = getChildWithRank(i).dotLink(sb, idx, next);
		}
		return next;
	}

	private int dotLink(StringBuilder sb, int idx, int next) {
		sb.append(String.format("\t\t%d -> %d;%n", idx, next));
		return dotNode(sb, next);
	}
}