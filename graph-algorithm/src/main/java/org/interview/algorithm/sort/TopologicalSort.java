package org.interview.algorithm.sort;

import org.interview.algorithm.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * @author Sam Ma
 * @date 2020/10/07
 * 使用拓扑排序算法解决依赖问题（A->B, B->C, D->C 针对各个事件如何制定执行顺序）
 */
public class TopologicalSort {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopologicalSort.class);

    private Graph graph;

    public TopologicalSort(Graph graph) {
        this.graph = graph;
    }

    /**
     * 使用Kahn算法实现拓扑排序，每次找inDegree中入度为0的顶点先执行，之后将该顶点入度-1. 通过while循环执行整个过程，
     * 直到graph中所有的顶点都被处理完成
     */
    public void topoSortByKahn() {
        int vertexs = graph.getVertex();
        LinkedList<Integer>[] adjacent = graph.getAdj();

        // 声明临时数组用于统计每个顶点的入度, 遍历临接表依据vertexId统计入度
        int[] inDegree = new int[vertexs];
        for (int i = 0; i < vertexs; ++i) {
            for (int j = 0; j < adjacent[i].size(); ++j) {
                // vertexId为在关系边中 顶点i指向的另外一个结点id, i --> vertexId
                int vertexId = adjacent[i].get(j);
                inDegree[vertexId]++;
            }
        }

        // 查找inDegree数组中入度为0的顶点，入度为0表示其可直接执行 不依赖任何其它结点
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < vertexs; ++i) {
            if (inDegree[i] == 0) queue.add(i);
        }

        while (!queue.isEmpty()) {
            int vertexId = queue.remove();
            System.out.print("->" + vertexId);
            for (int j = 0; j < adjacent[vertexId].size(); ++j) {
                int k = adjacent[vertexId].get(j);
                inDegree[k]--;
                if (inDegree[k] == 0) {
                    queue.add(k);
                }
            }
        }
    }

    /**
     * 使用深度优先算法实现拓扑排序，核心思想为递归处理每个顶点: 先输出其可达的顶点，然后再输出它自身
     */
    public void topoSortByDFS() {
        int vertexs = graph.getVertex();
        LinkedList<Integer>[] adjacent = graph.getAdj();

        // 先构建逆临接表，边from->to表示: from依赖于to to限于from执行
        LinkedList<Integer> inverseAdj[] = new LinkedList[vertexs];
        for (int i = 0; i < vertexs; i++) {
            inverseAdj[i] = new LinkedList<>();
        }
        // 申请内容空间，通过临接表生成逆临接表 (根据adjacent[i].get(j)拿到toId, 然后将toId作为开始添加i)
        for (int i = 0; i < vertexs; ++i) {
            for (int j = 0; j < adjacent[i].size(); ++j) {
                int toId = adjacent[i].get(j);
                inverseAdj[toId].add(i);
            }
        }

        boolean[] visited = new boolean[vertexs];
        // 使用深度优先算法遍历图结构
        for (int i = 0; i < vertexs; ++i) {
            if (visited[i] == false) {
                visited[i] = true;
                dfs(i, inverseAdj, visited);
            }
        }
    }

    /**
     * 使用深度优先算法遍历graph图，不重复访问已访问过的结点
     *
     * @param vertex
     * @param inverseAdj
     * @param visited
     */
    private void dfs(int vertex, LinkedList<Integer> inverseAdj[], boolean[] visited) {
        for (int i = 0; i < inverseAdj[vertex].size(); ++i) {
            int toId = inverseAdj[vertex].get(i);
            if (visited[toId]) continue;
            visited[toId] = true;
            dfs(toId, inverseAdj, visited);
        }
        // 先将vertex这个顶点可达的所有顶点都打印出来后，再打印自身
        System.out.print("->" + vertex);
    }

    /*public static void main(String[] args) {
        Graph topoGraph = new Graph(5);
        // 1-->2, 1-->3, 2-->3, 2-->4, 假设graph中共4个顶点，有4条关系边
        topoGraph.addEdge(1, 2);
        topoGraph.addEdge(1, 3);
        topoGraph.addEdge(3, 0);
        topoGraph.addEdge(2, 3);
        topoGraph.addEdge(2, 4);
        TopologicalSorting topoSorting = new TopologicalSorting(topoGraph);
        // 1.使用Kahn算法打印出topo排序的结果
        topoSorting.topoSortByKahn();
        System.out.println();
        LOGGER.info("\nsorting element by deep first search, final result: ");
        // 2.使用Dfs深度优先算法打印出topo排序后的结果
        topoSorting.topoSortByDFS();
    }
*/
}
