import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>{
    private Node first;
    private Node last;
    private int N;
    
    private class Node{
        Item item;
        Node before;
        Node next;
    }
    
    public Deque() { 
       N = 0;
    }
    
    public boolean isEmpty(){
        return N == 0;
    }
    
    public int size() {
        return N;
    }
    
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null item at the beginning!");
        }
        N++;
        Node temp = first;
        first = new Node();
        first.item = item;
        first.next = temp;
        if (N == 1) last = first;
        else temp.before = first;
                 
    }
    
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null item at the end!");
        }
        N++;
        Node temp = last;
        last = new Node();
        last.item = item;
        last.before = temp;
        if (N == 1)  first = last; 
        else temp.next = last;      
    }
    
    public Item removeFirst() {
        Node temp;
        if (N == 0) {
            throw new java.util.NoSuchElementException("No element in queue!");
        } else {
            N--;
            temp = first;
            first = first.next;            
            if (N == 0) last = null;
            else first.before = null;
        }
        return temp.item;        
    }
    
    public Item removeLast() {
        Node temp;
        if (N == 0) {
            throw new java.util.NoSuchElementException("No element in queue!");
        } else {
            N--;
            temp = last;
            last = last.before;
            if (N == 0) first = null;
            else last.next = null;
        }
        return temp.item;    
    }
    
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException("Do not call remove in iterator!");
        }
        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException("No item to return!");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    
    }
    
    
    public static void main(String[] args) {
        Deque<Integer> sample = new Deque<Integer>();        
        sample.addFirst(1);
        sample.addFirst(2);
        sample.addFirst(3);
        sample.addLast(4);
        sample.addLast(5);
        sample.addLast(6);
        System.out.println(sample.removeLast());
        System.out.println(sample.removeFirst());
        System.out.println(sample.removeLast());
        
        System.out.println(sample.size());
        Iterator<Integer> it = sample.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        
    }
    
}