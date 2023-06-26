package gad.binomilia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BinomialHeap {

	private ArrayList<BinomialTreeNode> binomialHeap;
	public BinomialHeap() {
		this.binomialHeap = new ArrayList<>();
	}

	public int min() {
		return binomialHeap.get(0).min();
	}

	public void insert(int key, Result result) {
		BinomialTreeNode nNode = new BinomialTreeNode(key);
		binomialHeap.add(nNode);
		if (binomialHeap.size() % 2 == 0) {
			for (int i = 1; i < binomialHeap.size(); i++) {
				if (binomialHeap.get(i - 1).rank() == binomialHeap.get(i).rank()) {
					BinomialTreeNode temp = BinomialTreeNode.merge(binomialHeap.get(i - 1), binomialHeap.get(i));
					binomialHeap.remove(binomialHeap.get(i - 1));
					binomialHeap.remove(binomialHeap.get(i));
					binomialHeap.add(temp);
				}
			}
		}
	}

	public int deleteMin(Result result) {
		return 0;
	}

	public static String dot(BinomialTreeNode[] trees) {
		return dot(Arrays.stream(trees).toList());
	}

	public static String dot(Collection<BinomialTreeNode> trees) {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph {").append(System.lineSeparator());
		int id = 0;
		List<Integer> roots = new ArrayList<>();
		for (BinomialTreeNode tree : trees) {
			sb.append(String.format("\tsubgraph cluster_%d {%n", id));
			roots.add(id);
			id = tree.dotNode(sb, id);
			sb.append(String.format("\t}%n"));
		}
		for (int i = 0; i < roots.size() - 1; i++) {
			sb.append(String.format("\t%d -> %d [constraint=false,style=dashed];%n", roots.get(i), roots.get(i + 1)));
		}
		sb.append("}");
		return sb.toString();
	}
}