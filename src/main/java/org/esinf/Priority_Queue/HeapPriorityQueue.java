package org.esinf.Priority_Queue;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * An implementation of a priority queue using an array-based heap.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 *
 *
 */
public class HeapPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {
    /** primary collection of priority queue entries */
    protected ArrayList<Entry<K,V>> heap = new ArrayList<>();

    /** Creates an empty priority queue based on the natural ordering of its keys. */
    public HeapPriorityQueue() { super(); }

    /**
     * Creates an empty priority queue using the given comparator to order keys.
     * @param comp comparator defining the order of keys in the priority queue
     */
    public HeapPriorityQueue(Comparator<K> comp) { super(comp); }

    /**
     * Creates a priority queue initialized with the respective
     * key-value pairs.  The two arrays given will be paired
     * element-by-element. They are presumed to have the same
     * length. (If not, entries will be created only up to the length of
     * the shorter of the arrays)
     * @param keys an array of the initial keys for the priority queue
     * @param values an array of the initial values for the priority queue
     */
    public HeapPriorityQueue(K[] keys, V[] values) {
        super();
        for (int i = 0; i < Math.min(keys.length, values.length); i++) {
            heap.add(new PQEntry<>(keys[i], values[i]));
        }
        buildHeap();
    }

    // protected utilities
    protected int parent(int j) { return (j-1) / 2; }     // truncating division
    protected int left(int j) { return 2*j + 1; }
    protected int right(int j) { return 2*j + 2; }
    protected boolean hasLeft(int j) { return left(j) < heap.size(); }
    protected boolean hasRight(int j) { return right(j) < heap.size(); }

    /** Exchanges the entries at indices i and j of the array list. */
    protected void swap(int i, int j) {
        Entry<K,V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /** Moves the entry at index j higher, if necessary, to restore the heap property. */
    protected void percolateUp(int j) {
        while (j > 0) {  // continua até chegar à raiz
            int p = parent(j);
            if (compare(heap.get(j), heap.get(p)) >= 0) {
                break;  // heap-order property já está satisfeita
            }
            swap(j, p);
            j = p;  // move para a posição do pai
        }
    }

    /** Moves the entry at index j lower, if necessary, to restore the heap property. */
    protected void percolateDown(int j) {
        while (hasLeft(j)) {  // continua enquanto houver pelo menos um filho esquerdo
            int leftIndex = left(j);
            int smallChildIndex = leftIndex;  // assume que o filho esquerdo é o menor

            if (hasRight(j)) {
                int rightIndex = right(j);
                if (compare(heap.get(leftIndex), heap.get(rightIndex)) > 0) {
                    smallChildIndex = rightIndex;  // filho direito é menor
                }
            }

            if (compare(heap.get(smallChildIndex), heap.get(j)) >= 0) {
                break;  // heap-order property já está satisfeita
            }

            swap(j, smallChildIndex);
            j = smallChildIndex;  // move para a posição do menor filho
        }
    }

    /** Performs a batch bottom-up construction of the heap in O(n) time. */
    protected void buildHeap() {
        // Começa do último nó interno (parent do último elemento) e vai até à raiz
        int startIdx = parent(size() - 1);
        for (int j = startIdx; j >= 0; j--) {
            percolateDown(j);
        }
    }

    // public methods

    /**
     * Returns the number of items in the priority queue.
     * @return number of items
     */
    @Override
    public int size() { return heap.size(); }

    /**
     * Returns (but does not remove) an entry with minimal key.
     * @return entry having a minimal key (or null if empty)
     */
    @Override
    public Entry<K,V> min() {
        if (heap.isEmpty()) return null;
        return heap.get(0);  // a raiz contém o elemento com menor chave
    }

    /**
     * Inserts a key-value pair and return the entry created.
     * @param key     the key of the new entry
     * @param value   the associated value of the new entry
     * @return the entry storing the new key-value pair
     * @throws IllegalArgumentException if the key is unacceptable for this queue
     */
    @Override
    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);  // valida a chave
        Entry<K,V> newest = new PQEntry<>(key, value);
        heap.add(newest);  // adiciona no final (última posição)
        percolateUp(heap.size() - 1);  // restaura a heap-order property
        return newest;
    }

    /**
     * Removes and returns an entry with minimal key.
     * @return the removed entry (or null if empty)
     */
    @Override
    public Entry<K,V> removeMin() {
        if (heap.isEmpty()) return null;

        Entry<K,V> answer = heap.get(0);  // guarda o elemento mínimo (raiz)
        swap(0, heap.size() - 1);  // troca raiz com o último elemento
        heap.remove(heap.size() - 1);  // remove o último elemento (que era a raiz)

        if (!heap.isEmpty()) {
            percolateDown(0);  // restaura a heap-order property
        }

        return answer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < heap.size(); i++) {
            if (i > 0) sb.append(", ");
            Entry<K,V> entry = heap.get(i);
            sb.append("(").append(entry.getKey()).append(":").append(entry.getValue()).append(")");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public HeapPriorityQueue<K,V> clone() {
        HeapPriorityQueue<K,V> cloned = new HeapPriorityQueue<>();
        for (Entry<K,V> entry : heap) {
            cloned.heap.add(new PQEntry<>(entry.getKey(), entry.getValue()));
        }
        return cloned;
    }
}