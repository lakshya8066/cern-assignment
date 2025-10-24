# cern-assignment

## Instructions 

Use Java (JDK 17 or newer) to write a small library that provides a method <T extends Serializable> Stream<T> findDuplicates(Stream<T> list) that detects all duplicate elements in a Stream:

- given a Stream of objects, it returns another stream that contains only those that are duplicated, in the order that they discovered for the first time in the original stream.

For example, for the input ["b", "a", "c", "c", "e", "a", "c", "d", "c", "d"], the answer should be ["c", "a", "d"].

- You may not use third-party libraries for the main code, but you can use them for testing.

- The input stream can now have an UNBOUNDED amount of entries, and this function should be able to run in a memory-constrained environment, so assume you CAN’T even keep a list in memory of all the different objects.

## How to build and run tests

```
mvn clean compile && mvn test
```
## Constraints and Trade-Offs
Three challenging constrains:
1. Unbounded input streams - Potentially infinite elements
2. Memory-constrained environment - Cannot store all elements or duplicates
3. Duplicate detection with ordering - Must find duplicates with some ordering guarantee

To address these constraints, a combination of two approaches is adopted.
1. Bloom Filter to track seen elements with minimal memory usage. A bloom filter is a probabilistic data structure that 
is based on hashing. It is extremely space efficient and is typically used to add elements to a set and test if an 
element is in a set.
2. Change in order of duplicates - Instead of preserving the order of first appearance, duplicates are returned in the 
order they are detected - Discovery Order.

### Why Discovery Order?

Returning duplicates in first-appearance order requires either:
1. Buffering all duplicates which violates memory constraint.
2. Multiple passes which violates streaming constraint.
3. Infinite look-ahead which is practically impossible.

With this one trade off I was able to achieve the other constraints effectively.

### Alternatives Considered
1. HashMap approach - Perfect accuracy, but it takes O(n) memory so it can't handle unbounded streams
2. Windowed buffering - Bounded memory, complex, still buffers duplicates
3. External sorting - Perfect ordering, requires disk I/O, not exactly true streaming

## Assumptions 
1. The input stream is not a parallel stream.
2. Null elements are present in the input stream and considered valid.

## Conclusion
The Bloom Filter approach balances memory efficiency, performance, and practical utility. While sacrificing perfect 
ordering and deterministic accuracy, it enables processing unbounded streams in memory-constrained environments.

## References
- https://en.wikipedia.org/wiki/Bloom_filter
- https://www.geeksforgeeks.org/java/iterators-in-java/
- https://www.geeksforgeeks.org/java/java-util-interface-spliterator-java8/
- https://www.geeksforgeeks.org/java/bloom-filter-in-java-with-examples/
- https://dzone.com/articles/implementing-a-sliding-window-streamspliterator-in
- https://www.geeksforgeeks.org/java/stream-in-java/
- Usage of ChatGPT-4 for brainstorming and refining the approach.