/******************************************************************************
 *  Compilation: 
 *  [optional lines]
 *  Execution: 
 *  [optional lines]
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials;
    private final double[] trialResults;
    /**
     * Perform trials independent experiments on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0) throw new IllegalArgumentException(n + "is an illegal argument");
        if (trials <= 0) throw new IllegalArgumentException(trials + "is an illegal argument");
        this.trials = trials;
        trialResults = new double[trials];
        for (int i = 0; i < trials; i++) trialResults[i] = StdRandom.uniform();
    }
    
    /**
     * Sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(trialResults);
    } 
    
    /**
     * Sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(trialResults);
    } 
    
    /**
     * Low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(trials -1));
    }    
    
    /**
     * High endpoint of 95% confidence interval
     */
    public double confidenceHi()  {
        return mean() + ((1.96 * stddev()) / Math.sqrt(trials -1));
    }
    
    /**
     * Test client (described below)
     */
    public static void main(String[] args) {
        
        if (args.length > 0) {
            int n = Integer.parseInt(args[0]);
            int trials = Integer.parseInt(args[1]);
            System.out.println("n: " + n + ", trials: " + trials);
            PercolationStats percolationStats = new PercolationStats(n, trials);
        }
    }       
}