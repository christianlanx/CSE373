package deques;

public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;
    // Feel free to add any additional fields you may need, though.

    public LinkedDeque() {
        size = 0;
        front = new Node<>(null);
        back = new Node<>(null);
        front.next = back;
        back.prev = front;
    }

    public void addFirst(T item) {
        Node<T> first = new Node<>(item, front, front.next);
        front.next.prev = first;
        front.next = first;
        size += 1;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void addLast(T item) {
        Node<T> last = new Node<>(item, back.prev, back);
        back.prev.next = last;
        back.prev = last;
        size += 1;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T retVal = front.next.value;
        front.next = front.next.next;
        front.next.prev = front;
        size -= 1;
        return retVal;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T retVal = back.prev.value;
        back.prev.prev.next = back;
        back.prev = back.prev.prev;
        size -= 1;
        return retVal;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        Node<T> node;
        if (index > size/2) {
            node = back.prev;
            for (int i = size-1; i > index; i--) {
                node = node.prev;
            }
        }
        else {
            node = front.next;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node.value;
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    public int size() {
        return size;
    }
}
