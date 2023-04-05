package pl.mentoring.factorial;

import java.math.BigInteger;

public class SequentialFactorial {

    private SequentialFactorial() {
        throw new IllegalStateException("Utility class");
    }

    public static BigInteger calculateFactorial(BigInteger number) {
        BigInteger factorial = BigInteger.ONE;
        while (number.compareTo(BigInteger.ZERO) > 0) {
            factorial = factorial.multiply(number);
            number = number.subtract(BigInteger.ONE);
        }
        return factorial;
    }
}
