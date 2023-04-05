package pl.mentoring.recursive;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciTaskTest {

    @Test
    void compute() {
        assertEquals(1134903170L, new ForkJoinPool().invoke(new FibonacciTask(45)));
    }

    @Test
    void compute_shouldReturnExpectedValue() {
        ForkJoinPool pool = new ForkJoinPool();
        assertEquals(1L, pool.invoke(new FibonacciTask(2)));
        assertEquals(8L, pool.invoke(new FibonacciTask(6)));
        assertEquals(6765L, pool.invoke(new FibonacciTask(20)));
    }
}