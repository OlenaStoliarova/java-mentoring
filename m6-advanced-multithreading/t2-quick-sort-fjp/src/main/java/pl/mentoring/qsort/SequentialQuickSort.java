package pl.mentoring.qsort;

import java.util.Arrays;

public class SequentialQuickSort {

    private final int[] array;

    public SequentialQuickSort(int[] array) {
        this.array = array;
    }

    public int[] sort() {
        quickSort(0, array.length - 1);
        return array;
    }

    private void quickSort(int left, int right) {
        if (left < right) {
            if (right - left < 1000) {
                Arrays.sort(array, left, right + 1);
            } else {
                int q = partition(left, right);
                quickSort(left, q - 1);
                quickSort(q + 1, right);
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
