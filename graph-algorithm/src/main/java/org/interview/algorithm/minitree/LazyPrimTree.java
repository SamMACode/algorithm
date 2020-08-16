package org.interview.algorithm.minitree;


import org.interview.algorithm.MinPQ;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * Prim algorithm实现最小生成树（Lazy）
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
    private MinPQ<WeightEdge> priorityQueue;

    private double weight;

    public LazyPrimTree(WeightedGraph graph) {
        priorityQueue = new MinPQ<>(graph.getEdgeNum());
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
     *
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

}
