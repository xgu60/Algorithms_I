import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] bks;
    private int dim;
    
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {        
        dim = blocks.length;
        bks = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {                
                bks[i][j] = blocks[i][j];
            }
        }
        
        
    }

    // board dimension n                                       
    public int dimension() {
        return dim;
    }
    
    // number of blocks out of place
    public int hamming() {
        int num = dim * dim - 1;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {                
                if (bks[i][j] == i * dim + j + 1) num--;
            }
        }
        return num;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int num = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (bks[i][j] == 0) continue;
                num += Math.abs((bks[i][j] - 1)/ dim - i) + Math.abs((bks[i][j] - 1) % dim - j);                
            }
        }
        return num;
    }
    
    // is this board the goal board?
    public boolean isGoal() {        
        
        if(bks[dim - 1][dim - 1] != 0) return false;
        
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (i == dim - 1 && j == dim - 1) continue;
                if (bks[i][j] != i * dim + j + 1) return false;
            }
        }
        
        return true;
        
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int rd1;
        int rd2;
        Random rand = new Random();
        while (true) {
            rd1 = rand.nextInt(dim);
            rd2 = rand.nextInt(dim);            
            if (bks[rd1][rd2] != 0 && bks[rd1][(rd2 + 1) % dim] != 0) break;    
        }
        
        return newBoard(rd1, rd2, rd1, (rd2 + 1) % dim);
        
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
        if (!(y instanceof Board)) return false;
        
        Board that = (Board) y;
        if (this.dim != that.dim) return false;
        
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (this.bks[i][j] != that.bks[i][j]) return false;          
            }
        }
        
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<Board>();
        
        // find the empty position
        int row = 0;
        int col = 0;
        for (int i = 0; i < dim * dim; i++) {
            if (bks[i / dim][i % dim] == 0) {
                row = i / dim;
                col = i % dim;
                break;                    
            }
        }
        
        // add neighbors
        if (row + 1 < dim) neighbors.add(newBoard(row, col, row + 1, col));
        if (row - 1 >= 0) neighbors.add(newBoard(row, col, row - 1, col));
        if (col + 1 < dim) neighbors.add(newBoard(row, col, row, col + 1));
        if (col - 1 >= 0) neighbors.add(newBoard(row, col, row, col - 1));
        
        return neighbors;
        
    }
    
    private Board newBoard(int row, int col, int row2, int col2) {
        int[][] blocks = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                blocks[i][j] = bks[i][j];
            }
        }
        int temp = blocks[row][col];
        blocks[row][col] = blocks[row2][col2];
        blocks[row2][col2] = temp;
        
        return new Board(blocks);
    }
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(Integer.toString(dim) + "\n");                
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                string.append(Integer.toString(bks[i][j]) + " ");                                
            }
            string.append("\n");
        }
        
        return string.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        int[][] test = new int[3][3];
        for (int i = 0; i < 8; i++) {
            test[i / 3][i % 3] = i + 1;
        }
        
        Board bd = new Board(test);
        Board bd2 = bd.twin();
        System.out.print(bd2);
        System.out.print(bd2.hamming());
        System.out.print(bd2.manhattan());
    }
}