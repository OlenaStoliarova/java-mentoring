package pl.mentoring.factorial;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        final int NUM_EVAL_RUNS = 5;
        final int FACTORIAL_TEST = 15000;

        logger.info("Evaluating Sequential Implementation...");
        // warm up run
        BigInteger sequentialResult = SequentialFactorial.calculateFactorial(BigInteger.valueOf(FACTORIAL_TEST));

        PerformanceEvaluationRunner<BigInteger, BigInteger> sequentialFactorialEvaluator = new PerformanceEvaluationRunner<>();
        double sequentialTime = sequentialFactorialEvaluator.runAndReturnAverageTime(NUM_EVAL_RUNS,
            BigInteger.valueOf(FACTORIAL_TEST),
            SequentialFactorial::calculateFactorial);

        logger.info("Evaluating Parallel Implementation...");
        BigInteger parallelResult = ParallelFactorial.calculateFactorial(BigInteger.valueOf(FACTORIAL_TEST));

        PerformanceEvaluationRunner<BigInteger, BigInteger> parallelFactorialEvaluator = new PerformanceEvaluationRunner<>();
        double parallelTime = parallelFactorialEvaluator.runAndReturnAverageTime(NUM_EVAL_RUNS,
            BigInteger.valueOf(FACTORIAL_TEST),
            ParallelFactorial::calculateFactorial);

        // display sequential and parallel results for comparison
        if (!sequentialResult.equals(parallelResult))
            throw new AssertionError("ERROR: sequentialResult and parallelResult do not match!");
        logger.info("{}! = {}", FACTORIAL_TEST, sequentialResult);
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
}
