package maps;

import java.util.Iterator;
import  java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 8;
    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    SimpleEntry<K, V>[] entries;

    // You may add extra fields or helper methods though!
    int size;

    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public ArrayMap(int initialCapacity) {
        this.entries = this.createArrayOfEntries(initialCapacity);
        this.size = 0;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override
    public V get(Object key) {
        for (int i = 0; i < this.size; i++) {
            if (Objects.equals(key, entries[i].getKey())) {
                return entries[i].getValue();
            }
        }
        return null;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public V put(K key, V value) {
        V retVal;
        for (int i = 0; i < this.size; i++) {
            if (Objects.equals(entries[i].getKey(), key)) {
                retVal = entries[i].getValue();
                entries[i].setValue(value);
                return retVal;
            }
        }
        size += 1;
        if (needsResize()) {
            resize();
        }
        entries[this.size - 1] = new SimpleEntry<>(key, value); // add a new entry
        return null;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public V remove(Object key) {
        for (int i = 0; i < this.size; i++) {
            if (key == entries[i].getKey() || Objects.equals(key, entries[i].getKey())) {
                V retVal = entries[i].getValue();
                entries[i] = entries[size - 1];
                entries[size - 1] = null;
                size -= 1;
                return retVal;
            }
        }
        return null;    // if the key was not found
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.size; i++) {
            entries[i] = null;
        }
        this.size = 0;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean containsKey(Object key) {
        for (int i = 0; i < this.size; i++) {
            if (Objects.equals(key, entries[i].getKey())) {
                return true;
            }
        }
        return false;   // if the key was not found
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public int size() {
        return this.size;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ArrayMapIterator<>(this.entries);
    }

    /*
     * HELPER METHODS!
     */
    private boolean needsResize() {
        return this.size == this.entries.length;
    }

    private void resize() {
        int newLength = this.size * 2;
        SimpleEntry<K, V>[] newEntries = this.createArrayOfEntries(newLength);
        System.arraycopy(entries, 0, newEntries, 0, entries.length);
        this.entries = newEntries;
    }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        // You may add more fields and constructor parameters
        int i;

        public ArrayMapIterator(SimpleEntry<K, V>[] entries) {
            this.entries = entries;
            i = 0;
        }

        @Override
        public boolean hasNext() {
            return (entries[i] != null);
            // throw new UnsupportedOperationException("Not implemented yet.");
        }

        @Override
        public Map.Entry<K, V> next() {
            if (hasNext()) {
                return entries[i++];
            } else {
                throw new NoSuchElementException();
            }
            // throw new UnsupportedOperationException("Not implemented yet.");
        }
    }
}
