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



### Concurrency and Multithreading Architecture (m38-concurrency-architecture)

* __Task 1__ - Das experiment

Create HashMap<Integer, Integer>. The first thread adds elements into the map, the other go along the given map and sum the values. Threads should work before catching ConcurrentModificationException. Try to fix the problem with ConcurrentHashMap and Collections.synchronizedMap(). What has happened after simple Map implementation exchanging? How it can be fixed in code? Try to write your custom ThreadSafeMap with synchronization and without. Run your samples with different versions of Java (6, 8, and 10, 11) and measure the performance. Provide a simple report to your mentor.

* __Task 2__ - Deadlocks

Create three threads:
 - 1st thread is infinitely writing random number to the collection;
 - 2nd thread is printing sum of the numbers in the collection;
 - 3rd is printing square root of sum of squares of all numbers in the collection.

Make these calculations thread-safe using synchronization block. Fix the possible deadlock.

* __Task 3__ - Where’s your bus, dude?

Implement message bus using Producer-Consumer pattern.
1.	Implement asynchronous message bus. Do not use queue implementations from java.util.concurrent.
2.	Implement producer, which will generate and post randomly messages to the queue.
3.	Implement consumer, which will consume messages on specific topic and log to the console message payload.
4.	(Optional) Application should create several consumers and producers that run in parallel.

* __Task 4__ - Create simple object pool with support for multithreaded environment. 

No any extra inheritance, polymorphism or generics needed here, just implementation of simple class:

Pool that block when it has not any items or if full.
 - Constructor - Creates filled pool of passed size
 - public Object get() - Gets object from pool or blocks if pool is empty
 - public void put(Object object) - Puts object to pool or blocks if pool is full

Use any blocking approach you like.

* __Task 5__ - Make an application that contains business logic for making exchange operations between different currencies.

1.	Create models for dealing with currencies, user accounts and exchange rates. One account can have multiple currency values. Use BigDecimal for performing of exchange calculations.
2.	Data with user accounts should be stored as files (one file per account).
3.	Separate application functionality to DAO, service and utilities.
4.	Create module which will provide high-level operations (manage accounts, currencies, exchange rates).
5.	Create sample accounts and currencies. Define sample exchange rates.
6.	Provide concurrent data access to user accounts. Simulate simultaneous currency exchanges for single account by multiple threads and ensure that all the operations are thread-safe.
7.	Use ExecutorService to manage threads.
8.	Make custom exceptions to let user know the reason of error. Do not handle runtime exceptions.
9.	Validate inputs such an account existence, sufficiency of currency amount, etc.
10.	Log information about what is happening on different application levels and about conversion results. Use Logger for that.
