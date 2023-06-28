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
			if (roots.get(nNode.rank()) != null) {
				// An element with the same rank as the inserted node already exists
				ArrayList<BinomialTreeNode> temp = new ArrayList<>();
				temp.add(nNode.rank(), nNode);
				merge(temp, nNode.rank(), result);
				result.logIntermediateStep(roots);
			} else {
				roots.add(nNode.rank(), nNode);
				n++;
				result.logIntermediateStep(roots);
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

	public void merge(ArrayList<BinomialTreeNode> tree, int rank, Result result) {
		BinomialTreeNode mergedNode = BinomialTreeNode.merge(roots.get(rank), tree.get(rank));
		roots.remove(rank);
		result.addToIntermediateStep(roots);
		if (roots.get(mergedNode.rank()) != null) {
			ArrayList<BinomialTreeNode> temp = new ArrayList<>();
			temp.add(mergedNode.rank(), mergedNode);
			merge(temp, mergedNode.rank(), result);
		} else {
			roots.add(mergedNode.rank(), mergedNode);
			result.addToIntermediateStep(roots);
		}
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