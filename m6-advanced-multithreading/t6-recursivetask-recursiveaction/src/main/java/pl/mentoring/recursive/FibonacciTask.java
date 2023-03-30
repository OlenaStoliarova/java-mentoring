package pl.mentoring.recursive;

import java.util.concurrent.RecursiveTask;

public class FibonacciTask extends RecursiveTask<Long> {

    private final int n;

    public FibonacciTask(int n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n <= 10) {
            if (n == 0) return 0L;

            long fib1 = 1L;
            long fib2 = 1L;

            for (int j = 0; j < n - 2; j++) {
                long fibSum = fib1 + fib2;
                fib1 = fib2;
                fib2 = fibSum;
            }

            return fib2;
        } else {
            FibonacciTask f1 = new FibonacciTask(n - 1);
            f1.fork();
            FibonacciTask f2 = new FibonacciTask(n - 2);
            return f2.compute() + f1.join();
        }
    }
}

