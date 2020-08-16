package org.interview.algorithm.minitree;


import org.interview.algorithm.IndexMinPQ;

import java.util.Arrays;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * 使用Prim algorithm算法实现即时最小生成数
 */
public class InstantPrimTree {

    /**
     * shortest edge from tree vertex to non-tree vertex
     */
    private WeightEdge[] pathArray;

    /**
     * whether target node is visited
     */
    private boolean[] marked;

    private double[] weightArray;

    private IndexMinPQ<Double> priorityQueue;

    public InstantPrimTree(WeightedGraph graph) {
        pathArray = new WeightEdge[graph.getNodeNum()];
        weightArray = new double[graph.getNodeNum()];
        marked = new boolean[graph.getNodeNum()];
        priorityQueue = new IndexMinPQ<>(graph.getNodeNum());

        Arrays.stream(weightArray).forEach(item -> item = Double.POSITIVE_INFINITY);
        // init start node
        weightArray[0] = 0.0;
        priorityQueue.insert(0, 0.0);
        while (!priorityQueue.isEmpty()) {
            prim(graph, priorityQueue.delMin());
        }

    }

    /**
     * using prim algorithm to find prim tree
     *
     * @param graph
     * @param nodeId
     */
    private void prim(WeightedGraph graph, Integer nodeId) {
        marked[nodeId] = true;
        for (WeightEdge weightEdge : graph.adjEdgeList(nodeId)) {
            Integer toNode = weightEdge.another(nodeId);
            // if toNode is visited, this weight edge is invalid
            if (marked[toNode]) {
                continue;
            }

            // update edge if this weight is less than other
            if (weightEdge.getWeight() < weightArray[toNode]) {
                weightArray[toNode] = weightEdge.getWeight();
                pathArray[toNode] = weightEdge;

                if (priorityQueue.contains(toNode)) {
                    priorityQueue.change(toNode, weightArray[toNode]);
                } else {
                    priorityQueue.insert(toNode, weightArray[toNode]);
                }
            }

        }
    }

    /**
     * return all edge list
     *
     * @return
     */
    public Iterable<WeightEdge> edgeList() {
        return Arrays.asList(pathArray);
    }

}
