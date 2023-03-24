package pl.mentoring.factorial;

import java.math.BigInteger;

public class SequentialFactorial {

    private BigInteger number;

    public SequentialFactorial(BigInteger number) {
        this.number = number;
    }

    public BigInteger calculateFactorial() {
        BigInteger factorial = BigInteger.ONE;
        while(number.compareTo(BigInteger.ZERO) > 0) {
            factorial = factorial.multiply(number);
            number = number.subtract(BigInteger.ONE);
        }
        return factorial;
    }

}
