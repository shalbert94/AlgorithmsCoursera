/******************************************************************************
 *  Compilation: 
 *  [optional lines]
 *  Execution: 
 *  [optional lines]
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] items;
    private int size;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        size = 0;
    }                 
    
    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0; 
    }               
    
    // return the number of items on the randomized queue
    public int size() {
        return size;    
    }                       
    
    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Null item enqueued");
        if (size == items.length)  resizeItems(items.length * 2);
        items[size] = item;
        size++;
    }  
    
    // remove and return a random item
    public Item dequeue() {
        validateGetItem();
        if (size == (items.length / 4)) resizeItems(items.length / 2);
        
        int lastItem = size - 1;
        int randomIndex;
        if (lastItem != 0) randomIndex = StdRandom.uniform(0, (size - 1));
        else randomIndex = lastItem; 
        
        Item removedItem = items[randomIndex];
        items[randomIndex] = items[lastItem];
        items[lastItem] = null;
        size--;
        return removedItem;
    }                 
    
    // return a random item (but do not remove it)
    public Item sample() {
        validateGetItem();
        int randomIndex = StdRandom.uniform(0, (size - 1));
        return items[randomIndex];
    }                   
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { 
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item> {
        private final Item[] itemsShuffled;
        private int i;
        
        public ArrayIterator() {
            itemsShuffled = Arrays.copyOfRange(items, 0, size);
            StdRandom.shuffle(itemsShuffled);
            i = 0;
        }
        
        public boolean hasNext() {
            return i < size;
        }
        
        public void remove() {
            throw new UnsupportedOperationException("Cannot call remove()");
        }
        
        public Item next() {
            if (i == size) throw new NoSuchElementException("No next element");
            Item item = itemsShuffled[i];
            i++;
            return item;
        }
    }
    
    private void resizeItems(int capacity) {
        assert capacity >= size;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            // System.out.println("copy.length: " + copy.length + ", items.length: " + items.length + ", i: " + i);
            copy[i] = items[i];
        }
        items = copy;
    }
    
    private void validateGetItem() {
        if (size == 0) throw new NoSuchElementException("Queue is empty");
    }
    
    public static void main(String[] args) {
        // empty
    }  // unit testing (optional)
}