package org.interview.algorithm;

import java.util.LinkedList;

/**
 * @author Sam Ma
 * @date 2020/10/07
 * 定义Graph图对象, vertex表示图中顶点的个数、adj[]为图中的临接表
 */
public class Graph {
    /**
     * 用于表示图中顶点的个数
     */
    private int vertex;

    /**
     * 用于表示图中临接表数量
     */
    private LinkedList<Integer> adj[];

    public Graph(int vertex) {
        this.vertex = vertex;
        adj = new LinkedList[vertex];
        for (int i = 0; i < vertex; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    /**
     * 在图中添加一条关系边，边的指向为from->to
     *
     * @param from
     * @param to
     */
    public void addEdge(int from, int to) {
        adj[from].add(to);
    }

    public int getVertex() {
        return vertex;
    }

    public LinkedList<Integer>[] getAdj() {
        return adj;
    }

}