package pl.mentoring.qsort;

import java.util.function.Function;
import java.util.function.Supplier;

public class PerformanceEvaluationRunner<K, R> {

    public double runAndReturnAverageTime(int runsNumber, Supplier<K> classToTest, Function<K, R> methodToTest) {

        double runTime = 0;
        for (int i = 0; i < runsNumber; i++) {
            K testedInstance = classToTest.get();
            long start = System.currentTimeMillis();
            methodToTest.apply(testedInstance);
            runTime += System.currentTimeMillis() - start;
        }

        return runTime / runsNumber;
    }

}
