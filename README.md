# java-mentoring
Java Global Mentoring Program 2023


### Advanced Multithreading (m6-advanced-multithreading)

* Task 1 (Optional) - Factorial via FJP

Use FJP to calculate factorial. Compare with the sequential implementation. Use BigInteger to keep values.

* Task 2 - Multithreading Sorting via FJP

Implement Merge Sort or Quick Sort algorithm that sorts huge array of integers in parallel using Fork/Join framework.

* Task 3 - File Scanner via FJP

Create CLI application that scans a specified folder and provides detailed statistics:

File count.
Folder count.
Size (sum of all files size) (similar like Windows context menu Properties). Since the folder may contain huge number of files the scanning process should be executed in a separate thread displaying an informational message with some simple animation like progress bar in CLI (up to you, but I'd like to see that task is in progress).
Once task is done, the statistics should be displayed in the output immediately. Additionally, there should be ability to interrupt the process pressing some reserved key (for instance c). Of course, use Fork-Join Framework for implementation parallel scanning.

* Task 5 (prodcons module): Solve producer–consumer problem

Using:
1. Semaphore
2. BlockingQueue

* Task 6

_RecursiveTask_

Give example from RecursiveTask javadoc.
Write FibonacciTask that implements RecursiveTask.
Apply suggestion from javadoc to check minimum granularity size less or equal 10. And in that case use linear algorithm.
Using unit test check that your code works correctly:
assertEquals(1134903170L, new ForkJoinPool().invoke(new FibonacciTask(45)).longValue());

_RecursiveAction_

Sum of double squares

Give last example from RecursiveAction javadoc about calculation of sum of squares in double[] array.
Use double array of half-billion size 500_000_000 filled by random doubles.
Compare speed with direct linear calculation (you may use Stream API as well):
double sum = 0; for (double v : ARRAY) { sum += v * v; }