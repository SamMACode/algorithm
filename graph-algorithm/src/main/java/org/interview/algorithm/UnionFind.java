package org.interview.algorithm;

/**
 * @author Sam Ma
 * @date 2020/08/16
 * UnionFind类构建连通图 实现根据id找其连通分量
 */
public class UnionFind {

    /**
     * connect id
     */
    private int[] connectId;

    private int[] rootNumber;

    /**
     * number of components
     */
    private int count;

    /**
     * create an empty union find data structure with n isolated sets
     *
     * @param capacity
     */
    public UnionFind(int capacity) {
        count = capacity;
        connectId = new int[capacity];
        rootNumber = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            connectId[i] = i;
            rootNumber[i] = 1;
        }
    }

    /**
     * Return the id of component corresponding to object p
     *
     * @param node
     * @return
     */
    public int findComponentId(int node) {
        while (node != connectId[node]) {
            node = connectId[node];
        }
        return node;
    }

    /**
     * return size of components
     *
     * @return
     */
    public int count() {
        return count;
    }

    /**
     * whether the two nodes is connected
     *
     * @param node
     * @param anotherNode
     * @return
     */
    public boolean connected(int node, int anotherNode) {
        return findComponentId(node) == findComponentId(anotherNode);
    }

    /**
     * replace sets containing node and anotherNode with their union
     *
     * @param node
     * @param anotherNode
     */
    public void unionItem(int node, int anotherNode) {
        int componentId = findComponentId(node);
        int anotherComponentId = findComponentId(anotherNode);

        if (componentId == anotherComponentId) {
            return;
        }
        // maker smaller root point to large one
        if (rootNumber[node] < rootNumber[anotherNode]) {
            connectId[node] = connectId[anotherComponentId];
            rootNumber[anotherNode] += rootNumber[node];
        } else {
            connectId[anotherNode] = node;
            rootNumber[node] += rootNumber[anotherNode];
        }
        count--;
    }

}
