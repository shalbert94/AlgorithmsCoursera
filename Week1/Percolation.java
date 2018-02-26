/******************************************************************************
 *  Compilation: 
 *  [optional lines]
 *  Execution: 
 *  [optional lines]
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /* Counts how many sites were opened */
    private int openSiteCount = 0;
    /* Grid's side length */
    private final int gridSideLength; 
    /* Open/closed reference for grid of all sites. {@code true} site is open
     * {@code false} side is closed */
    private boolean[] openSites; 
    /* Used for implementing wighted quick-union algorithm */
    private final WeightedQuickUnionUF uf;
    
    /**
     * Create n-by-n grid, with all sites blocked
     * @param n is the grid's side length
     */
    public Percolation(int n) {
        validateGridSize(n);
        
        gridSideLength = n;
        int gridSize = gridSideLength * gridSideLength;
        
        openSites = new boolean[gridSize];
        for (int i = 0; i < gridSize; i++) openSites[i] = false;
        
        uf = new WeightedQuickUnionUF(gridSize);
    }
    
    /**
     * Opens a site on the grid and connects it to any surrounding open sites
     * @param row Site's y-coordinate
     * @param col Site's x-coordinate
     */
    public void open(int row, int col)  {
        validateCoordinates(row, col);
        
        int gridIndex = convertToArrayIndex(row, col);
        int siteAbove = gridIndex - gridSideLength;
        int siteBelow = gridIndex + gridSideLength;
        int siteToLeft = gridIndex - 1;
        int siteToRight = gridIndex + 1;
        
        if (!openSites[gridIndex]) {
            openSites[gridIndex] = true;
            
            /* Connect surrounding open sites with union */
            if (validateIndex(siteAbove) && openSites[siteAbove] && row != 1) {
                uf.union(siteAbove, gridIndex);
            } 
            if (validateIndex(siteBelow) && openSites[siteBelow] && row != gridSideLength) {
                uf.union(siteBelow, gridIndex);
            }
            if (validateIndex(siteToLeft) && openSites[siteToLeft] && col != 1) { 
                uf.union(siteToLeft, gridIndex);
            }
            if (validateIndex(siteToRight) && openSites[siteToRight] && col != gridSideLength) { 
                uf.union(siteToRight, gridIndex);
            }
            openSiteCount++;
        }
    }
    
    /**
     * Checks if a site is open
     * @param row Site's y-coordinate
     * @param col Site's x-coordinate
     * @return {@code true} if site is open, and {@code false} if it's closed 
     */
    public boolean isOpen(int row, int col) {
        validateCoordinates(row, col);
        
        int gridIndex = convertToArrayIndex(row, col);
        return openSites[gridIndex];
    }
    
    /**
     * Checks if provided site is full by seeing if {@code uf.find()} for it 
     * matches {@code uf.find()} for one of the open site in the first row
     * @param row Site's y-coordinate
     * @param col Site's x-coordinate
     * @return {@code true} if site is full, and {@code false} if site is
     * either closed or disconnected from an open site in the first row
     */
    public boolean isFull(int row, int col) {
        validateCoordinates(row, col);
        int gridIndex = convertToArrayIndex(row, col);
        if (!openSites[gridIndex]) return false;
        
        for (int i = 0; i < gridSideLength; i++) {
            if (uf.find(gridIndex) == uf.find(i)) return true;
        }
        return false;
    }
    
    /**
     * @return the current number of open sites
     */
    public int numberOfOpenSites() {
        return openSiteCount;
    }  
    
    /**
     * Checks if the system percolates
     * @return {@code true} if system percolates, and {@code false} if system
     * doesn't percolate
     */
    public boolean percolates() {
        for (int topCol = 0; topCol < gridSideLength; topCol++) {
            for (int bottomCol = 0; bottomCol < gridSideLength; bottomCol++) {
                int topIndex = topCol;
                int bottomIndex = bottomCol + (gridSideLength * gridSideLength) - gridSideLength;
                if (uf.find(topIndex) == uf.find(bottomIndex)) return true;
            }
        }
        return false;
    } 
    
    /**
     * Converts a set of coordinates to an array index
     * @param row Site's y-coordinate
     * @param col Site's x-coordinate
     * @return The coordinates array index in {@code grid} array
     */
    private int convertToArrayIndex(int row, int col) {
        int arrayIndex = col - 1;
        if (row >= 2) {
            for (int i = 2; i <= row; i++) {
                arrayIndex += gridSideLength;
            }
        }
        return arrayIndex;
    }
    
    /**
     * Ensures provided grid sidelength is valid
     * @throws IllegalyArgmuentException if {@code n <= 0} 
     */
    private void validateGridSize(int n) {
        if (n <= 0) throw new IllegalArgumentException(n + "is less than or equal to 0");
    }   
    
    /**
     * Ensures provided coordinates exist on the grid
     * @param row Site's y-coordinate
     * @param col Site's x-coordinate
     * @throws OutOfBoundsException if {@code row} or {@code column} is equal to
     * or less than 0, or greater than {@code gridSideLength}
     */
    private void validateCoordinates(int row, int col) {
        if (row <= 0 || row > gridSideLength) {
            throw new IllegalArgumentException("Row " + row + " does not exist");
        } 
        else if (col <= 0 || col > gridSideLength) {
            throw new IllegalArgumentException("Column " + col + " does not exist");
        }
    }
    
    /**
     * Checks if arrayIndex lies within the array bounds
     * @param arrayIndex is the arrayIndex for a supposed site
     * @return if {@code true} then {@code arrayIndex} is within the array 
     * bounds, and if {@code false} then {@code arrayIndex} is not.
     */
    private boolean validateIndex(int arrayIndex) {
        if (arrayIndex < 0 || arrayIndex >= (gridSideLength * gridSideLength)) return false;
        return true;
    }
    
    // test client (optional)  
    public static void main(String[] args) {
        // Empty
    }
}
