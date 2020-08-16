package org.interview.algorithm.minitree;



import org.interview.algorithm.MinPQ;
import org.interview.algorithm.UnionFind;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * kruskal algorithm算法实现最小生成树
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
