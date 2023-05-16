package gad.binomilia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StudentResult implements Result {
	private List<BinomialTreeNode> currentHeap = new ArrayList<>();

	@Override
	public void startInsert(int value, Collection<BinomialTreeNode> heap) {
		System.out.println("Starte Einfügen mit Heap mit " + heap.size() + " Bäumen");
		currentHeap.clear();
		currentHeap.addAll(heap);
	}

	@Override
	public void startInsert(int value, BinomialTreeNode[] heap) {
		System.out.println("Starte Einfügen mit Heap mit " + heap.length + " Bäumen");
		currentHeap.clear();
		currentHeap.addAll(Arrays.stream(heap).toList());
	}

	@Override
	public void startDeleteMin(Collection<BinomialTreeNode> heap) {
		System.out.println("Starte Löschen mit Heap mit " + heap.size() + " Bäumen");
		currentHeap.clear();
		currentHeap.addAll(heap);
	}

	@Override
	public void startDeleteMin(BinomialTreeNode[] heap) {
		System.out.println("Starte Löschen mit Heap mit " + heap.length + " Bäumen");
		currentHeap.clear();
		currentHeap.addAll(Arrays.stream(heap).toList());
	}

	@Override
	public void logIntermediateStep(Collection<BinomialTreeNode> heap) {
		System.out.println("Zwischenschritt mit " + heap.size() + " Bäumen");
		currentHeap.clear();
		currentHeap.addAll(heap);
	}

	@Override
	public void logIntermediateStep(BinomialTreeNode[] heap) {
		System.out.println("Zwischenschritt mit " + heap.length + " Bäumen");
		currentHeap.clear();
		currentHeap.addAll(Arrays.stream(heap).toList());
	}

	@Override
	public void logIntermediateStep(BinomialTreeNode tree) {
		System.out.println("Zwischenschritt mit einem Baum");
		currentHeap.clear();
		currentHeap.add(tree);
	}

	@Override
	public void addToIntermediateStep(Collection<BinomialTreeNode> additionalHeap) {
		System.out.println("Es wurden " + additionalHeap.size() + " weitere Bäume zum Zwischenschritt hinzugefügt");
		currentHeap.addAll(additionalHeap);
	}

	@Override
	public void addToIntermediateStep(BinomialTreeNode[] additionalHeap) {
		System.out.println("Es wurden " + additionalHeap.length + " weitere Bäume zum Zwischenschritt hinzugefügt");
		currentHeap.addAll(Arrays.stream(additionalHeap).toList());
	}

	@Override
	public void addToIntermediateStep(BinomialTreeNode tree) {
		System.out.println("Es wurde ein weiter Baum zum Zwischenschritt hinzugefügt");
		currentHeap.add(tree);
	}

	@Override
	public void printCurrentIntermediateStep() {
		System.out.println(BinomialHeap.dot(currentHeap));
	}
}