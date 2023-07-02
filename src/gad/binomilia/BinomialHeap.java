package gad.binomilia;

import java.util.*;

public class BinomialHeap {

	private List<BinomialTreeNode> roots;
	private int minPointer;

	public BinomialHeap() {
		this.roots = new ArrayList<>();
		this.minPointer = -1;
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

		// Case heap is empty
		if (roots.isEmpty()) {
			roots.add(nNode);
			result.logIntermediateStep(roots);
			minPointer = 0;
		} else {
			// Reset the minPointer if necessary
			if (nNode.min() < roots.get(minPointer).min()) {
				minPointer = roots.size();
			}
			roots.add(nNode);
			result.logIntermediateStep(roots);
			if (hasDoubleRank()) {
				merge(result);
			}
		}
			resetMinPointer();
	}

	public int deleteMin(Result result) {
		if (!roots.isEmpty()) {
			result.startDeleteMin(roots);
			BinomialTreeNode minNode = roots.get(minPointer);
			ArrayList<BinomialTreeNode> children = minNode.getChildren();
			ArrayList<BinomialTreeNode> toBeMerged = new ArrayList<>();

			// Remove the minimum node
			roots.remove(minNode);
			//result.logIntermediateStep(roots);

			// Add its children to the heap and merge them afterwards
			// n is not changed since each child element has been part of n before
			for (BinomialTreeNode child : children) {
				roots.add(child);
			}
			result.logIntermediateStep(roots);
			//result.addToIntermediateStep(roots);
			if (hasDoubleRank()) {
				merge(result);
			}
			if (!roots.isEmpty()) {
				resetMinPointer();
			}
			return minNode.min();
		} else {
			throw new NoSuchElementException();
		}
	}

	public void merge(Result result) {
		// Loop through roots and merge all with same rank
		roots.sort(Comparator.comparing(BinomialTreeNode::rank));
		boolean mergeAgain = true;
		while (mergeAgain) {
			for (int i = 1; i < roots.size(); i++) {
				BinomialTreeNode root1 = roots.get(i - 1);
				BinomialTreeNode root2 = roots.get(i);
				if (root1.rank() == root2.rank()) {
					BinomialTreeNode mergedNode = BinomialTreeNode.merge(root1, root2);
					roots.remove(root1);
					roots.set(i - 1, mergedNode);
					//roots.sort(Comparator.comparing(BinomialTreeNode::rank));
					result.logIntermediateStep(roots);
				}
			}
			if (!hasDoubleRank()) {
				mergeAgain = false;
			}
		}
	}

	public boolean hasDoubleRank() {
		roots.sort(Comparator.comparing(BinomialTreeNode::rank));
		for (int i = 1; i < roots.size(); i++) {
			BinomialTreeNode root1 = roots.get(i - 1);
			BinomialTreeNode root2 = roots.get(i);
			if (root1.rank() == root2.rank()) {
				return true;
			}
		}
		return false;
	}

	public void resetMinPointer() {
		int tempMinVlaue = roots.get(0).min();
		this.minPointer = 0;
		for (int i = 0; i < roots.size(); i++) {
			if (tempMinVlaue > roots.get(i).min()) {
				tempMinVlaue = roots.get(i).min();
				this.minPointer = i;
			}
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

	public static void main(String[] args) {
		BinomialHeap binomialHeap = new BinomialHeap();

		/*// Test hasRank
		int n = 11; // Number of nodes in the binomial heap
		int rank = 0; // Desired rank to check
		boolean hasRank = binomialHeap.hasRank(n, rank);
		System.out.println(hasRank); // Output: true for n element N = {0, 1, 3}*/

		// Test heap
		StudentResult studentResult = new StudentResult();
		Random random = new Random();

		/*//Test 1
		for (int i = 0; i < 2500; i++) {
			binomialHeap.insert(random.nextInt(-1000, 1000), studentResult);
		}
		System.out.println(dot(binomialHeap.roots));
		for (int i = 0; i < 2500; i++) {
			binomialHeap.deleteMin(studentResult);
		}
		System.out.println(dot(binomialHeap.roots));*/

		//Test 2
		for (int i = 0; i < 20; i++) {
			binomialHeap.insert(random.nextInt(-1000, 1000), studentResult);
			if (i % 5 == 0) {
				System.out.println(dot(binomialHeap.roots));
				binomialHeap.deleteMin(studentResult);
				System.out.println(dot(binomialHeap.roots));
			}
		}
	}
}