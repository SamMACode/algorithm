package org.interview.graph.minitree;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dong
 * @date 2019-01-31
 * className: WeightedGraph
 * Comparable weight edge
 */

public class WeightedGraph {

    private final int nodeNum;

    private int edgeNum;

    private ArrayList<WeightEdge>[] adjTable;

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

        adjTable[fromNode].add(edge);
        adjTable[toNode].add(edge);
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

    /**
     * find all adjacent edge list by vertex
     * @param vertex
     * @return
     */
    public Iterable<WeightEdge> adjEdgeList(int vertex) {
        ArrayList<WeightEdge> adjList = adjTable[vertex];
        if (CollectionUtils.isEmpty(adjList)) {
            return Collections.emptyList();
        }
        return adjList;
    }

    /**
     * find all edges in this graph
     * @return
     */
    public Iterable<WeightEdge> edgeList() {
        Set<WeightEdge> adjEdgeSet = Arrays.stream(adjTable).flatMap(edgeList -> edgeList.stream())
                .collect(Collectors.toSet());
        return adjEdgeSet;
    }
}
