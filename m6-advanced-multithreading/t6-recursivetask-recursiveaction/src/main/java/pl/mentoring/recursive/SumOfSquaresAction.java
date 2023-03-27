package pl.mentoring.recursive;

import java.util.concurrent.RecursiveAction;

public class SumOfSquaresAction extends RecursiveAction {

    private final double[] array;

    private final int left, right;

    private double result;

    public SumOfSquaresAction(double[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
        this.result = 0;
    }

    @Override
    protected void compute() {
        if (left < right) {
            if (right - left < 1000) {
                double sum = 0;
                for (int i = left; i < right; i++) {
                    sum += array[i] * array[i];
                }
                result += sum;
            } else {
                int mid = (left + right) / 2;
                SumOfSquaresAction leftWorker = new SumOfSquaresAction(array, left, mid);
                SumOfSquaresAction rightWorker = new SumOfSquaresAction(array, mid, right);
                leftWorker.fork();
                rightWorker.compute();
                leftWorker.join();
                result = leftWorker.getResult() + rightWorker.getResult();
            }
        }
    }

    public double getResult() {
        return result;
    }
}
