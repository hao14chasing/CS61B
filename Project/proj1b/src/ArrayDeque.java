import java.util.ArrayList;
import java.util.List;


public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    public static final int USAG_MIN = 16;
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 0;
    }

    public void resize(int newlen) {
        T[] newarr = (T[]) new Object[newlen];
        /*for (int i = 0; i <= nextLast; i++) {
            newarr[i] = items[i];
        }
        int count = 0;
        for (int i = nextFirst; i < items.length; i++) {
            newarr[newlen - (items.length - nextFirst) + count] = items[i];
            count++;
        }
        nextFirst = newlen - (items.length - nextFirst);*/
        int sizeOfFirstCopy = items.length - nextFirst;
        System.arraycopy(items, nextFirst, newarr, 0, sizeOfFirstCopy);
        System.arraycopy(items, 0, newarr, sizeOfFirstCopy, size - sizeOfFirstCopy);
        items = newarr;
        nextFirst = 0;
        nextLast = size - 1;
        items = newarr;
    }

    public void resizedown(int newlen) {
        T[] newarr = (T[]) new Object[newlen];
        if (nextLast < nextFirst) {
            int sizeOfFirstCopy = items.length - nextFirst;
            System.arraycopy(items, nextFirst, newarr, 0, sizeOfFirstCopy);
            System.arraycopy(items, 0, newarr, sizeOfFirstCopy,
                    size - sizeOfFirstCopy);

        } else {
            System.arraycopy(items, nextFirst, newarr, 0, size);
        }
        items = newarr;
        nextFirst = 0;
        nextLast = size - 1;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (size == 0) {
            nextFirst = 0;
            nextLast = 0;
            items[0] = x;
            size++;
            return;
        }
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst--;
        }
        items[nextFirst] = x;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (size == 0) {
            nextFirst = 0;
            nextLast = 0;
            items[0] = x;
            size++;
            return;
        }
        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast++;
        }

        items[nextLast] = x;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        /*if (nextLast != 0 && nextFirst != 0 && nextLast != items.length - 1) {
            for (int i = nextFirst; i < items.length; i++) {
                returnList.add(items[i]);
            }
            for (int i = 0; i <= nextLast; i++) {
                returnList.add(items[i]);
            }
        }
        if (nextLast == items.length - 1 && nextFirst != 0) {
            for (int i = nextFirst; i < items.length; i++) {
                returnList.add(items[i]);
            }
        }
        if (nextLast == 0 && nextFirst != 0) {
            for (int i = nextFirst; i < items.length; i++) {
                returnList.add(items[i]);
            }
            returnList.add(items[0]);
        }
        if (nextLast != 0 && nextFirst == 0) {
            for (int i = 0; i <= nextLast; i++) {
                returnList.add(items[i]);
            }
        }
        if (nextLast == 0 && nextFirst == 0 && size != 0) {
            returnList.add(items[0]);
        }*/

        //new
        /*int currentFirst = (nextFirst) % items.length;

        if ((currentFirst + size) <= items.length) {
            for (int i = currentFirst; i < currentFirst + size; i++) {
                returnList.add(items[i]);
            }
        } else {
            int initSize = items.length - currentFirst;
            int restSize = size - initSize;

            for (int i = currentFirst; i < currentFirst + initSize; i++) {
                returnList.add(items[i]);
            }
            for (int i = 0; i < restSize; i++) {
                returnList.add(items[i]);
            }
        }*/
        for (int i = 0; i < size; i++) {
            returnList.add(get(i));
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        /*if (nextFirst == items.length - 1) {
            T[] newarr = (T[]) new Object[items.length];
            T returnvalue = items[0];
            items[0] = null;
            for (int i = 0; i < nextLast; i++) {
                newarr[i] = items[i + 1];
            }
            size--;
            nextLast--;
            items = newarr;
            if (size < items.length / 4 && items.length > 16) {
                resize(items.length / 2);
            }
            return returnvalue;
        }*/
        T returnvalue = items[nextFirst];
        items[nextFirst] = null;
        size--;
        if (nextFirst == items.length - 1) {
            nextFirst = 0;
        } else {
            nextFirst++;
        }
        if (size == 0) {
            nextFirst = 0;
            nextLast = 0;
        }
        if (size < items.length / 4 && items.length > USAG_MIN) {
            resizedown(items.length / 2);
        }
        return returnvalue;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        /*if (nextLast == 0) {
            T[] newarr = (T[]) new Object[items.length];
            T returnvalue = items[items.length - 1];
            items[items.length - 1] = null;
            for (int i = nextFirst + 1; i < items.length - 1; i++) {
                newarr[i + 1] = items[i];
            }
            items = newarr;
            size--;
            nextFirst++;
            if (size < items.length / 4 && items.length > 16) {
                resize(items.length / 2);
            }
            return returnvalue;
        }*/
        T returnvalue = items[nextLast];
        items[nextLast] = null;
        size--;
        if (nextLast == 0) {
            nextLast = items.length - 1;
        } else {
            nextLast--;
        }
        if (size == 0) {
            nextFirst = 0;
            nextLast = 0;
        }
        if (size < items.length / 4 && items.length > USAG_MIN) {
            resizedown(items.length / 2);
        }
        return returnvalue;
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            return null;
        }
        if (index >= size) {
            return null;
        }
        /*if (nextFirst != items.length - 1) {
            if (nextFirst + 1 + index > items.length - 1) {
                return items[index - (items.length - nextFirst - 1)];
            } else {
                return items[nextFirst + 1 + index];
            }
        }
        return items[index];*/
        return items[(nextFirst + index) % items.length];
    }
    public static void main(String[] args) {
        Deque<Integer> ad = new ArrayDeque<>();
        ad.addLast(0); //[0]
        ad.addLast(1);  //[0,1]
        ad.addLast(2);  //[0,1,2]
        ad.addLast(3);  //[0,1,2,3]
        ad.addFirst(4); //[0,1,2,3,4]
        ad.addFirst(5); //[0,1,2,3,5,4]
        ad.addFirst(6); //[0,1,2,3,6,5,4]
        ad.addFirst(7); //[0,1,2,3,7,6,5,4]
        ad.addLast(8); // [0,1,2,3,7,6,5,4,8]
        ad.addFirst(9); // [0,1,2,3,7,6,5,4,8,9]
    }
}
