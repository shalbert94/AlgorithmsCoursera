/******************************************************************************
 *  Compilation: 
 *  javac Deque.java
 *  Execution: 
 *  java Deque < input.txt
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    /* Beginning of deque */
    private Node<Item> first;
    /* End of deque */
    private Node<Item> last;
    /* Number of elements in deque */
    private int n;
    
    /* Helper linked list class */
    private static class Node<Item> {
        private Item item;
        /* Neighbouring item closer to end */
        private Node<Item> behind;
        /* Neighbouring item closer to end */
        private Node<Item> inFront;
    }
    
    /* Creates an empty deque */
    public Deque()  {
        first = null;
        last = null;
        n = 0;
    }                         
    
    /**
     * Checks if deque is empty
     * 
     * @return {@code true} if deque contains no Items, and {@code false} if it 
     * does
     */
    public boolean isEmpty() {
        return first == null;
    }
    
    
    /**
     * Returns the number of items in the deque
     * 
     * @return Number of items in the deque
     */
    public int size() {
        return n;   
    }
    
    /**
     * Add item to front of deque
     * 
     * @param item The item to add
     */
    public void addFirst(Item item) {
        validateAddItem(item);
        Node<Item> oldFirst = first;
        first  = new Node<Item>();
        first.item = item;
        first.behind = oldFirst;
        first.inFront = null;
        if (isEmpty()) first = last;
        else         oldFirst.inFront = first;
        n++;
    }
    
    /**
     * Add item to end of deque
     * 
     * @param item The item to add
     */
    public void addLast(Item item) {
        validateAddItem(item);
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.behind = null;
        last.inFront = oldLast;
        if (isEmpty()) first = last;
        else           oldLast.behind = last;
        n++; 
    }
    
    /**
     * Remove and return the item from the front
     * 
     * @return The item that was removed
     */
    public Item removeFirst() {
        validateRemoveItem();
        Item item = first.item;
        first = first.behind;
        first.inFront = null;
        n--;
        if (isEmpty()) last = null;
        return item;
    }
    /**
     * Remove item from end of deque and return it
     * 
     * @return Removed Item
     */
    public Item removeLast() {
        validateRemoveItem();
        Item item = last.item;
        last = last.inFront;
        last.behind = null;
        n--;
        if (isEmpty()) first = null;
        return item;
    }
    
    /**
     * Return an iterator over items in order from front to end
     * 
     * @return An Iterator which iterates in order from front to end
     */
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }  
    
    
    private class ListIterator<Item> implements Iterator<Item> {
        /* Node that iterator is currently on */
        private Node<Item> current;
        
        /**
         * Set current node as the first node
         */
        public ListIterator(Node<Item> first) {
            current = first;   
        }
        
        /**
         * Check if there is another Item to iterate over
         * 
         * @return {@code true} if {@code current} has another node behind it
         * and {@code false} when there are no more Items to iterate over
         */
        public boolean hasNext() { return current != null; }
        
        /**
         * Unsupported method 
         *
         * @throw UnsupportedOperationException if {@method remove} is called
         */
        public void remove() { 
            throw new UnsupportedOperationException("Cannot call remove()");
        }
        
        /**
         * Change {@code current} node to the one behind it
         * 
         * @return the new node {@code current} references
         */
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("There is no next item");
            Item item = current.item;
            current = current.behind;
            return item;
        }        
    }
    
    /**
     * Check if added item is valid
     * 
     * @param item Item being added to deque
     * @throw IllegalArgumentException if {@code item} is {@code null} 
     */
    private void validateAddItem(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot add null Item");  
    } 
    
    /**
     * Check if a node can be removed
     * 
     * @throw NoSuchElementException if deque is empty
     */
    private void validateRemoveItem() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");  
    }
          
    /**
     * Will not test using {@code main} method
     */
    public static void main(String[] args) {
        // empty
    }   // unit testing (optional)
}