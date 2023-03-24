package pl.mentoring.factorial;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        final int NUM_EVAL_RUNS = 5;
        final int FACTORIAL_TEST = 15000;

        System.out.println("Evaluating Sequential Implementation...");
        // warm up run
        SequentialFactorial sequentialFactorial = new SequentialFactorial(BigInteger.valueOf(FACTORIAL_TEST));
        BigInteger sequentialResult = sequentialFactorial.calculateFactorial();

        PerformanceEvaluationRunner<SequentialFactorial, BigInteger> sequentialFactorialEvaluator = new PerformanceEvaluationRunner<>();
        double sequentialTime = sequentialFactorialEvaluator.runAndReturnAverageTime(NUM_EVAL_RUNS,
            () -> new SequentialFactorial(BigInteger.valueOf(FACTORIAL_TEST)),
            SequentialFactorial::calculateFactorial);

        System.out.println("Evaluating Parallel Implementation...");
        ParallelFactorial parallelFactorial = new ParallelFactorial(BigInteger.valueOf(FACTORIAL_TEST));
        BigInteger parallelResult = parallelFactorial.calculateFactorial();

        PerformanceEvaluationRunner<ParallelFactorial, BigInteger> parallelFactorialEvaluator = new PerformanceEvaluationRunner<>();
        double parallelTime = parallelFactorialEvaluator.runAndReturnAverageTime(NUM_EVAL_RUNS,
            () -> new ParallelFactorial(BigInteger.valueOf(FACTORIAL_TEST)),
            ParallelFactorial::calculateFactorial);

        // display sequential and parallel results for comparison
        if (!sequentialResult.equals(parallelResult))
            throw new Error("ERROR: sequentialResult and parallelResult do not match!");
        System.out.format("%d! = %s\n", FACTORIAL_TEST, sequentialResult);
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
}
