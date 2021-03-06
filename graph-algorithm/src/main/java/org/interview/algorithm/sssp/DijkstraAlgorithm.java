package org.interview.algorithm.sssp;

import java.util.LinkedList;

/**
 * @author Sam Ma
 * @date 2020/10/07
 * 使用Dijkstra最短路径算法求两点之间的最短路径
 */
public class DijkstraAlgorithm {

    /**
     * 临接表存储每个vertex顶点的相邻边
     */
    private LinkedList<Edge> adjacent[];

    /**
     * 表示有向加权图中顶点的个数
     */
    private int vertexNum;

    public DijkstraAlgorithm(int vertexNum) {
        this.vertexNum = vertexNum;
        this.adjacent = new LinkedList[vertexNum];
        for (int i = 0; i < vertexNum; ++i) {
            this.adjacent[i] = new LinkedList<>();
        }
    }

    /**
     * 添加一条边，从fromId..toId以及边的权重weight
     *
     * @param fromId
     * @param toId
     * @param weight
     */
    public void addEdge(int fromId, int toId, int weight) {
        this.adjacent[fromId].add(new Edge(fromId, toId, weight));
    }

    /**
     * 找从fromId到toId的最短路径 (Dijkstra算法实现)
     *
     * @param fromId
     * @param toId
     */
    public void dijkstra(int fromId, int toId) {
        // 用于还原最短路径,processors[i]存储的是访问它的结点的id数值
        int[] processors = new int[this.vertexNum];
        Vertex[] vertexs = new Vertex[this.vertexNum];
        for (int i = 0; i < this.vertexNum; ++i) {
            vertexs[i] = new Vertex(i, Integer.MAX_VALUE);
        }

        // 构建小顶堆
        PriorityQueue queue = new PriorityQueue(this.vertexNum);
        // 标记是否进入过队列
        boolean[] inqueue = new boolean[this.vertexNum];
        vertexs[fromId].dist = 0;
        queue.add(vertexs[fromId]);
        inqueue[fromId] = true;

        while (!queue.isEmpty()) {
            // 取堆顶元素并且进行删除, 如果minVertex.id为toId 则表明最短路径已经产生了
            Vertex minVertex = queue.poll();
            if (minVertex.id == toId) break;

            for (int i = 0; i < adjacent[minVertex.id].size(); ++i) {
                // 取出一条minVertex相连的边, 根据minEdge获取最小权重边的另外一个顶点
                Edge edge = adjacent[minVertex.id].get(i);
                Vertex nextVertex = vertexs[edge.toId];

                // 更新nextVertex的distance距离
                if (minVertex.dist + edge.weight < nextVertex.dist) {
                    nextVertex.dist = minVertex.dist + edge.weight;
                    processors[nextVertex.id] = minVertex.id;
                    // 更新队列中的distance数值
                    if (inqueue[nextVertex.id] == true) {
                        queue.update(nextVertex);
                    } else {
                        queue.add(nextVertex);
                        inqueue[nextVertex.id] = true;
                    }
                }
            }
        }
        // 输出最短路径
        System.out.print(fromId);
        print(fromId, toId, processors);
    }

    /**
     * 将使用Dijkstra搜寻到的最短路径进行打印
     *
     * @param fromId
     * @param toId
     * @param predecessor
     */
    private void print(int fromId, int toId, int[] predecessor) {
        if (fromId == toId) return;
        print(fromId, predecessor[toId], predecessor);
        System.out.print("->" + toId);
    }

    /**
     * Edge为有向边，包含fromId、toId以及边的权重weight
     */
    private class Edge {
        public int fromId;
        public int toId;
        public int weight;

        public Edge(int fromId, int toId, int weight) {
            this.fromId = fromId;
            this.toId = toId;
            this.weight = weight;
        }
    }

    /**
     * Vertex顶点类为了Dijkstra算法实现用的，id为顶点的编号、dist为从起始点到这个顶点的距离
     */
    private class Vertex {
        public int id;
        public int dist;

        public Vertex(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }
    }

