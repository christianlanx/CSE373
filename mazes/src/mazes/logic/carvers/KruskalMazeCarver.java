package mazes.logic.carvers;

//import graphs.AdjacencyListUndirectedGraph;
import graphs.EdgeWithData;
//import graphs.minspantrees.KruskalMinimumSpanningTreeFinder;
import graphs.minspantrees.MinimumSpanningTree;
import graphs.minspantrees.MinimumSpanningTreeFinder;
import mazes.entities.Room;
import mazes.entities.Wall;
import mazes.logic.MazeGraph;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Carves out a maze based on Kruskal's algorithm.
 */
public class KruskalMazeCarver extends MazeCarver {
    MinimumSpanningTreeFinder<MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder;
    private final Random rand;

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random();
    }

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder,
                             long seed) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random(seed);
    }

    @Override
    protected Set<Wall> chooseWallsToRemove(Set<Wall> walls) {
        Set<EdgeWithData<Room, Wall>> edges = new HashSet<>();
        Set<Wall> toRemove = new HashSet<>();
        MinimumSpanningTree<Room, EdgeWithData<Room, Wall>> mst;
        for (Wall wall : walls) {
            EdgeWithData<Room, Wall> edge = new EdgeWithData<>(wall.getRoom1(), wall.getRoom2(),
                rand.nextDouble(), wall);
            edges.add(edge);
        }
        mst = this.minimumSpanningTreeFinder.findMinimumSpanningTree(new MazeGraph(edges));
        for (EdgeWithData<Room, Wall> edge : mst.edges()) {
            toRemove.add(edge.data());
        }
        return toRemove;
    }
}
