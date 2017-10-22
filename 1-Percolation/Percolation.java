import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{
    private boolean[][] grid;
    private final int gridDim;
    private int openSites;
    private final WeightedQuickUnionUF wuf, wuf2;
    
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("IllegalArgumentException: n <= 0");
        }
        // initialize grid as a 2D array, o as close and 1 as open 
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        
        gridDim = n;
        openSites = 0;   
        
        // create UF data structure
        wuf = new WeightedQuickUnionUF(n * n + 2);
        wuf2 = new WeightedQuickUnionUF(n * n + 1);
        
    }
    
    public void open(int row, int col) {
        if (row < 1 || row > this.gridDim || col < 1 || col > this.gridDim) {
            throw new IllegalArgumentException("row or col number not in range");
        }
        
        if (!this.grid[row - 1][col - 1]) {
            this.grid[row - 1][col - 1] = true;
            this.openSites += 1;
            // update uf structure
            int top = this.gridDim * this.gridDim;
            int bottom = this.gridDim * this.gridDim + 1;
            int target = (row - 1) * this.gridDim + (col - 1);
            int upper = (row - 2) * this.gridDim + (col - 1);
            int down = row * this.gridDim + (col - 1);
            int left = (row - 1) * this.gridDim + (col - 2);
            int right = (row - 1) * this.gridDim + col;
        
            if (row == 1) {
                wuf.union(top, target);  
                wuf2.union(top, target);      
                if (isOpen(row + 1, col)) {
                    wuf.union(target, down);
                    wuf2.union(target, down);
                }
            } else if (row == this.gridDim) {
                wuf.union(bottom, target);
                if (isOpen(row - 1, col)) {
                    wuf.union(target, upper);
                    wuf2.union(target, upper);
                }
                if (col == 1) {
                    if(isOpen(row, col + 1)) wuf2.union(target, right);                     
                } else if (col == this.gridDim) {
                    if (isOpen(row, col - 1)) wuf2.union(target, left); 
                } else {
                    if (isOpen(row, col + 1)) wuf2.union(target, right); 
                    if (isOpen(row, col - 1)) wuf2.union(target, left);
                }
            } else {
                if (col == 1) {
                    if(isOpen(row, col + 1)) {
                        wuf.union(target, right);   
                        wuf2.union(target, right);   
                    }
                } else if (col == this.gridDim) {
                    if (isOpen(row, col - 1)) {
                        wuf.union(target, left); 
                        wuf2.union(target, left); 
                    }
                } else {
                    if (isOpen(row, col + 1)) {
                        wuf.union(target, right);
                        wuf2.union(target, right);
                    }
                    if (isOpen(row, col - 1)) {
                        wuf.union(target, left);
                        wuf2.union(target, left);
                    }
                }
                if (isOpen(row - 1, col)) {
                    wuf.union(target, upper);
                    wuf2.union(target, upper);
                }
                if (isOpen(row + 1, col)) {
                    wuf.union(target, down);
                    wuf2.union(target, down);
                }
            }
        }
        
    }
    
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > this.gridDim || col < 1 || col > this.gridDim) {
            throw new IllegalArgumentException("row or col number not in range");
        }
        return this.grid[row - 1][col - 1];
    }
    
    public boolean isFull(int row, int col) {
        if (row < 1 || row > this.gridDim || col < 1 || col > this.gridDim) {
            throw new IllegalArgumentException("row or col number not in range");
        }
        int top = this.gridDim * this.gridDim;
        int target = (row - 1) * this.gridDim + (col - 1);
        return wuf2.connected(top, target);        
    }
    
    public int numberOfOpenSites() {
        return this.openSites;
    }
    
    public boolean percolates() {        
        int top = this.gridDim * this.gridDim;
        int bottom = this.gridDim * this.gridDim + 1;
        return wuf.connected(top, bottom);      
    }
        
    
    public static void main(String[] args) {
        Percolation sample = new Percolation(4);
        System.out.println(sample.percolates());
        
        sample.open(1, 1);
        sample.open(2, 1);
        sample.open(3, 1);
        sample.open(4, 1);
        System.out.println(sample.percolates());
        
    }
    
    
    
    
    
}