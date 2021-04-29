package com.company;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<E> extends AbstractCollection<E> {
    private static final Object[] EMPTY_ARRAY = {};
    private Object[] array;
    private int size;

    public Stack() {
        array = new Object[16];
    }
    public Stack(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException();
        else if (initialCapacity == 0)
            array = EMPTY_ARRAY;
        else
            array = new Object[initialCapacity];
    }

    @SuppressWarnings("unchecked")
    private E elementAt(int i) {
        return (E) array[i];
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean add(E e) {
        push(e);
        return true;
    }
    public void push(E e) {
        int length = array.length;
        if (size >= length) {
            int newCapacity = (length + 1) + (length >> 1);
            array = Arrays.copyOf(array, newCapacity);
        }
        array[size++] = e;
    }

    public E pop() {
        if (size == 0)
            throw new NoSuchElementException();
        return elementAt(--size);
    }
    public E poll() {
        return (size == 0) ? null : elementAt(--size);
    }

    public E element() {
        if (size == 0)
            throw new NoSuchElementException();
        return elementAt(size - 1);
    }
    public E peek() {
        return size == 0 ? null : elementAt(size - 1);
    }

    private void delete(int index) {
        System.arraycopy(array, index + 1, array, index, --size - index);
    }

    private int indexOf(Object o) {
        int i = 0;
        if (o == null) {
            while (i < size) {
                if (null == array[i])
                    return i;
                i++;
            }
        } else {
            while (i < size) {
                if (o.equals(array[i]))
                    return i;
                i++;
            }
        }
        return -1;
    }

    public boolean contains(Object o) {
        return indexOf(o) > -1;
    }
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index > -1) {
            delete(index);
            return true;
        }
        return false;

    }

    public void clear() {
        Object[] es = array;
        int s = size;
        for (int i = 0; i < s; i++)
            es[i] = null;
        size = 0;
    }

    public Iterator<E> iterator() {
        return new Iterator<>() {
            int lastRet = -1;
            int i = 0;

            public boolean hasNext() {
                return i < size;
            }

            public E next() {
                if (hasNext())
                    return elementAt((lastRet = i++));
                throw new NoSuchElementException();
            }

            public void remove() {
                if (lastRet == -1) throw new IllegalStateException();
                delete(i = lastRet);
                lastRet = -1;
            }
        };
    }
    public Iterator<E> descending() {
        return new Iterator<>() {
            int lastRet = -1;
            int i = size - 1;

            public boolean hasNext() {
                return i > - 1;
            }

            public E next() {
                if (hasNext())
                    return elementAt((lastRet = i--));
                throw new NoSuchElementException();
            }

            public void remove() {
                if (lastRet == -1) throw new IllegalStateException();
                delete(i = lastRet);
                lastRet = -1;
            }
        };
    }

    public String toString() {
        if (isEmpty())
            return "[]";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (int i = 0, to = size - 1; ; i++) {
            stringBuilder.append(array[i]);
            if (i == to)
                return stringBuilder.append(']').toString();
            stringBuilder.append(',').append(' ');
        }
    }
}

