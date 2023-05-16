package gad.binomilia;

public class BinomialTreeNode {
	private int element;

	public BinomialTreeNode(int element) {
		this.element = element;
	}

	public int min() {
		return 0;
	}

	public int rank() {
		return 0;
	}

	public BinomialTreeNode getChildWithRank(int rank) {
		return null;
	}

	public static BinomialTreeNode merge(BinomialTreeNode a, BinomialTreeNode b) {
		return null;
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