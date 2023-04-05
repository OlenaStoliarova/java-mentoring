package pl.mentoring.recursive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

class SumOfSquaresActionTest {

    private final double[] testArray = generateRandomArray(300_000_000);

    private double expectedSumResult, streamsSum, directLinearCalculationTime, streamApiTime;

    @BeforeEach
    void setup() {
        System.out.println("Evaluating Sequential Implementation...");
        long start = System.currentTimeMillis();
        expectedSumResult = 0;
        for (double el : testArray) {
            expectedSumResult += el * el;
        }
        directLinearCalculationTime = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        streamsSum = Arrays.stream(testArray)
            .map(el -> el * el)
            .sum();
        streamApiTime = System.currentTimeMillis() - start;
    }

    @Test
    void computeTest() {
        System.out.println("Evaluating Parallel Implementation...");
        long start = System.currentTimeMillis();
        SumOfSquaresAction subject = new SumOfSquaresAction(testArray, 0, testArray.length);
        new ForkJoinPool().invoke(subject);
        double recursiveActionCalculationTime = System.currentTimeMillis() - start;

        System.out.format("Direct Linear Calculation result: %f\n", expectedSumResult);
        System.out.format("Stream API Calculation result: %f\n", streamsSum);
        System.out.format("Parallel Calculation result: %f\n", subject.getResult());

        double threshold = 0.0001;
        if (Math.abs(expectedSumResult - streamsSum) > threshold) {
            System.out.println("directLinearCalculation result and streamsSum result aren't equal");
        }
        if (Math.abs(expectedSumResult - subject.getResult()) > threshold) {
            System.out.println("directLinearCalculation result and paraller result aren't equal");
        }

        System.out.format("Direct Linear Calculation Time: %.1f ms\n", directLinearCalculationTime);
        System.out.format("Stream API Calculation Time: %.1f ms\n", streamApiTime);
        System.out.format("Parallel Time: %.1f ms\n", recursiveActionCalculationTime);

        System.out.format("Speedup to linear: %.2f \n", directLinearCalculationTime / recursiveActionCalculationTime);
        System.out.format("Speedup to streams: %.2f \n", streamApiTime / recursiveActionCalculationTime);
    }


    private static double[] generateRandomArray(int length) {
        System.out.format("Generating random array double[%d]...\n", length);
        Random rand = new Random();
        double[] output = new double[length];
        for (int i = 0; i < length; i++)
            output[i] = rand.nextDouble();
        return output;
    }
}