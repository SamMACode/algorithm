package org.interview.graph.minitree;

import org.interview.queue.IndexMinPQ;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author dong
 * @date 2019-02-07
 * className: LazyPrimTree
 * Prim algorithm minimal tree
 */

public class LazyPrimTree {

    /**
     * whether target node is visited
     */
    private boolean[] marked;

    /**
     * minimal tree
     */
    private Queue<WeightEdge> minimalTree;

    /**
     * all Prim tree edge list
     */
    private IndexMinPQ<WeightEdge> priorityQueue;

    private double weight;

    public LazyPrimTree(WeightedGraph graph) {
        priorityQueue = new IndexMinPQ<>(graph.getEdgeNum());
        marked = new boolean[graph.getNodeNum()];
        minimalTree = new ArrayDeque<>();

        scanGraph(graph, 0);
        while (!priorityQueue.isEmpty()) {
            WeightEdge miniEdge = priorityQueue.delMinKey();
            int fromNode = miniEdge.getFrom();
            int toNode = miniEdge.another(fromNode);

            if (marked[fromNode] && marked[toNode]) {
                continue;
            }
            // add minimal weightEdge to priorityQueue
            minimalTree.add(miniEdge);
            weight += miniEdge.getWeight();

            if (!marked[fromNode]) {
                scanGraph(graph, fromNode);
            }
            if (!marked[toNode]) {
                scanGraph(graph, toNode);
            }
        }
    }

    /**
     * scanGraph graph from start node
     * @param graph
     * @param start
     */
    private void scanGraph(WeightedGraph graph, int start) {
        marked[start] = true;
        for (WeightEdge weightEdge : graph.adjEdgeList(start)) {
            if (!marked[weightEdge.another(start)]) {
                priorityQueue.insert(weightEdge);
            }
        }
    }

    public Iterable<WeightEdge> edgeList() {
        return priorityQueue;
    }

    public double getWeight() {
        return weight;
    }

    public Iterable<WeightEdge> minimalTree() {
        return this.minimalTree;
    }

    public static void main(String[] args) {
        // test data
        WeightedGraph weightedGraph = new WeightedGraph(6);
        weightedGraph.addWeightEdge(new WeightEdge(1, 2, 0.25));
        weightedGraph.addWeightEdge(new WeightEdge(2, 3, 0.65));
        weightedGraph.addWeightEdge(new WeightEdge(2, 4, 0.55));

        LazyPrimTree primTree = new LazyPrimTree(weightedGraph);
        System.out.println("primTree wight: " + primTree.getWeight());
    }
}
