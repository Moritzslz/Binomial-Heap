package gad.binomilia;

import java.util.*;

public class BinomialHeap {

	private ArrayList<BinomialTreeNode> binomialHeap;
	public BinomialHeap() {
		this.binomialHeap = new ArrayList<>();
	}

	public int min() {
		if (!binomialHeap.isEmpty()) {
			return binomialHeap.get(0).min();
		} else {
			throw new NoSuchElementException();
		}
	}

	public void insert(int key, Result result) {
		result.startInsert(key, binomialHeap);
		BinomialTreeNode node = new BinomialTreeNode(key);
		binomialHeap.add(node);
		result.logIntermediateStep(binomialHeap);
		if (node.rank() == binomialHeap.get(binomialHeap.size() - 2).rank()) {
			BinomialTreeNode merged = BinomialTreeNode.merge(node, binomialHeap.get(binomialHeap.size() - 2));
			binomialHeap.remove(node);
			binomialHeap.remove(binomialHeap.get(binomialHeap.size() - 2));
			binomialHeap.add(merged);
			for (BinomialTreeNode child : merged.getChildren()) {
				binomialHeap.add(child);
			}
			result.addToIntermediateStep(binomialHeap);
		}
	}

	public int deleteMin(Result result) {
		result.startDeleteMin(binomialHeap);
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