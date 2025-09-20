# Java Multithreading Examples

This repository demonstrates core multithreading concepts in Java:

- Thread lifecycle  
- Synchronization using `synchronized`, `ReentrantLock`, etc.  
- Working with `ExecutorService` / `ThreadPoolExecutor`  
- Futures and how `Future.get()` behaves  
- `CompletableFuture` API: `thenApply`, `thenCompose`, `thenCombine`, `thenAccept`, etc.  
- Pitfalls like task rejection, blocking vs non-blocking calls  

## Requirements

- Java 8 or higher  
- Use of concurrent package (`java.util.concurrent`)  

## How to Run Samples

Each example has its own `main` class. You can run them via your IDE or from command line:

```bash
cd path/to/project/src
javac multithreading/future/*.java
java multithreading.future.Main
