package org.interview.graph.minitree;

/**
 * @author dong
 * @date 2019-01-31
 * className: WeightEdge
 * Comparable weight edge
 */

public class WeightEdge implements Comparable<WeightEdge> {

    private final int from;

    private final int to;

    private final double weight;

    public WeightEdge(int from, int to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }

    public int getFrom() {
        return this.from;
    }

    public int another(int vertex) {
        if (vertex == from) { return to; }
        else if (vertex == to) { return from; }
        else { throw new UnsupportedOperationException(); }
    }

    @Override
    public int compareTo(WeightEdge another) {
        if (Double.compare(this.getWeight(), another.getWeight()) < 0) { return -1; }
        else if (this.getWeight() > another.getWeight()) { return 1; }
        else { return 0; }
    }

    @Override
    public String toString() {
        return "WeightEdge{ from=" + from + ", to=" + to + ", weight=" + weight + '}';
    }
}
