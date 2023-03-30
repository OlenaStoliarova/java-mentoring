package pl.mentoring.qsort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws NoSuchAlgorithmException {
        final int NUM_EVAL_RUNS = 5;
        final int TEST_ARRAY_LENGTH = 100_000;

        final int[] input = generateRandomArray(TEST_ARRAY_LENGTH);

        logger.info("Evaluating Sequential Implementation...");
        SequentialQuickSort sequentialQuickSort = new SequentialQuickSort(Arrays.copyOf(input, input.length));
        int[] sequentialResult = sequentialQuickSort.sort();

        PerformanceEvaluationRunner<SequentialQuickSort, int[]> sequentialEvaluator = new PerformanceEvaluationRunner<>();
        double sequentialTime = sequentialEvaluator.runAndReturnAverageTime(NUM_EVAL_RUNS,
            () -> new SequentialQuickSort(Arrays.copyOf(input, input.length)),
            SequentialQuickSort::sort);

        logger.info("Evaluating Parallel Implementation...");
        ParallelQuickSort parallelQuickSort = new ParallelQuickSort(Arrays.copyOf(input, input.length));
        int[] parallelResult = parallelQuickSort.sort();

        PerformanceEvaluationRunner<ParallelQuickSort, int[]> parallelEvaluator = new PerformanceEvaluationRunner<>();
        double parallelTime = parallelEvaluator.runAndReturnAverageTime(NUM_EVAL_RUNS,
            () -> new ParallelQuickSort(Arrays.copyOf(input, input.length)),
            ParallelQuickSort::sort);

        // display sequential and parallel results for comparison
        if (!Arrays.equals(sequentialResult, parallelResult))
            throw new AssertionError("ERROR: sequentialResult and parallelResult do not match!");
        logger.info("Average Sequential Time: {} ms", sequentialTime);
        logger.info("Average Parallel Time: {} ms", parallelTime);

        String speedup;
        String efficiency;
        if (sequentialTime > parallelTime) {
            speedup = String.format("%.2f", sequentialTime / parallelTime);
            efficiency = String.format("%.2f", 100 * (sequentialTime / parallelTime) / Runtime.getRuntime().availableProcessors());
        } else {
            speedup = String.format("-%.2f", parallelTime / sequentialTime);
            efficiency = String.format("-%.2f", 100 * (parallelTime / sequentialTime) / Runtime.getRuntime().availableProcessors());
        }
        logger.info("Speedup: {}", speedup);
        logger.info("Efficiency: {} %", efficiency);
    }

    private static int[] generateRandomArray(int length) throws NoSuchAlgorithmException {
        logger.info("Generating random array int[{}]...", length);
        Random rand = SecureRandom.getInstanceStrong();
        int[] output = new int[length];
        for (int i = 0; i < length; i++)
            output[i] = rand.nextInt();
        return output;
    }
}
