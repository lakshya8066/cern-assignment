package cern.home.cern.assignment.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator implementation for finding duplicates in a stream.
 * Uses a BloomFilter for efficient duplicate detection.
 *
 * @param <T> the type of elements being iterated
 */
public class DuplicateIterator<T> implements Iterator<T> {
    private final Iterator<T> inputIterator;
    private final BloomFilter filter;
    private T nextDuplicate;
    private boolean hasNextCached;

    /**
     * Creates a new DuplicateIterator.
     *
     * @param inputIterator the source iterator
     * @param filter the bloom filter for tracking seen elements
     * @param capacity the capacity (unused, kept for compatibility)
     */
    public DuplicateIterator(Iterator<T> inputIterator, BloomFilter filter, int capacity) {
        this.inputIterator = inputIterator;
        this.filter = filter;
        this.nextDuplicate = null;
        this.hasNextCached = false;
    }

    @Override
    public boolean hasNext() {
        if (hasNextCached) {
            return true;
        }

        while (inputIterator.hasNext()) {
            T element = inputIterator.next();

            if (filter.isAlreadyReported(element)) {
                continue;
            }

            if (filter.isDuplicate(element)) {
                nextDuplicate = element;
                hasNextCached = true;
                filter.markAsReported(element);
                return true;
            }

            filter.markAsSeen(element);
        }

        return false;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more duplicates");
        }
        hasNextCached = false;
        return nextDuplicate;
    }
}