    /**
     * 因为java提供的优先级队列，没有暴露更新数据的接口，所以我们需要重新实现一个(根据vertex.dist构建最小顶堆)
     */
    public static class PriorityQueue {
        private Vertex[] nodes;
        /**
         * 堆中可存储的最大数据的个数
         */
        private int capacity;
        /**
         * 堆中已存储元素的数量
         */
        private int count;

        public PriorityQueue(int vertexs) {
            // 小顶堆，数组从下标1开始 比较好计算
            this.nodes = new Vertex[vertexs + 1];
            this.capacity = vertexs;
            this.count = 0;
        }

        /**
         * 返回堆顶的元素 (将最后一个元素放入堆顶，然后进行堆化)
         *
         * @return
         */
        public Vertex poll() {
            Vertex vertex = nodes[1];
            // 将最后一个元素添加到堆顶，自上而下堆化 (小顶堆最小的元素在堆顶)
            nodes[1] = nodes[count];
            --count;
            heapifyUpToDown(1);  //堆顶从上而下堆化
            return vertex;
        }

        public void add(Vertex vertex) {
            // 堆已满, 不再添加新的元素
            if (count == capacity) return;
            nodes[++count] = vertex;
            heapifyDownToUp(count); //从下往上堆化
        }

        /**
         * 更新节点的值，并且从下往上堆化，符合堆的定义. 时间复杂度为O(logN)
         *
         * @param vertex
         */
        public void update(Vertex vertex) {
            for (Vertex element : nodes) {
                if (null != element && element.id == vertex.id) {
                    element.dist = vertex.dist;
                }
            }
            heapifyDownToUp(count);  //从下往上堆化
        }

        public boolean isEmpty() {
            return this.count == 0 ? true : false;
        }

        /**
         * 自上而下进行堆化 (将较大的元素沉入到堆的底部)
         *
         * @param index
         */
        private void heapifyUpToDown(int index) {
            while (true) {
                int minPos = index;
                // 若当前结点的值大于其左子树结点(index * 2)，对其数值进行交换
                if (index * 2 < count && nodes[index].dist > nodes[index * 2].dist) minPos = index * 2;
                // 当前结点的数值大于其右子结点(index * 2 + 1), 将其与右子结点进行交换
                if (index * 2 + 1 <= count && nodes[minPos].dist > nodes[index * 2 + 1].dist) minPos = index * 2 + 1;
                if (minPos == index) break;
                /*
                 * 交换minPos与当前index的位置，并且将交换后的较小元素继续下沉
                 */
                swap(index, minPos);
                index = minPos;
            }
        }

        /**
         * 从下往上进行堆化 (小顶堆～最小的元素在堆顶)
         */
        private void heapifyDownToUp(int i) {
            while (i / 2 > 0 && nodes[i].dist < nodes[i / 2].dist) {
                swap(i, i / 2);
                i = i / 2;
            }
        }

        /**
         * 定义数据交换的函数
         *
         * @param i
         * @param maxPos
         */
        private void swap(int i, int maxPos) {
            Vertex temp = nodes[i];
            nodes[i] = nodes[maxPos];
            nodes[maxPos] = temp;
        }
    }

    /*public static void main(String[] args) {
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(6);
        // 向有向图中添加数据: (0-->1, 10), (1-->3, 2), (1-->2, 15), (3-->2, 1), (2-->5, 5), (3-->5, 12)
        // (0-->4, 15), (4-->5, 10)
        dijkstra.addEdge(0, 1, 10);
        dijkstra.addEdge(1, 3, 2);
        dijkstra.addEdge(1, 2, 15);
        dijkstra.addEdge(3, 2, 1);
        dijkstra.addEdge(2, 5, 5);
        dijkstra.addEdge(3, 5, 12);
        dijkstra.addEdge(0, 4, 15);
        dijkstra.addEdge(4, 5, 10);
        // 使用dijkstra算法查找从结点0-->结点5的最短路径为: 0->1->3->2->5, 路径长度为18
        dijkstra.dijkstra(0, 5);
    }*/

}
