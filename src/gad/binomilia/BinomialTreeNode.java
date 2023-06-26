package gad.binomilia;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BinomialTreeNode {
	private int element;
	private int rank;
	private static BinomialTreeNode[] children;
	private static BinomialTreeNode parent;

	public BinomialTreeNode(int element) {
		this.element = element;
		children = new BinomialTreeNode[2];
	}

	public int min() {
		return element;
	}

	public int rank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public BinomialTreeNode getChildWithRank(int rank) {
		if (this.rank == rank) {
			if (children[0] != null) {
				return children[0];
			} else {
				throw new NoSuchElementException();
			}
		} else {
			return children[0].getChildWithRank(rank);
		}
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

	public static boolean addChild(BinomialTreeNode node) {
		if (children[0] == null) {
			children[0] = node;
			return true;
		} else if (children[1] == null) {
			children[1] = node;
			return true;
		} else {
			return false;
		}
	}

	public static BinomialTreeNode[] getChildren() {
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