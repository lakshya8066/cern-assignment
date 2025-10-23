# cern-assignment

## Instructions 

Use Java (JDK 17 or newer) to write a small library that provides a method <T extends Serializable> Stream<T> findDuplicates(Stream<T> list) that detects all duplicate elements in a Stream:

- given a Stream of objects, it returns another stream that contains only those that are duplicated, in the order that they appeared for the first time in the original stream.

For example, for the input ["b", "a", "c", "c", "e", "a", "c", "d", "c", "d"], the answer should be ["a", "c", "d"].

- You may not use third-party libraries for the main code, but you can use them for testing.

- You can assume the contents of the Stream fit entirely in memory.

## How to build and run tests

```
mvn clean compile && mvn test
```

## Assumptions 
1. The input stream is not a parallel stream.