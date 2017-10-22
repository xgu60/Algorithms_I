import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private double[] thres;
    private double mean;
    private double stddev;
    
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials should not be less than 1");
        }
        
        this.thres = new double[trials];
        
        // for every trial
        for (int i = 0; i < trials; i++) {
            Percolation exp = new Percolation(n);
            while (!exp.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                exp.open(row, col);                
            }
            this.thres[i] = (exp.numberOfOpenSites() * 1.0) / (n * n); 
        }
        
        this.mean = StdStats.mean(this.thres);
        this.stddev = StdStats.stddev(this.thres);   
    }
    
    public double mean() {
        return this.mean;
    }
    
    public double stddev() {
        return this.stddev;     
    }
    
    public double confidenceLo() {        
        double trials = this.thres.length;
        return (this.mean - 1.96 * this.stddev / Math.sqrt(trials));
    }
    
    public double confidenceHi() {
        double trials = this.thres.length;
        return (this.mean + 1.96 * this.stddev / Math.sqrt(trials));
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats exp = new PercolationStats(n, trials);
        System.out.println("mean");
        System.out.println(exp.mean());
        System.out.println("stddev");
        System.out.println(exp.stddev());
        System.out.println("confidenceLo");
        System.out.println(exp.confidenceLo());
        System.out.println("confidenceHi");
        System.out.println(exp.confidenceHi());
    }
    
    
}