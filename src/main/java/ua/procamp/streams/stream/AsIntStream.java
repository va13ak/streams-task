package ua.procamp.streams.stream;

import ua.procamp.streams.function.*;

import java.util.ArrayList;
import java.util.Iterator;

public class AsIntStream implements IntStream {
    protected ArrayList<Integer> arrayList;
    protected Iterator<Integer> iterator;

    private AsIntStream() {
        arrayList = new ArrayList<>();
    }

    public static IntStream of(int... values) {
        AsIntStream newStream = new AsIntStream();
        for (int value: values
             ) {
            newStream.arrayList.add(value);
        }
        newStream.iterator = newStream.arrayList.iterator();
        return newStream;
    }

    private Iterator<Integer> getIterator() {
        if (iterator == null) {
            iterator = arrayList.iterator();
        }
        return iterator;
    }

    @Override
    public Double average() {
        Long cnt = count();
        if (cnt == 0) throw new IllegalArgumentException("Stream is empty!");
        return sum().doubleValue()/cnt.doubleValue();
    }

    @Override
    public Integer max() {
        if (count() == 0) throw new IllegalArgumentException("Stream is empty");
        System.out.println("max");
        return reduce(Integer.MIN_VALUE, (left, right) -> left > right ? left : right);
    }

    @Override
    public Integer min() {
        if (count() == 0) throw new IllegalArgumentException("Stream is empty");
        System.out.println("min");
        return reduce(Integer.MAX_VALUE, (left, right) -> left < right ? left : right);
    }

    @Override
    public long count() {
        return arrayList.size();
    }

    @Override
    public Integer sum() {
        if (count() == 0) throw new IllegalArgumentException("Stream is empty");
        System.out.println("sum");
        return reduce(0, (left, right) -> left + right);
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        final Iterator<Integer> currIt = getIterator();
        iterator = new Iterator<Integer>() {
            private int index = -1;
            private boolean hasNext = true;
            private ArrayList<Integer> localArrayList = new ArrayList<>();

            @Override
            public boolean hasNext() {
                if (!hasNext) return false;
                if (index < (localArrayList.size() - 1)) return true;
                while (currIt.hasNext()) {
                    System.out.println("filter: next");
                    int value = currIt.next();
                    if (predicate.test(value)) {
                        localArrayList.add(value);
                        return true;
                    }
                }
                hasNext = false;
                return false;
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    System.out.println("filter");
                    return localArrayList.get(++index);
                }
                return null;
            }
        };
        return this;
    }

    @Override
    public void forEach(IntConsumer action) {
        while (iterator.hasNext()) {
            System.out.println("foreach");
            action.accept(iterator.next());
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        final Iterator<Integer> currIt = iterator;
        iterator = new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return currIt.hasNext();
            }

            @Override
            public Integer next() {
                System.out.println("map");
                return mapper.apply(currIt.next());
            }
        };
        return this;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        final Iterator<Integer> currIt = iterator;
        iterator = new Iterator<Integer>() {
            private int index = -1;
            private boolean hasNext = true;
            private ArrayList<Integer> localArrayList = new ArrayList<>();


            @Override
            public boolean hasNext() {
                if (!hasNext) return false;
                if (index < (localArrayList.size() - 1)) return true;
                if (currIt.hasNext()) {
                    System.out.println("flatMap: toArray");
                    int[] array = func.applyAsIntStream(currIt.next()).toArray();
                    for (Integer value:array
                         ) {
                        localArrayList.add(value);
                    }
                    return true;
                }
                hasNext = false;
                return false;
            }

            @Override
            public Integer next() {
                System.out.println("flatMap");
                if (hasNext())
                    return localArrayList.get(++index);
                return null;
            }
        };
        return this;
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int result = identity;
        while (iterator.hasNext()) {
            System.out.println("reduce");
            result = op.apply(result, iterator.next());
        }
        return result;
    }

    @Override
    public int[] toArray() {
        ArrayList<Integer> resArrList = new ArrayList<>();
        while (iterator.hasNext()) {
            System.out.println("toArray (get value)");
            resArrList.add(iterator.next());
        }
        int[] resArray = new int[resArrList.size()];
        for (int i = 0; i < resArrList.size(); i++) {
            resArray[i] = resArrList.get(i);
        }
        return resArray;
    }

}
