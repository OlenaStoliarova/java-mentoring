package pl.mentoring.factorial;

import java.util.function.Function;

public class PerformanceEvaluationRunner<K, R> {

    public double runAndReturnAverageTime(int runsNumber, K input, Function<K, R> methodToTest) {

        double runTime = 0;
        for (int i = 0; i < runsNumber; i++) {
            long start = System.currentTimeMillis();
            methodToTest.apply(input);
            runTime += System.currentTimeMillis() - start;
        }

        return runTime / runsNumber;
    }

}