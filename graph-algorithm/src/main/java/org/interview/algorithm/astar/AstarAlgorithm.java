package org.interview.algorithm.astar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * @author Sam Ma
 * @date 2020/10/08
 * AStar算法的实现，在图中寻找两点间的最短路径
 */
public class AstarAlgorithm {

    private static final Logger LOGGER = LoggerFactory.getLogger(AstarAlgorithm.class);

    /**
     * 临接表存储每个vertex顶点的相邻边
     */
    private LinkedList<Edge> adjacent[];

    private Vertex[] vertices;

    /**
     * 表示有向加权图中顶点的个数
     */
    private int vertexNum;

    public AstarAlgorithm(int vertexNum) {
        this.vertexNum = vertexNum;
        this.adjacent = new LinkedList[vertexNum];
        for (int i = 0; i < vertexNum; ++i) {
            this.adjacent[i] = new LinkedList<>();
        }
        this.vertices = new Vertex[this.vertexNum];
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
     * 将顶点Vertex添加到Graph中
     *
     * @param id
     * @param x
     * @param y
     */
    public void addVertex(int id, int x, int y) {
        vertices[id] = new Vertex(id, x, y);
    }

    /**
     * 通过Astar算法计算从start~end结点之间的距离
     *
     * @param start
     * @param end
     */
    public void astar(int start, int end) {
        // 定义predecessor数组用于还原路径
        int[] predecessor = new int[this.vertexNum];
        PriorityQueue queue = new PriorityQueue(this.vertexNum);
        /*
         * 标记是否进入过队列, 优先级队列是根据(dist+hManhattan)来对节点进行排序的
         */
        boolean[] inqueue = new boolean[this.vertexNum];
        vertices[start].dist = 0;
        vertices[start].f = 0;
        queue.add(vertices[start]);
        inqueue[start] = true;

        while (!queue.isEmpty()) {
            // 获取堆顶元素并将其从堆中进行删除, 在astar算法中优先级队列使用f函数排序
            Vertex minVertex = queue.poll();
            for (int i = 0; i < adjacent[minVertex.id].size(); ++i) {
                // 取出一条minVertex相连接的边, 依据minVertex取出nextVertex结点
                Edge edge = adjacent[minVertex.id].get(i);
                Vertex nextVertex = vertices[edge.toId];
                if (minVertex.dist + edge.weight < nextVertex.dist) {
                    nextVertex.dist = minVertex.dist + edge.weight;
                    // f数值计算的是nextVertex与目的结点间的曼哈顿距离, 通过在predecessor中记录下起始点的id
                    nextVertex.f = nextVertex.dist + hManhattan(nextVertex, vertices[end]);
                    predecessor[nextVertex.id] = minVertex.id;

                    if (inqueue[nextVertex.id] == true) {
                        queue.update(nextVertex);
                    } else {
                        queue.add(nextVertex);
                        inqueue[nextVertex.id] = true;
                    }
                }

                /*
                 * 当扫描nextVertex.id与终止结点id相同时，将queue中的数据进行清空(跳出while循环)
                 */
                if (nextVertex.id == end) {
                    queue.clear();
                    break;
                }
            }
        }
        // 输出最短路径
        System.out.print(start);
        print(start, end, predecessor);
    }

    /**
     * 将使用astar搜寻到的最短路径进行打印
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
     * Vertex表示顶点，计算两点之间的曼哈顿距离
     *
     * @param v1
     * @param v2
     * @return
     */
    private int hManhattan(Vertex v1, Vertex v2) {
        return Math.abs(v1.x - v2.x) + Math.abs(v1.y - v2.y);
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

    private class Vertex {
        private int id;
        private int dist;
        /**
         * f(i) = g(i) + h(i)
         */
        public int f;
        /**
         * 新增顶点在地图上的坐标 (x, y)
         */
        private int x, y;

        public Vertex(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.f = Integer.MAX_VALUE;
            this.dist = Integer.MAX_VALUE;
        }
    }

    /**
     * 因为java提供的优先级队列，没有暴露更新数据的接口，所以我们需要重新实现一个(根据vertex.dist构建最小顶堆)
     */
    public class PriorityQueue {
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
         * 从下往上进行堆化 (小顶堆～最小的元素在堆顶)
         */
        private void heapifyDownToUp(int i) {
            while (i / 2 > 0 && nodes[i].dist < nodes[i / 2].dist) {
                swap(i, i / 2);
                i = i / 2;
            }
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
                if (index * 2 < count && nodes[index].f > nodes[index * 2].f) minPos = index * 2;
                // 当前结点的数值大于其右子结点(index * 2 + 1), 将其与右子结点进行交换
                if (index * 2 + 1 <= count && nodes[minPos].f > nodes[index * 2 + 1].f) minPos = index * 2 + 1;
                if (minPos == index) break;
                /*
                 * 交换minPos与当前index的位置，并且将交换后的较小元素继续下沉
                 */
                swap(index, minPos);
                index = minPos;
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

        private void clear() {
            this.count = 0;
        }
    }

    /*public static void main(String[] args) {

    }*/

}
