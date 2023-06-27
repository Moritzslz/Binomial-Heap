package gad.binomilia;

import java.util.*;

public class BinomialHeap {

	private ArrayList<BinomialTreeNode> roots;
	private int minPointer;
	public BinomialHeap() {
		this.roots = new ArrayList<>();
		minPointer = 0;
	}

	public int min() {
		if (!roots.isEmpty()) {
			return roots.get(minPointer).min();
		} else {
			throw new NoSuchElementException();
		}
	}

	public void insert(int key, Result result) {
		result.startInsert(key, roots);
		BinomialTreeNode nRoot = new BinomialTreeNode(key);
		roots.add(nRoot);
		if (nRoot.min() < roots.get(minPointer).min()) {
			minPointer =roots.size() - 1;
		}
		result.logIntermediateStep(roots);
		// Merge with existing root from the same rank
		if (nRoot.rank() == roots.get(roots.size() - 2).rank()) {
			BinomialTreeNode merged = BinomialTreeNode.merge(roots.get(roots.size() - 2), nRoot);
		}
	}

	public int deleteMin(Result result) {
		if (!roots.isEmpty()) {
			result.startDeleteMin(roots);
			return 0;
		} else {
			throw new NoSuchElementException();
		}
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