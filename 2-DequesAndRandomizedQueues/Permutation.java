import edu.princeton.cs.algs4.StdIn;
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        
        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();            
            rq.enqueue(string);
            //if (rq.size() > k) rq.dequeue();
        }
        
        for (int i = 0; i < k; i++) System.out.println(rq.dequeue());
    }
}

