import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] storeArray;    
    private int N;
    
    public RandomizedQueue() {
        storeArray = (Item[])new Object[1];
        N = 0;
    }    
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public int size() {
        return N;
    }
    
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("Cannot add a null item!");
        }
        if (N + 1 > storeArray.length) doubleArraySize();  
        storeArray[N++] = item;
        
    }
    
    private void doubleArraySize(){
        Item[] newArray = (Item[])new Object[N * 2];
        for (int i = 0; i < N; i++) {
            newArray[i] = storeArray[i];
        }
        storeArray = newArray;
    }   
              
    
    public Item dequeue() {
        if (N == 0) {
            throw new java.util.NoSuchElementException("No element in queue!");
        }
        StdRandom.shuffle(storeArray, 0, N);
        Item temp = storeArray[--N];
        storeArray[N] = null;
        if (N * 4 < storeArray.length) reduceArraySize();
        return temp;           
        
    }   
        
    private void reduceArraySize() {
        Item[] newArray = (Item[])new Object[N * 2 + 1];
        for (int i = 0; i < N; i++) {
            newArray[i] = storeArray[i];
        }
        storeArray = newArray;
    }
    
    public Item sample() {
        if (N == 0) {
            throw new java.util.NoSuchElementException("No element in queue!");
        }
        int i = StdRandom.uniform(N);
        return storeArray[i];
    }
    
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int num = 0;
        private Item[] a;
        
        public RandomizedQueueIterator() {
            StdRandom.shuffle(storeArray, 0, N);
            a = (Item[])new Object[N];
            for (int i = 0; i < N; i++) {
                a[i] = storeArray[i];
            }
        }
        
        public boolean hasNext() {
            return num < N;
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException("Do not call remove in iterator!");
        }
        
        public Item next() {
            if (num == N) {
                throw new java.util.NoSuchElementException("No item to return!");
            }
            return a[num++];
        }
        
    }
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> exp = new RandomizedQueue<Integer>();
        exp.enqueue(1);
        exp.enqueue(2);
        exp.enqueue(3);
        exp.dequeue();
        exp.dequeue();
        exp.dequeue();
        exp.enqueue(4);
        exp.enqueue(5);
        exp.enqueue(6);
        System.out.println(exp.size());
        System.out.println(exp.dequeue());
        System.out.println(exp.size());
        Iterator<Integer> it = exp.iterator();
        Iterator<Integer> it2 = exp.iterator();
        while (it.hasNext()) System.out.print(it.next() + " ");
        System.out.println();
        while (it2.hasNext()) System.out.print(it2.next() + " ");
        System.out.println();
    
    }


}