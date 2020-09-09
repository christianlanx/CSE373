package disjointsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A quick-union-by-size data structure with path compression.
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    List<Integer> pointers;

    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */
    private final HashMap<T, Integer> ids;
    private int lastIndex;

    public UnionBySizeCompressingDisjointSets() {
        pointers = new ArrayList<>();
        ids = new HashMap<>();
        lastIndex = 0;
    }

    @Override
    public void makeSet(T item) {
        pointers.add(-1);
        ids.put(item, lastIndex++);
    }

    @Override
    public int findSet(T item) {
        if (!ids.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        int set = ids.get(item);
        List<Integer> indices = new LinkedList<>();
        while (pointers.get(set) >= 0) {
            indices.add(set);
            set = pointers.get(set);
        }
        for (int i : indices) {
            pointers.set(i, set);
        }
        return set;
    }

    @Override
    public boolean union(T item1, T item2) {
        if (!(ids.containsKey(item1) && ids.containsKey(item2))) {
            throw new IllegalArgumentException();
        }
        int rootA = findSet(item1);
        int rootB = findSet(item2);
        if (rootA == rootB) {
            return false;
        }
        int weightA = pointers.get(rootA) * -1;
        int weightB = pointers.get(rootB) * -1;
        int finalWeight = (weightA + weightB) * -1;
        if (weightA < weightB) {
            pointers.set(rootA, rootB);
            pointers.set(rootB, finalWeight);
        } else {
            pointers.set(rootB, rootA);
            pointers.set(rootA, finalWeight);
        }
        return true;
    }
}
