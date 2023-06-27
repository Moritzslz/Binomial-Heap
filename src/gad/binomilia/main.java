package gad.binomilia;

import java.util.Random;

public class main {
    public static void main(String[] args) {
        Random random = new Random();
        BinomialTreeNode one = new BinomialTreeNode(2);
        BinomialTreeNode two = new BinomialTreeNode(4);
        one.addChild(two);
        BinomialTreeNode three = new BinomialTreeNode(6);
        BinomialTreeNode four = new BinomialTreeNode(8);
        three.addChild(four);
        BinomialTreeNode.merge(one, two);
        System.out.println(one.getChildWithRank(0).min());
    }
}
