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