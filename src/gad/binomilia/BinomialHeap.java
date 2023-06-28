package gad.binomilia;

import java.util.*;

public class BinomialHeap {

	private ArrayList<BinomialTreeNode> roots;
	private int minPointer;
	private int n;

	public BinomialHeap() {
		this.roots = new ArrayList<>();
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
		BinomialTreeNode nNode = new BinomialTreeNode(key);
		if (roots.isEmpty()) {
			roots.add(nNode.rank(), nNode);
			n = 1;
			minPointer = 0;
			result.logIntermediateStep(roots);
		} else {
			if (roots.get(nNode.rank()) == null) {
				roots.add(nNode.rank(), nNode);
				result.logIntermediateStep(roots);
			} else {
				roots.add(nNode);
				result.logIntermediateStep(roots);
				merge(roots.get(nNode.rank()), roots.get(roots.size() - 1), nNode.rank(), result);
				result.addToIntermediateStep(roots);
			}
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

	public void merge(BinomialTreeNode a, BinomialTreeNode b, int rank, Result result) {
		BinomialTreeNode mergedNode = BinomialTreeNode.merge(a, b);
		roots.remove(rank);
		roots.remove(roots.size() - 1);
		result.addToIntermediateStep(roots);
		if (roots.get(mergedNode.rank()) == null) {
			roots.add(mergedNode);
			result.addToIntermediateStep(roots);
		} else {
			roots.add(mergedNode);
			result.addToIntermediateStep(roots);
			merge(roots.get(mergedNode.rank()), roots.get(roots.size() - 1), mergedNode.rank(), result);
		}
		result.addToIntermediateStep(roots);
	}

	public static int getBit(int element, int binPlace) {
		return (element >> binPlace) & 1;
	}

	public int getN() {
		return n;
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