package linkedlist;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CustomLinkedList<T> implements Queue<T> {

    // Singly linked node
    private static final class Entry<E> {
        E v; Entry<E> next;
        Entry(E v){ this.v = v; }
    }

    private Entry<T> head;   // front
    private Entry<T> tail;   // back
    private int size = 0;

    public CustomLinkedList() {}

    // ---- helpers ----
    private void checkIndex(int i){
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException("index="+i+", size="+size);
    }

    // ---- custom methods required by assignment ----
    @Override
    public boolean add(T val){ return offer(val); }

    public T get(int index){
        checkIndex(index);
        Entry<T> cur = head;
        for (int i=0;i<index;i++) cur = cur.next;
        return cur.v;
    }

    public T remove(int index){
        checkIndex(index);
        if (index == 0) return remove();
        Entry<T> prev = head;
        for (int i=0;i<index-1;i++) prev = prev.next;
        Entry<T> t = prev.next;
        prev.next = t.next;
        if (t == tail) tail = prev;
        size--;
        return t.v;
    }

    // ---- Queue ----
    @Override
    public boolean offer(T val){
        Entry<T> e = new Entry<>(val);
        if (tail == null){ head = tail = e; }
        else { tail.next = e; tail = e; }
        size++;
        return true;
    }

    @Override
    public T peek(){ return head == null ? null : head.v; }

    @Override
    public T poll(){
        if (head == null) return null;
        T v = head.v;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return v;
    }

    @Override
    public T element(){
        T v = peek();
        if (v == null) throw new NoSuchElementException();
        return v;
    }

    @Override
    public T remove(){
        T v = poll();
        if (v == null) throw new NoSuchElementException();
        return v;
    }

    // ---- Collection ----
    @Override public int size(){ return size; }
    @Override public boolean isEmpty(){ return size == 0; }

    @Override
    public void clear(){ head = tail = null; size = 0; }

    @Override
    public boolean contains(Object o){
        for (Entry<T> c = head; c != null; c = c.next)
            if (Objects.equals(c.v, o)) return true;
        return false;
    }

    @Override
    public Iterator<T> iterator(){
        return new Iterator<T>() {
            Entry<T> cur = head, last = null, lastPrev = null;
            @Override public boolean hasNext(){ return cur != null; }
            @Override public T next(){
                if (cur == null) throw new NoSuchElementException();
                T v = cur.v; lastPrev = last; last = cur; cur = cur.next; return v;
            }
            @Override public void remove(){
                if (last == null) throw new IllegalStateException();
                if (last == head){ CustomLinkedList.this.remove(); }
                else {
                    lastPrev.next = cur;
                    if (last == tail) tail = lastPrev;
                    size--;
                }
                last = null;
            }
        };
    }

    @Override
    public Object[] toArray(){
        Object[] a = new Object[size];
        int i = 0;
        for (Entry<T> c = head; c != null; c = c.next) a[i++] = c.v;
        return a;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E[] toArray(E[] a){
        if (a.length < size)
            a = (E[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        int i = 0;
        for (Entry<T> c = head; c != null; c = c.next) a[i++] = (E)c.v;
        if (a.length > size) a[size] = null;
        return a;
    }

    @Override
    public boolean addAll(Collection<? extends T> c){
        boolean ch = false;
        for (T e: c){ offer(e); ch = true; }
        return ch;
    }

    @Override
    public boolean remove(Object obj){
        if (head == null) return false;
        if (Objects.equals(head.v, obj)){ remove(); return true; }
        Entry<T> p = head, c = head.next;
        while (c != null){
            if (Objects.equals(c.v, obj)){
                p.next = c.next;
                if (c == tail) tail = p;
                size--;
                return true;
            }
            p = c; c = c.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c){
        for (Object e: c) if (!contains(e)) return false;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c){
        boolean ch = false;
        for (Object e: c){
            boolean r;
            do { r = remove(e); ch |= r; } while (r);
        }
        return ch;
    }

    @Override
    public boolean retainAll(Collection<?> c){
        boolean ch = false;
        Entry<T> dummy = new Entry<>(null);
        dummy.next = head;
        Entry<T> p = dummy, cur = head;
        while (cur != null){
            if (!c.contains(cur.v)){
                p.next = cur.next;
                if (cur == tail) tail = p;
                size--; ch = true;
            } else {
                p = cur;
            }
            cur = cur.next;
        }
        head = dummy.next;
        if (head == null) tail = null;
        return ch;
    }

    // ---- simple benchmark ----
    public static void main(String[] args){
        final int COUNT = 1_000_00; // 100k (1e5) to keep it fast
        ThreadLocalRandom r = ThreadLocalRandom.current();

        long t0 = System.nanoTime();
        Queue<Integer> lib = new LinkedList<>();
        for (int i=0;i<COUNT;i++) lib.add(r.nextInt());
        long t1 = System.nanoTime();

        long t2 = System.nanoTime();
        Queue<Integer> cus = new CustomLinkedList<>();
        for (int i=0;i<COUNT;i++) cus.add(r.nextInt());
        long t3 = System.nanoTime();

        System.out.println("time for library LL: " + (t1 - t0)/1_000_000.0 + " ms");
        System.out.println("time for custom  LL: " + (t3 - t2)/1_000_000.0 + " ms");
    }
}
