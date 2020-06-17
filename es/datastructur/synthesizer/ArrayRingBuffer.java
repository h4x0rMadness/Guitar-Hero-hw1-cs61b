package es.datastructur.synthesizer;
import java.util.Iterator;



public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) throw new RuntimeException("Ring Buffer overflow");

        rb[last] = x;
        last = (last == capacity() - 1) ? 0 : last + 1;
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) throw new RuntimeException("Ring Buffer underflow");

        T removedItem = rb[first];
        first = (first == capacity() - 1) ? 0 : first + 1;
        fillCount--;

        return removedItem;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()) throw new RuntimeException("Ring Buffer underflow");
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator<T>();
    }
    private class ArrayRingBufferIterator<T> implements Iterator<T> {
        private int index;
        private int numOfItems;
        public ArrayRingBufferIterator() {
            index = first;
            numOfItems = 0;
        }

        @Override
        public boolean hasNext() {
            return numOfItems < fillCount;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new RuntimeException("there's no more elements to return");
            T nextItem = (T) rb[index];
            index = (index == capacity() - 1) ? 0 : index + 1;
            numOfItems++;
            return nextItem;
        }


    }
    @Override
    public boolean equals(Object o){
        // assume two array ring buffer is the same with same list of value
        // even though their capacities are different
        if (o == null) return false;
        if (this == o) return true;
        if (this.getClass() != o.getClass()) return false;

        ArrayRingBuffer<T> arb = (ArrayRingBuffer<T>) o;
        if (fillCount != arb.fillCount) return false;

        T[] itemArray = (T[]) new Object[arb.fillCount];
        int index = 0;
        for (T item : arb) {
            itemArray[index++] = item;
        }

        T[] curArray = (T[]) new Object[fillCount];
        index = 0;
        for (T item : this) {
            curArray[index++] = item;
        }

        for (int i = 0; i < curArray.length; i++) {
            if (!curArray[i].equals(itemArray[i])) return false;
        }


        return true;

    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
}
    // TODO: Remove all comments that say TODO when you're done.
