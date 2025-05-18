package dsa;

import java.util.ArrayList;
import java.util.List;

public class Stack<T> {
    private List<T> items;
    private int maxSize;

    public Stack(int maxSize) {
        this.items = new ArrayList<>();
        this.maxSize = maxSize;
    }

    public void push(T item) {
        if (items.size() >= maxSize) {
            items.remove(0); // Remove oldest item if stack is full
        }
        items.add(item);
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return items.remove(items.size() - 1);
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return items.get(items.size() - 1);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public List<T> getAllItems() {
        return new ArrayList<>(items);
    }

    public void clear() {
        items.clear();
    }
} 