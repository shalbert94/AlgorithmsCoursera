/******************************************************************************
 *  Compilation: 
 *  javac Permutation.java
 *  Execution: 
 *  java Permutation # < input.txt
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        
        RandomizedQueue<String> queue = new RandomizedQueue<>();
                
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            queue.enqueue(input);
        }
        
        for (int i = 0; i < k; i++) {
            System.out.println(queue.dequeue());
        }
        System.out.println();
    } 
}