# java-mentoring
Java Global Mentoring Program 2023


### Advanced Multithreading (m6-advanced-multithreading)

* __Task 1__ (Optional) - Factorial via FJP

Use FJP to calculate factorial. Compare with the sequential implementation. Use BigInteger to keep values.

* __Task 2__ - Multithreading Sorting via FJP

Implement Merge Sort or Quick Sort algorithm that sorts huge array of integers in parallel using Fork/Join framework.

* __Task 3__ - File Scanner via FJP

Create CLI application that scans a specified folder and provides detailed statistics:

File count.
Folder count.
Size (sum of all files size) (similar like Windows context menu Properties). Since the folder may contain huge number of files the scanning process should be executed in a separate thread displaying an informational message with some simple animation like progress bar in CLI (up to you, but I'd like to see that task is in progress).
Once task is done, the statistics should be displayed in the output immediately. Additionally, there should be ability to interrupt the process pressing some reserved key (for instance c). Of course, use Fork-Join Framework for implementation parallel scanning.

* __Task 4__ - Completable Future Helps to Build Open Salary Society

Assume, we have REST endpoint that returns a list of hired Employees. 
- REST endpoint is wrapped by Java service class that consuming this endpoint.
- Fetch a list of Employee objects asynchronously by calling the hiredEmployees().
- Join another CompletionStage List that takes care of filling the salary of each hired employee, by calling the getSalary(hiredEmployeeId) method which returns a CompletionStage that asynchronously fetches the salary (again could be consuming a REST endpoint).
- When all Employee objects are filled with their salaries, we end up with a List of CompletionStage, so we call "special operation on CF" to get a final stage that completes upon completion of all these stages.
- Print hired Employees with their salaries via "special operation on CF" on final stage.

Provide correct solution with CF usage and use appropriate CF operators instead "special operation on CF". Why does the CF usage improve performance here in comparison with synchronous approach? Discuss it with mentor. How thread waiting is implemented in synchronous world?

* __Task 5__ (prodcons module): Solve producer–consumer problem

Using:
1. Semaphore
2. BlockingQueue

* __Task 6__

_RecursiveTask_

Give example from [RecursiveTask](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/RecursiveTask.html) javadoc.
Write FibonacciTask that implements RecursiveTask.
Apply suggestion from javadoc to check minimum granularity size less or equal 10. And in that case use linear algorithm.
Using unit test check that your code works correctly:
assertEquals(1134903170L, new ForkJoinPool().invoke(new FibonacciTask(45)).longValue());

_RecursiveAction_

Sum of double squares

Give last example from [RecursiveAction](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/RecursiveAction.html) javadoc about calculation of sum of squares in double[] array.
Use double array of half-billion size 500_000_000 filled by random doubles.
Compare speed with direct linear calculation (you may use Stream API as well):
double sum = 0; for (double v : ARRAY) { sum += v * v; }

* __Task 7__ - Blurring for Clarity (forkjoin module)

Execute ForkBlur example from [Java SE Fork/Join tutorial](https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html)