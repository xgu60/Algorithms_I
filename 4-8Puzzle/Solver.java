import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private Node solution;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new java.lang.IllegalArgumentException("argument cannot be null object");
        
        MinPQ<Node> pq1 = new MinPQ<Node>();
        MinPQ<Node> pq2 = new MinPQ<Node>();
        
        Node root = new Node(initial);        
        Node root2 = new Node(initial.twin());        
        pq1.insert(root);
        pq2.insert(root2);
        
        while (true) {
            Node node = pq1.delMin();
            if (node.bd.isGoal()) {
                solution = node;
                break;
            }
            for (Board bd : node.bd.neighbors()) {
                if (node.previous != null && bd.equals(node.previous.bd)) continue;
                Node temp = new Node(bd);
                temp.move = node.move + 1;
                temp.previous = node;
                pq1.insert(temp);
                    
            }
            
            Node node2 = pq2.delMin();
            if (node2.bd.isGoal()) {                
                break;
            }
            for (Board bd : node2.bd.neighbors()) {
                if (node2.previous != null && bd.equals(node2.previous.bd)) continue;
                Node temp = new Node(bd);
                temp.move = node2.move + 1;
                temp.previous = node2;
                pq2.insert(temp);
                    
            }
                
        }
        
    }
    
    private class Node implements Comparable<Node> {
        Board bd;        
        //int hamming;
        int manhattan;
        int move;
        Node previous;
        
        public Node (Board board) {
            bd = board;
            //hamming = board.hamming();
            manhattan = board.manhattan();
            move = 0;
        }
        
        @Override
        public int compareTo(Node other) {
            if (this.move + this.manhattan > other.move + other.manhattan) return 1;
            else if (this.move + this.manhattan < other.move + other.manhattan) return -1;
            else return 0;
        }
        
        
    }
    // is the initial board solvable?
    public boolean isSolvable() {
        return solution != null;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solution == null) return -1;
        return solution.move;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solution == null) return null;
        
        Stack<Board> solutions = new Stack<Board>();
        Node node = solution;
        while (node.previous != null) {
            solutions.push(node.bd);
            node = node.previous;
        }
        solutions.push(node.bd);
        return solutions;
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
    }
}