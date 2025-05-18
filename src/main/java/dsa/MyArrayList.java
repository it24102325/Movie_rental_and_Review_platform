package dsa;

import models.Movie;

public class MyArrayList<Movie> {
    private Object[] elements;
    private int size = 0;
    private static final int INITIAL_CAPACITY = 10;

    public MyArrayList() {
        elements = new Object[INITIAL_CAPACITY];
    }

    public void add(Movie item) {
        if (size == elements.length) {
            resize();
        }
        elements[size++] = item;
    }

    public Movie get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (Movie) elements[index];
    }

    public void set(int index, Movie item) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        elements[index] = item;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;
    }

    public int size() {
        return size;
    }

    private void resize() {
        Object[] newElements = new Object[elements.length * 2];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }
    //Bubblesort
    public void sort() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                // compare movie titles using String.compareTo()
                models.Movie[] data = new models.Movie[0];
                if (data[j].getTitle().compareTo(data[j + 1].getTitle()) > 0) {
                    Movie temp = (Movie) data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = (models.Movie) temp;
                }
            }
        }
    }

}

