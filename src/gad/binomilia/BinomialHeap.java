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
			roots.add(nNode);
			n = 1;
			minPointer = 0;
			result.addToIntermediateStep(roots);
			return;
		}
		ArrayList<BinomialTreeNode> nRoots = new ArrayList<>();
		nRoots.add(nNode);
		roots = merge(roots, nRoots, 1);
		result.addToIntermediateStep(roots);
	}

	public int deleteMin(Result result) {
		if (!roots.isEmpty()) {
			result.startDeleteMin(roots);
			return 0;
		} else {
			throw new NoSuchElementException();
		}
	}

	public ArrayList<BinomialTreeNode> merge(ArrayList<BinomialTreeNode> a, ArrayList<BinomialTreeNode> b, int nB) {
		ArrayList<BinomialTreeNode> nRoots = new ArrayList<>();
		// Get the number of elements of each tree
		int nA = n;

		// Compare bits of each n
		for (int i = 0; i < 32; i++) {
			if (getBit(nA, i) == 1 && getBit(nB, i) == 1) {
				// Merge trees of rank i
				// Get root with rank i from a
				BinomialTreeNode aRoot = a.get(i);
				// Get root with rank i from b
				BinomialTreeNode bRoot = b.get(i);
				BinomialTreeNode nNode = BinomialTreeNode.merge(aRoot, bRoot);
				nRoots.add(nNode);
			} else if (getBit(nA, i) == 1) {
				BinomialTreeNode aRoot = a.get(i);
				nRoots.add(aRoot);
			} else if (getBit(nB, i) == 1) {
				BinomialTreeNode bRoot = b.get(i);
				nRoots.add(bRoot);
			} else {
				// Add nothing
			}
		}
		int minElement = nRoots.get(0).min();
		minPointer = 0;
		for (int i = 1; i < nRoots.size(); i++) {
			if (nRoots.get(i).min() < minElement) {
				minElement = nRoots.get(i).min();
				minPointer = i;
			}
		}
		return nRoots;
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