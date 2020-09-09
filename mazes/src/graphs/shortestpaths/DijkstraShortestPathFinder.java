package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import graphs.shortestpaths.ShortestPath.Success;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {
        Map<V, E> edgeTo = new HashMap<>();
        Map<V, Double> distTo = new HashMap<>();

        ExtrinsicMinPQ<V> perimeter = createMinPQ();
        perimeter.add(start, 0);
        distTo.put(start, 0.0);

        while (!perimeter.isEmpty()) {
            V u = perimeter.removeMin();
            if (Objects.equals(end, u)) {
                break;
            }
            for (E edge : graph.outgoingEdgesFrom(u)) {
                V v = edge.to();
                double w = edge.weight();
                double oldDist = distTo.getOrDefault(v, Double.POSITIVE_INFINITY);
                double newDist = distTo.get(u) + w;
                if (newDist < oldDist) {
                    distTo.put(v, newDist);
                    edgeTo.put(v, edge);
                    if (perimeter.contains(v)) {
                        perimeter.changePriority(v, newDist);
                    } else {
                        perimeter.add(v, newDist);
                    }
                }
            }
        }
        return edgeTo;
    }

    @Override
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {
        if (Objects.equals(start, end)) {
            return new ShortestPath.SingleVertex<>(start);
        }
        if (!spt.containsKey(end)) {
            return new ShortestPath.Failure<>();
        }
        E edge = spt.get(end);
        LinkedList<E> edges = new LinkedList<>();
        V from = edge.from();
        while (!Objects.equals(from, start)) {
            edges.addFirst(edge);
            edge = spt.get(from);
            from = edge.from();
        }
        edges.addFirst(edge);
        return new Success<V, E>(edges);
    }

}
