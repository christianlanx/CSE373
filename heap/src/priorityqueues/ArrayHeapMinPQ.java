package priorityqueues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 1;
    List<PriorityNode<T>> items;
    int size;
    Map<T, Integer> itemMap;

    public ArrayHeapMinPQ() {
        this.items = new ArrayList<>();
        this.items.add(0, null);
        this.itemMap = new HashMap<>();
        this.size = 0;
        }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.

    /**
     * swaps the elements of two indexes
     */
    private void swap(int a, int b) {
        PriorityNode<T> nodeA = items.get(a);
        PriorityNode<T> nodeB = items.get(b);
        items.set(a, items.get(b));
        items.set(b, nodeA);
        itemMap.put(nodeA.getItem(), b);
        itemMap.put(nodeB.getItem(), a);
    }

    /**
     * Compares and rearranges the elements from the top to bottom.
     * re-heapifying for decrease in priority, or deletion of an element.
     */
    private void percolateDown(int index) {
        int leftChildIndex = index * 2;
        if (leftChildIndex > size) {
            return;
        }
        double nodePriority = items.get(index).getPriority();
        double leftChildPriority = items.get(leftChildIndex).getPriority();
        if (leftChildIndex == size) {
            if (leftChildPriority < nodePriority) {
                swap(index, leftChildIndex);
            }
            return;
        }
        int rightChildIndex = leftChildIndex + 1;
        double rightChildPriority = items.get(rightChildIndex).getPriority();
        int minIndex = (leftChildPriority < rightChildPriority) ? leftChildIndex : rightChildIndex;
        double minPriority = items.get(minIndex).getPriority();
        if (minPriority < nodePriority) {
            swap(index, minIndex);
            percolateDown(minIndex);
        }
    }


    /**
     * Compares and rearranges the elements from the bottom to top.
     * re-heapifying for increase in priority, or addition of an element at the bottom of the heap.
     */
    private void percolateUp(int index) {
        if (index == 1) {
            return;
        }
        int parentIndex = index / 2;
        double nodePriority = items.get(index).getPriority();
        double parentPriority = items.get(parentIndex).getPriority();
        if (nodePriority < parentPriority) {
            swap(index, parentIndex);
            percolateUp(parentIndex);
        }
    }

    @Override
    public void add(T item, double priority) {
        if (item == null ||itemMap.containsKey(item)) {
            throw new IllegalArgumentException();
        }

        PriorityNode<T> node = new PriorityNode<>(item, priority);
        items.add(++size, node);
        itemMap.put(item, size);
        percolateUp(size);
    }

    @Override
    public boolean contains(T item) {
        return itemMap.containsKey(item);
    }

    @Override
    public T peekMin() {
        if (size == 0) {
            throw new NoSuchElementException("No such element");
        }

        return items.get(1).getItem();
    }

    @Override
    public T removeMin() {
        if (size == 0) {
            throw new NoSuchElementException("No such element");
        }
        T min = items.get(1).getItem();
        swap(1, size);
        items.remove(size--);
        itemMap.remove(min);
        percolateDown(1);
        return min;
    }

    @Override
    public void changePriority(T item, double priority) {
        Integer index = itemMap.get(item);
        if (index == null) {
            throw new NoSuchElementException("Element not found");
        }

        PriorityNode<T> node = items.get(index);
        node.setPriority(priority);
        percolateDown(index);
        percolateUp(index);
    }

    @Override
    public int size() {
        return size;
    }
}

// currentNode sometimes is null because currIndex is out of index.
    /*
        private void percolateUp() {
        if (size > 1) {
            PriorityNode<T> parentNode = items.get(currIndex/2);
            PriorityNode<T> currentNode = items.get(currIndex);

            while (currIndex / 2 >= 1 && parentNode.getPriority() > currentNode.getPriority()) {
                swap(currIndex, currIndex/2);
                currIndex = currIndex / 2;

                parentNode = items.get(currIndex / 2);
                currentNode = items.get(currIndex);
            }
        }
    }

    private void percolateDown() {
        if (currIndex * 2 <= size) {
            PriorityNode<T> currentNode = items.get(currIndex);
            int higherPriorityIndex = comparePriority(currIndex * 2, currIndex * 2 + 1);
            PriorityNode<T> childNode = items.get(higherPriorityIndex);

            while (currIndex * 2 <= size && childNode.getPriority() < currentNode.getPriority()) {
                swap(higherPriorityIndex, currIndex);
                currIndex = higherPriorityIndex;
                if (currIndex * 2 + 1 <= size) {
                    currentNode = items.get(currIndex);
                    higherPriorityIndex = comparePriority(currIndex * 2, currIndex * 2 + 1);
                    childNode = items.get(higherPriorityIndex);
                } else { break; }
            }
        }
    }

    /**
     * returns the index of the PriorityNode that has lower priority
     */
    /*
private int comparePriority(int a, int b) {
    if (a < b && b > size) {
        return a;
    }

    if (a > b && a > size) {
        return b;
    }

    PriorityNode<T> nodeA = items.get(a);
    PriorityNode<T> nodeB = items.get(b);
    if (nodeA.getPriority() >= nodeB.getPriority()) {
        return b;
    } else {
        return a;
    }
}
    */
