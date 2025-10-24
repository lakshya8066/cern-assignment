package cern.home.cern.assignment;

import cern.home.cern.assignment.util.BloomFilter;
import cern.home.cern.assignment.util.DuplicateIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.stream.Stream;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;

/**
 * Utility class for finding duplicate elements in streams.
 * Uses a Bloom Filter for memory-efficient duplicate detection.
 */
public final class DuplicateFinder {

    private static final int DEFAULT_CAPACITY = 100000;

    private DuplicateFinder() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Finds duplicate elements in the given stream using default capacity.
     *
     * @param <T> the type of elements in the stream
     * @param stream the input stream to process
     * @return a stream containing only duplicate elements
     * @throws IllegalArgumentException if stream is null
     */
    public static <T extends Serializable> Stream<T> findDuplicates(Stream<T> stream) {
        return findDuplicates(stream, DEFAULT_CAPACITY);
    }

    /**
     * Finds duplicate elements in the given stream with custom capacity.
     *
     * @param <T> the type of elements in the stream
     * @param stream the input stream to process
     * @param capacity the capacity for the Bloom filter
     * @return a stream containing only duplicate elements
     * @throws IllegalArgumentException if stream is null or capacity is invalid
     */
    public static <T extends Serializable> Stream<T> findDuplicates(Stream<T> stream, int capacity) {
        validateInputs(stream, capacity);
        
        BloomFilter filter = new BloomFilter(capacity);
        Iterator<T> duplicateIterator = new DuplicateIterator<>(stream.iterator(), filter, capacity);
        
        return stream(spliteratorUnknownSize(duplicateIterator, ORDERED), false);
    }

    private static void validateInputs(Stream<?> stream, int capacity) {
        if (stream == null) {
            throw new IllegalArgumentException("Input stream cannot be null");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive, got: " + capacity);
        }
    }
}
