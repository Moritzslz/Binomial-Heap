package gad.binomilia;

import java.util.Collection;

public interface Result {
	void startInsert(int value, Collection<BinomialTreeNode> heap);

	void startInsert(int value, BinomialTreeNode[] heap);

	void startDeleteMin(Collection<BinomialTreeNode> heap);

	void startDeleteMin(BinomialTreeNode[] heap);

	void logIntermediateStep(Collection<BinomialTreeNode> heap);

	void logIntermediateStep(BinomialTreeNode[] heap);

	void logIntermediateStep(BinomialTreeNode tree);

	void addToIntermediateStep(Collection<BinomialTreeNode> heap);

	void addToIntermediateStep(BinomialTreeNode[] heap);

	void addToIntermediateStep(BinomialTreeNode tree);

	void printCurrentIntermediateStep();
}