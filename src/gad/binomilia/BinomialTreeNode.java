package gad.binomilia;

import java.util.ArrayList;

public class BinomialTreeNode {
	private int element;
	private ArrayList<BinomialTreeNode[]> tree;

	public ArrayList<BinomialTreeNode[]> getTree() {
		return tree;
	}

	public BinomialTreeNode(int element) {
		this.element = element;
	}

	public int min() {
		return element;
	}

	public int rank() {
		return 0;
	}

	public BinomialTreeNode getChildWithRank(int rank) {
		return tree.get(rank)[0];
	}

	public static BinomialTreeNode merge(BinomialTreeNode a, BinomialTreeNode b) {
		if (a.rank() == b.rank()) {
			if (a.min() < b.min()) {
				// a is the new root
				for (BinomialTreeNode[] node : b.getTree()) {
					a.tree.add(node);
				}
				return a;
			} else {
				// b is the new root
				for (BinomialTreeNode[] node : a.getTree()) {
					b.tree.add(node);
				}
				return b;
			}
		}
		return a;
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