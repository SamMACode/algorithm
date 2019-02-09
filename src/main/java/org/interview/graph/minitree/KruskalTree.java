package org.interview.graph.minitree;


import org.interview.find.UnionFind;
import org.interview.queue.MinPQ;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author dong
 * @date 2019-02-07
 * className: KruskalTree
 * kruskal algorithm minimal tree
 */

public class KruskalTree {

    private Queue<WeightEdge> minimalTree;

    public KruskalTree(WeightedGraph graph) {
        minimalTree = new PriorityQueue<>();
        MinPQ<WeightEdge> priorityQueue = new MinPQ<>();
        // add all edges to priority list
        graph.edgeList().forEach(edge -> priorityQueue.insert(edge));

        UnionFind unionFind = new UnionFind(graph.getNodeNum());

        while(!priorityQueue.isEmpty() && minimalTree.size() < graph.getNodeNum()-1) {
            // get the minimal edge from priority queue
            WeightEdge minimalEdge = priorityQueue.delMinKey();
            Integer from = minimalEdge.getFrom();
            Integer to = minimalEdge.another(from);

            if (unionFind.connected(from, to)) {
                continue;
            }
            unionFind.unionItem(from, to);
            minimalTree.add(minimalEdge);
        }
    }

    public Iterable<WeightEdge> edgeList() {
        return minimalTree;
    }

    /**
     * total weight of minimal tree
     * @return
     */
    public double totalWeight() {
        return minimalTree.stream().mapToDouble(WeightEdge::getWeight).sum();
    }
}
