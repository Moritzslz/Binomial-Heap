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
		ArrayList<BinomialTreeNode> nHeap = new ArrayList<>();
		BinomialTreeNode nNode = new BinomialTreeNode(key);
		nHeap.add(nNode);
		roots = merge(roots, nHeap);
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

	public ArrayList<BinomialTreeNode> merge(ArrayList<BinomialTreeNode> a, ArrayList<BinomialTreeNode> b) {
		ArrayList<BinomialTreeNode> nRoots = new ArrayList<>();
		// Get the number of elements of each tree
		int nA = 0;
		int nB = 0;
		for (int i = 0; i < a.size(); i++){
			nA += a.get(i).getChildren().size() + 1;
		}
		for (int i = 0; i < b.size(); i++){
			nB += b.get(i).getChildren().size() + 1;
		}
		// Compare bits of each n
		for (int i = 0; i < 32; i++) {
			if (getBit(nA, i) == 1 && getBit(nB, i) == 1) {
				// Merge trees of rank i
				// Get root with rank i from a
				BinomialTreeNode aRoot = a.get(0);
				for (int aIdx = 0; aIdx < a.size(); aIdx++) {
					if (a.get(aIdx).rank() == i) {
						aRoot = a.get(aIdx);
					}
				}
				// Get root with rank i from b
				BinomialTreeNode bRoot = b.get(0);
				for (int bIdx = 0; bIdx < a.size(); bIdx++) {
					if (b.get(bIdx).rank() == i) {
						bRoot = a.get(bIdx);
					}
				}
				BinomialTreeNode nNode = BinomialTreeNode.merge(aRoot, bRoot);
				nRoots.add(nNode);
			} else if (getBit(nA, i) == 1) {
				BinomialTreeNode aRoot = a.get(0);
				for (int aIdx = 0; aIdx < a.size(); aIdx++) {
					if (a.get(aIdx).rank() == i) {
						aRoot = a.get(aIdx);
					}
				}
				nRoots.add(aRoot);
			} else if (getBit(nB, i) == 1) {
				BinomialTreeNode bRoot = b.get(0);
				for (int bIdx = 0; bIdx < a.size(); bIdx++) {
					if (b.get(bIdx).rank() == i) {
						bRoot = a.get(bIdx);
					}
				}
				nRoots.add(bRoot);
			} else {
				// Add nothing
			}
		}
		return nRoots;
	}

	public static int getBit(int element, int binPlace) {
		return (element >> binPlace) & 1;
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