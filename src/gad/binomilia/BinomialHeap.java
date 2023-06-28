package gad.binomilia;

import java.util.*;

public class BinomialHeap {

	private List<BinomialTreeNode> roots;
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
			result.logIntermediateStep(roots);
		} else {
			if (getBit(n, nNode.rank()) == 1) {
				// An element with the rank 0 is already existing
				roots.add(nNode);
				n++;
				result.logIntermediateStep(nNode);
				for (int i = 0; i < roots.size(); i++) {
					BinomialTreeNode node = roots.get(i);
					if (node.rank() == nNode.rank()) {
						merge(node, nNode, result);
						break;
					}
				}
			} else {
				roots.add(nNode);
				n++;
				if (nNode.min() < roots.get(minPointer).min()) {
					minPointer = roots.size() - 1;
				}
			}
		}
	}

	public int deleteMin(Result result) {
		if (!roots.isEmpty()) {
			result.startDeleteMin(roots);
			BinomialTreeNode min = roots.get(minPointer);
			roots.remove(min);
			n--;
			result.logIntermediateStep(roots);
			if (roots.size() == 0) {
				for (BinomialTreeNode child : min.getChildren()) {
					roots.add(child);
					result.addToIntermediateStep(roots);
				}
			} else {
				for (BinomialTreeNode child : min.getChildren()) {
					roots.add(child);
					result.addToIntermediateStep(roots);
					for (int i = 0; i < roots.size(); i++) {
						BinomialTreeNode node = roots.get(i);
						if (node.rank() == child.rank()) {
							merge(node, child, result);
							break;
						}
					}
				}
			}
			return min.min();
		} else {
			throw new NoSuchElementException();
		}
	}

	public void merge(BinomialTreeNode a, BinomialTreeNode b, Result result) {
		int nRank = -1;
		BinomialTreeNode mergedNode;
		if (a.min() < b.min()) {
			a = BinomialTreeNode.merge(a, b);
			nRank = a.rank();
			mergedNode = a;
			roots.remove(b);
		} else {
			b = BinomialTreeNode.merge(a, b);
			nRank = b.rank();
			mergedNode = b;
			roots.remove(a);
		}

		result.addToIntermediateStep(roots);

		for (int i = 0; i < roots.size(); i++) {
			if (roots.get(i).rank() == nRank && roots.get(i) != mergedNode) {
				merge(roots.get(i), mergedNode, result);
			}
		}

		// Reset minPointer
		int min = roots.get(0).min();
		for (int i = 1; i < roots.size(); i++) {
			if (roots.get(i).min() < min) {
				min = roots.get(i).min();
				minPointer = i;
			}
		}
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

	public static void main(String[] args) {
		BinomialHeap binomialHeap = new BinomialHeap();
		StudentResult studentResult = new StudentResult();
		binomialHeap.insert(4, studentResult);
		for (int i = 0; i < binomialHeap.roots.size(); i++) {
			System.out.println(binomialHeap.roots.get(i).min());
		}
		binomialHeap.insert(5, studentResult);
		for (int i = 0; i < binomialHeap.roots.size(); i++) {
			System.out.println(binomialHeap.roots.get(i).min());
		}
		binomialHeap.insert(6, studentResult);
		for (int i = 0; i < binomialHeap.roots.size(); i++) {
			System.out.println(binomialHeap.roots.get(i).min());
		}
		binomialHeap.insert(7, studentResult);
		for (int i = 0; i < binomialHeap.roots.size(); i++) {
			System.out.println(binomialHeap.roots.get(i).min());
		}
		binomialHeap.deleteMin(studentResult);
		for (int i = 0; i < binomialHeap.roots.size(); i++) {
			System.out.println(binomialHeap.roots.get(i).min());
		}
	}
}