package com.company;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack2<E> extends AbstractCollection<E> {
    private static final Object[] EMPTY_ARRAY = {};
    private Object[] array;
    private int indexLastElement = -1;

    public Stack2() {
        array = new Object[16];
    }
    public Stack2(int initialCapacity) {
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
        return indexLastElement + 1;
    }
    public boolean isEmpty() {
        return indexLastElement == -1;
    }

    public boolean add(E e) {
        push(e);
        return true;
    }
    public void push(E e) {
        final int length = array.length;
        final int insertIndex = indexLastElement + 1;
        if (insertIndex >= length) {
            //double if small else grow 50%
            int jump = length < 64 ? length + 2 : length >> 1;
            array = Arrays.copyOf(array, length + jump);
        }
        array[indexLastElement = insertIndex] = e;
    }

    public E pop() {
        if (isEmpty())
            throw new NoSuchElementException();
        return elementAt(indexLastElement--);
    }
    public E poll() {
        return isEmpty() ? null : elementAt(indexLastElement--);
    }

    public E element() {
        if (isEmpty())
            throw new NoSuchElementException();
        return elementAt(indexLastElement);
    }
    public E peek() {
        return isEmpty() ? null : elementAt(indexLastElement);
    }

    private void delete(int index) {
        System.arraycopy(array, index + 1, array, index, (indexLastElement--) - index);
    }

    private int indexOf(Object o) {
        int i = 0;
        int to = size();
        if (o == null) {
            while (i < to) {
                if (null == array[i])
                    return i;
                i++;
            }
        } else {
            while (i < to) {
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
        int s = size();
        for (int i = 0; i < s; i++)
            es[i] = null;
        indexLastElement = -1;
    }

    public Iterator<E> iterator() {
        return new Iterator<>() {
            int lastRet = -1;
            int i = 0;

            public boolean hasNext() {
                return i <= indexLastElement;
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
            int i = indexLastElement;

            public boolean hasNext() {
                return i > -1;
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
        for (int i = 0, s = size(); ; i++) {
            stringBuilder.append(array[i]);
            if (i == s)
                return stringBuilder.append(']').toString();
            stringBuilder.append(',').append(' ');
        }
    }
}