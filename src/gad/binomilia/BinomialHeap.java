package gad.binomilia;

import java.util.*;

public class BinomialHeap {

	private List<BinomialTreeNode> roots;
	private int minPointer;
	private int n;

	public BinomialHeap() {
		this.roots = new ArrayList<>();
		this.minPointer = -1;
		this.n = 0;
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

			// Check if a node of rank 0 is currently existing
			if (hasRank(this.n, 0)) {
				// Add the new node to the heap and merge it with
				// the existing element of the same rank
				roots.add(nNode);
				result.logIntermediateStep(roots);
				merge(nNode, result);
			} else {
				roots.add(nNode);
				result.logIntermediateStep(roots);
			}
		}

		// Increase the number of all nodes by 1
		// The Increase has to happen at the end otherwise
		// the hasRank method would return wrong values when
		// checking whether a certain rank already is present
		n++;
	}

	public int deleteMin(Result result) {
		if (!roots.isEmpty()) {
			result.startDeleteMin(roots);
			BinomialTreeNode minNode = roots.get(min());
			ArrayList<BinomialTreeNode> children = minNode.getChildren();

			// Remove the minimum node
			roots.remove(minNode);
			result.logIntermediateStep(roots);

			// Add its children to the heap
			// n is not changed since each child element has been part of n before
			for (BinomialTreeNode child :children) {
				if (hasRank(this.n, child.rank())) {
					// Add the child to the heap and merge it with
					// the existing element of the same rank
					roots.add(child);
					result.addToIntermediateStep(roots);
					merge(child, result);
				} else {
					roots.add(child);
					result.addToIntermediateStep(roots);
				}
			}

			// Decrease the number of all nodes by 1
			// The Decrease has to happen at the end otherwise
			// the hasRank method would return wrong values when
			// checking whether a certain rank already is present
			n--;

			resetMinPointer();
			return minNode.min();
		} else {
			throw new NoSuchElementException();
		}
	}

	public void merge(BinomialTreeNode node, Result result) {
		// Find the existing node with the same rank as the new one
		for (int i = 0; i < roots.size(); i++) {
			BinomialTreeNode currentNode = roots.get(i);
			if (currentNode.rank() == node.rank() && currentNode != node) {

				// Merge the two nodes using the merge method in BinomialTreeNode
				BinomialTreeNode mergedNode = BinomialTreeNode.merge(currentNode, node);

				// Remove and log the old nodes
				roots.remove(currentNode);
				roots.remove(node);
				result.addToIntermediateStep(roots); // TODO this logging may be wrong

				// Add the mergedNode, recursively merge the mergedNode
				// if another node with the same rank is present
				if (hasRank(this.n, mergedNode.rank())) {
					roots.add(mergedNode);
					result.addToIntermediateStep(roots);
					merge(mergedNode, result);
				} else {
					roots.add(mergedNode);
					result.addToIntermediateStep(roots);
				}
				resetMinPointer();
				return;
			}
		}
	}

	public boolean hasRank(int n, int rank) {
		return ((n >> rank) & 1) == 1;
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
		for (int i = 0; i < 100; i++) {
			binomialHeap.insert(random.nextInt(-1000, 1000), studentResult);
		}
		System.out.println(dot(binomialHeap.roots));
		if (!binomialHeap.roots.isEmpty()) {
			binomialHeap.deleteMin(studentResult);
		} else {
			// Handle the case when the heap is empty
			System.out.println("EMPTY");
		}
		System.out.println(dot(binomialHeap.roots));
	}
}