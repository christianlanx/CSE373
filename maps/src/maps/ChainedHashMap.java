package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {
    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 1.2;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 8;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 4;

    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    AbstractIterableMap<K, V>[] chains;

    // You're encouraged to add extra fields (and helper methods) though!
    int chainInitialCapacity;
    int chainCount;
    double resizingLoadFactorThreshold;
    int size;

    private int getHashCode(Object key) {
        if (key == null) {
            return 0;
        }
        int hashCode = key.hashCode() % chainCount;
        return (hashCode > 0) ? hashCode : -1 * hashCode;
    }

    private void resizeIfNeeded() {
        if (((double) this.size) / ((double) this.chainCount) >= this.resizingLoadFactorThreshold) {
            chainCount *= 2;
            AbstractIterableMap<K, V>[] newChains = createArrayOfChains(chainCount);
            Iterator<Map.Entry<K, V>> entryIterator = this.iterator();
            while (entryIterator.hasNext()) {
                Map.Entry<K, V> next = entryIterator.next();
                K key = next.getKey();
                V value = next.getValue();
                int hashCode = getHashCode(key);
                if (newChains[hashCode] == null) {
                    newChains[hashCode] = createChain(chainInitialCapacity);
                }
                newChains[hashCode].put(key, value);
            }
            chains = newChains;
        }
    }

    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        this.chains = createArrayOfChains(initialChainCount);
        this.chainCount = initialChainCount;
        this.resizingLoadFactorThreshold = resizingLoadFactorThreshold;
        this.chainInitialCapacity = chainInitialCapacity;
        this.size = 0;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     *
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     *
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    @Override
    public V get(Object key) {
        int hashCode = getHashCode(key);
        if (chains[hashCode] == null) {
            return null;
        }
        return chains[hashCode].get(key);
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public V put(K key, V value) {
        int hashCode = getHashCode(key);
        if (chains[hashCode] == null) {
            chains[hashCode] = createChain(chainInitialCapacity);
        }
        V retVal = chains[hashCode].put(key, value);
        if (retVal == null) {
            size++;
        }
        resizeIfNeeded();
        return retVal;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public V remove(Object key) {
        int hashCode = getHashCode(key);
        if (chains[hashCode] == null) {
            return null;
        }
        V retVal =  chains[hashCode].remove(key);
        if (retVal != null) {
            size--;
        }
        return retVal;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void clear() {
        for (int i = 0; i < chainCount; i++) {
            if (chains[i] != null) {
                chains[i].clear();
            }
        }
        size = 0;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean containsKey(Object key) {
        int hashCode = getHashCode(key);
        if (chains[hashCode] == null) {
            return false;
        }
        return chains[hashCode].containsKey(key);
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public int size() {
        return size;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains);
    }


    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private AbstractIterableMap<K, V>[] chains;
        // You may add more fields and constructor parameters
        int index;
        Iterator<Map.Entry<K, V>> chainIterator;

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {
            this.chains = chains;
            this.index = 0;
            this.chainIterator = null;
        }

        @Override
        public boolean hasNext() {
            while (index < chains.length) {
                if (chains[index] != null) {
                    if (chainIterator == null) {
                        chainIterator = chains[index].iterator();
                    }
                    if (chainIterator.hasNext()) {
                        return true;
                    }
                    chainIterator = null;
                }
                index++;
            }
            return false;
        }

        @Override
        public Map.Entry<K, V> next() {
            while (index < chains.length) {
                if (chains[index] != null) {
                    if (chainIterator == null) {
                        chainIterator = chains[index].iterator();
                    }
                    if (chainIterator.hasNext()) {
                        return chainIterator.next();
                    }
                    chainIterator = null;
                }
                index++;
            }
            throw new NoSuchElementException();
        }
    }
}
