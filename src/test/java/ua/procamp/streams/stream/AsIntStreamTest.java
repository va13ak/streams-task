package ua.procamp.streams.stream;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AsIntStreamTest {
    @Test
    public void testToArray() throws Exception {
        System.out.println("testToArray");
        int[] testArr = {-1, 0, 1, 2, 3};
        int[] expResult = testArr;
        int[] result = AsIntStream.of(testArr).toArray();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testMap() throws Exception {
        System.out.println("testMap");
        int[] testArr = {1, 2, 3};
        int[] expResult = {1, 4, 9};
        int[] result = AsIntStream.of(testArr).map(x -> x * x).toArray();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testFlatMap() throws Exception {
        System.out.println("testFlatMap");
        int[] testArr = {1, 4, 9};
        int[] expResult = {0, 1, 2, 3, 4, 5, 8, 9, 10};
        int[] result = AsIntStream.of(testArr).flatMap(x -> AsIntStream.of(x - 1, x, x + 1)).toArray();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testReduce() throws Exception {
        System.out.println("testReduce");
        int[] testArr = {0, 1, 2, 3, 4, 5, 8, 9, 10};
        int expResult = 42;
        int result = AsIntStream.of(testArr).reduce(0, (a, b) -> a + b);
        assertEquals(expResult, result);
    }

    @Test
    public void testMin() throws Exception {
        System.out.println("testMin");
        int[] testArr = {-1, 0, 1, 2, 3};
        int expResult = -1;
        int result = AsIntStream.of(testArr).min();
        assertEquals(expResult, result);
    }

    @Test
    public void testMax() throws Exception {
        System.out.println("testMax");
        int[] testArr = {-1, 0, 1, 2, 3};
        int expResult = 3;
        int result = AsIntStream.of(testArr).max();
        assertEquals(expResult, result);
    }

    @Test
    public void testCount() throws Exception {
        System.out.println("testCount");
        int[] testArr = {-1, 0, 1, 2, 3};
        int expResult = 5;
        long result = AsIntStream.of(testArr).count();
        assertEquals(expResult, result);
    }

    @Test
    public void testSum() throws Exception {
        System.out.println("testSum");
        int[] testArr = {-1, 0, 1, 2, 3};
        int expResult = 5;
        long result = AsIntStream.of(testArr).count();
        assertEquals(expResult, result);
    }

    @Test
    public void testAverage() throws Exception {
        System.out.println("testAverage");
        int[] testArr = {-1, 0, 1, 2, 3};
        double expResult = 1.0;
        double result = AsIntStream.of(testArr).average();
        assertEquals(expResult, result, 0.01);
    }
}
