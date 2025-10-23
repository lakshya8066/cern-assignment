package cern.home.cern.assignment.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator implementation for finding duplicates in a stream.
 * Uses a BloomFilter for efficient duplicate detection.
 */
public class DuplicateIterator<T> implements Iterator<T> {
    private final Iterator<T> inputIterator;
    private final BloomFilter filter;
    private T nextDuplicate;
    private boolean hasNextCached;

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
