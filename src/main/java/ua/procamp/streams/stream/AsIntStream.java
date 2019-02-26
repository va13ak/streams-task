package ua.procamp.streams.stream;

import ua.procamp.streams.function.*;

import java.util.ArrayList;
import java.util.Iterator;

public class AsIntStream implements IntStream {
    protected ArrayList<Integer> arrayList;

    private AsIntStream() {
        arrayList = new ArrayList<>();
    }
    
    public static IntStream of(int... values) {
        AsIntStream newStream = new AsIntStream();
        for (int value: values
             ) {
            newStream.arrayList.add(value);
        }
        return newStream;
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
        return reduce(Integer.MIN_VALUE, (left, right) -> left > right ? left : right);
    }

    @Override
    public Integer min() {
        if (count() == 0) throw new IllegalArgumentException("Stream is empty");
        return reduce(Integer.MAX_VALUE, (left, right) -> left < right ? left : right);
    }

    @Override
    public long count() {
        return arrayList.size();
    }

    @Override
    public Integer sum() {
        if (count() == 0) throw new IllegalArgumentException("Stream is empty");
        return reduce(0, (left, right) -> left + right);
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        AsIntStream newStream = new AsIntStream();
        for (int value: arrayList
                ) {
            if (predicate.test(value)) newStream.arrayList.add(value);
        }
        return newStream;
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int value: arrayList
                ) {
            action.accept(value);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        AsIntStream newStream = new AsIntStream();
        for (int value: arrayList
                ) {
            newStream.arrayList.add(mapper.apply(value));
        }
        return newStream;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        AsIntStream newStream = new AsIntStream();
        for (int value: arrayList
                ) {
            for (int intValue: func.applyAsIntStream(value).toArray()
                 ) {
                newStream.arrayList.add(intValue);
            }
        }
        return newStream;
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int result = identity;
        for (int value: arrayList
                ) {
            result = op.apply(result, value);
        }
        return result;
    }

    @Override
    public int[] toArray() {
        int[] intArray = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            intArray[i] = arrayList.get(i);
        }
        return intArray;
    }

}
