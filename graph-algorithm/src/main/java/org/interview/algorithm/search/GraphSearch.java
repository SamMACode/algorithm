package org.interview.algorithm.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Sam Ma
 * @date 2021/03/06
 * 使用深度优先、广度优先算法实现Graph图的搜索
 */
public class GraphSearch {

    private static final Logger logger = LoggerFactory.getLogger(GraphSearch.class);

    // 表示顶点的个数
    private int vertexNum;

    // 临接表（点之间的关系）
    private LinkedList<Integer> adj[];

    public GraphSearch(int nodeNum) {
        this.vertexNum = nodeNum;
        adj = new LinkedList[nodeNum];
        for (int i = 0; i < vertexNum; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    /* 在无向图中一次存储两条关系边 */
    public void addEdge(int fromId, int endId) {
        adj[fromId].add(endId);
        adj[endId].add(fromId);
    }

    /* 图的广度优先搜索实现的代码，其中startId表示起始点id, endId表示结束点id */
    public void bfs(int startId, int endId) {
        if (startId == endId) return;
        // 构建visited数组，对于已经访问的过的元素标记visited属性为true
        boolean[] visited = new boolean[vertexNum];
        visited[startId] = true;
        // 广度优先 用queue保存这一层访问过的节点 (queue特点 先进先出)
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startId);

        // prev数组用于保存路径, 当标记点A的neighbor节点id时, 记录prev[neighbor]=A
        int[] prev = new int[vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            prev[i] = -1;
        }

        /* 核心地方，使用queue保存发散的这一层已经访问过的节点，每一层逐层访问 */
        while (queue.size() != 0) {
            int visitedId = queue.poll();
            // 逐层扩展的实现，每次从queue中弹出一个元素，然后访问该元素的邻接表
            for (int i = 0; i < adj[visitedId].size(); ++i) {
                int neighborId = adj[visitedId].get(i);
                if (!visited[neighborId]) {
                    /* 记录来时的路径，prev[neighborId] = visitedId, 用于在之后打印路径 */
                    prev[neighborId] = visitedId;
                    if (neighborId == endId) {
                        print(prev, startId, endId);
                        return;
                    }
                    visited[neighborId] = true;
                    queue.add(neighborId);
                }
            }
        }
    }

    /* 从路径追踪数组中打印处 从startId->endId的路径 */
    private void print(int[] prev, int startId, int endId) {
        if (prev[endId] != -1 && endId != startId) {
            print(prev, startId, prev[endId]);
        }
        System.out.print(endId + "->");
    }


    /* 开始深度优先算法dfs处理, 作用为当找到中止顶点end时，就不再递归的继续查找了 */
    private boolean found = false;

    public void dfs(int startId, int endId) {
        found = false;
        boolean[] visited = new boolean[vertexNum];
        int[] prev = new int[vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            prev[i] = -1;
        }
        recurDfs(startId, endId, visited, prev);
        print(prev, startId, endId);
    }

    /* 使用递归的方式遍历图中的路径，一条路走到底，穿不下去的时候回退到上一个入口 */
    private void recurDfs(int fromId, int toId, boolean[] visited, int[] prev) {
        if (found == true) return;
        visited[fromId] = true;
        if (fromId == toId) {
            found = true;
            return;
        }
        /* 使用了java中的方法栈，使用递归方式逐层穿透，直到找到匹配的节点id */
        for (int i = 0; i < adj[fromId].size(); ++i) {
            int q = adj[fromId].get(i);
            if (!visited[q]) {
                prev[q] = fromId;
                recurDfs(q, toId, visited, prev);
            }
        }
    }

    /*public static void main(String[] args) {
        GraphSearch graph = new GraphSearch(8);
        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(4, 5);
        graph.addEdge(4, 6);
        graph.addEdge(5, 7);
        graph.addEdge(6, 7);
        // 0->1->4->6 Disconnected from the target VM, address: 使用广度优先算法遍历从(0, 6)的相关路径 (广度优先为最短路径)
//        graph.bfs(0, 6);
        // 使用深度优先算法(shortestPath)寻找两点间的路径, 0->1->2->5->4->6 一条路径，很合理目前是在一条路径上
        graph.dfs(0, 6);
    }*/

}
