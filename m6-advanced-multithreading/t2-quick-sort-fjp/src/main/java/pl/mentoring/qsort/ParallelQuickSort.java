package pl.mentoring.qsort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort {

    private final int[] array;

    public ParallelQuickSort(int[] array) {
        this.array = array;
    }

    public int[] sort() {
        int processorsCount = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(processorsCount);

        ParallelQuickSortWorker task = new ParallelQuickSortWorker(0, array.length - 1);
        pool.invoke(task);
        pool.shutdown();

        return array;
    }


    private class ParallelQuickSortWorker extends RecursiveAction {

        int left;
        int right;

        public ParallelQuickSortWorker(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if (left < right) {
                if (right - left < 1000) {
                    Arrays.sort(array, left, right + 1);
                } else {
                    int q = partition(left, right);
                    ParallelQuickSortWorker leftWorker = new ParallelQuickSortWorker(left, q - 1);
                    ParallelQuickSortWorker rightWorker = new ParallelQuickSortWorker(q + 1, right);
                    leftWorker.fork();
                    rightWorker.compute();
                    leftWorker.join();
                }
            }
        }

        private int partition(int left, int right) {

            int pivotal = array[(left + right) / 2];

            int i = left;
            int j = right;

            while (i <= j) {
                while (array[i] < pivotal) i++;
                while (array[j] > pivotal) j--;

                if (i >= j) {
                    break;
                }

                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }

            return j;
        }
    }

}
