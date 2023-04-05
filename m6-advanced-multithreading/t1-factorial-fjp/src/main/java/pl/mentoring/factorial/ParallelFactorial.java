package pl.mentoring.factorial;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFactorial {

    private ParallelFactorial() {
        throw new IllegalStateException("Utility class");
    }

    public static BigInteger calculateFactorial(BigInteger number) {
        int processorsNumber = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(processorsNumber);

        BigInteger result = pool.invoke(new RangeMultiplier(BigInteger.ONE, number));

        pool.shutdown();

        return result;
    }

    private static class RangeMultiplier extends RecursiveTask<BigInteger> {

        private final BigInteger threshold = BigInteger.valueOf(1000);
        private final BigInteger start;
        private final BigInteger end;

        public RangeMultiplier(BigInteger start, BigInteger end) {
            if (start.compareTo(end) > 0) {
                throw new IllegalArgumentException("start should be less than end");
            }
            this.start = start;
            this.end = end;
        }

        @Override
        protected BigInteger compute() {

            if (end.subtract(start).compareTo(threshold) <= 0) {
                BigInteger result = BigInteger.ONE;
                for (BigInteger i = start; i.compareTo(end) <= 0; i = i.add(BigInteger.ONE)) {
                    result = result.multiply(i);
                }
                return result;
            } else {
                BigInteger mid = start.add(end.subtract(start).divide(BigInteger.valueOf(2)));
                RangeMultiplier left = new RangeMultiplier(start, mid);
                RangeMultiplier right = new RangeMultiplier(mid.add(BigInteger.ONE), end);

                left.fork();
                return right.compute().multiply(left.join());
            }
        }
    }

}
