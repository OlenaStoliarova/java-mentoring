package pl.mentoring.qsort;

import pl.mentoring.factorial.PerformanceEvaluationRunner;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        final int NUM_EVAL_RUNS = 5;

        final int[] input = generateRandomArray(100_000);

        System.out.println("Evaluating Sequential Implementation...");
        SequentialQuickSort sequentialQuickSort = new SequentialQuickSort(Arrays.copyOf(input, input.length));
        int[] sequentialResult = sequentialQuickSort.sort();

        PerformanceEvaluationRunner<SequentialQuickSort, int[]> sequentialEvaluator = new PerformanceEvaluationRunner<>();
        double sequentialTime = sequentialEvaluator.runAndReturnAverageTime(NUM_EVAL_RUNS,
            () -> new SequentialQuickSort(Arrays.copyOf(input, input.length)),
            SequentialQuickSort::sort);

        System.out.println("Evaluating Parallel Implementation...");
        ParallelQuickSort parallelQuickSort = new ParallelQuickSort(Arrays.copyOf(input, input.length));
        int[] parallelResult = parallelQuickSort.sort();

        PerformanceEvaluationRunner<ParallelQuickSort, int[]> parallelEvaluator = new PerformanceEvaluationRunner<>();
        double parallelTime = parallelEvaluator.runAndReturnAverageTime(NUM_EVAL_RUNS,
            () -> new ParallelQuickSort(Arrays.copyOf(input, input.length)),
            ParallelQuickSort::sort);

        // display sequential and parallel results for comparison
        if (!Arrays.equals(sequentialResult, parallelResult))
            throw new Error("ERROR: sequentialResult and parallelResult do not match!");
        System.out.format("Average Sequential Time: %.1f ms\n", sequentialTime);
        System.out.format("Average Parallel Time: %.1f ms\n", parallelTime);

        if (sequentialTime > parallelTime) {
            System.out.format("Speedup: %.2f \n", sequentialTime / parallelTime);
            System.out.format("Efficiency: %.2f%%\n", 100 * (sequentialTime / parallelTime) / Runtime.getRuntime().availableProcessors());
        } else {
            System.out.format("Speedup: -%.2f \n", parallelTime / sequentialTime);
            System.out.format("Efficiency: -%.2f%%\n", 100 * (parallelTime / sequentialTime) / Runtime.getRuntime().availableProcessors());
        }
    }

    private static int[] generateRandomArray(int length) {
        System.out.format("Generating random array int[%d]...\n", length);
        Random rand = new Random();
        int[] output = new int[length];
        for (int i=0; i<length; i++)
            output[i] = rand.nextInt();
        return output;
    }
}
