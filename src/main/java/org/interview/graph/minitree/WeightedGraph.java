package org.interview.graph.minitree;

import java.util.ArrayList;

/**
 * @author dong
 * @date 2019-01-31
 * className: WeightedGraph
 * Comparable weight edge
 */

public class WeightedGraph {

    private final int nodeNum;

    private int edgeNum;

    private ArrayList<Integer>[] adjTable;

    public WeightedGraph(int nodeNum) {
        this.nodeNum = nodeNum;
        this.edgeNum = 0;
        this.adjTable = new ArrayList[nodeNum];
        for (int vertex = 0; vertex < nodeNum; vertex++) {
            adjTable[vertex] = new ArrayList<>();
        }
    }

    public void addWeightEdge(WeightEdge edge) {
        int fromNode = edge.getFrom();
        int toNode = edge.another(fromNode);

        adjTable[fromNode].add(toNode);
        adjTable[toNode].add(fromNode);
        edgeNum ++;
    }

    /**
     * getter method
     */
    public int getNodeNum() {
        return nodeNum;
    }

    public int getEdgeNum() {
        return edgeNum;
    }
}
