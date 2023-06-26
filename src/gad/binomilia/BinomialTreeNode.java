package gad.binomilia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BinomialTreeNode {
	private int element;
	private ArrayList<BinomialTreeNode> children;

	public BinomialTreeNode(int element) {
		this.element = element;
		this.children = new ArrayList<>();
	}

	public int min() {
		return element;
	}

	public int rank() {
		return children.size();
	}

	public BinomialTreeNode getChildWithRank(int rank) {
		return children.get(rank);
	}

	public static BinomialTreeNode merge(BinomialTreeNode a, BinomialTreeNode b) {
		if (a.rank() == b.rank()) {
			if (a.min() <= b.min()) {
				// a is the new root
				a.children.add(b);
				a.children.sort(Comparator.comparing(BinomialTreeNode::min));
				return a;
			} else {
				// b is the new root
				b.children.add(a);
				b.children.sort(Comparator.comparing(BinomialTreeNode::min));
				return b;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	public ArrayList<BinomialTreeNode> getChildren() {
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