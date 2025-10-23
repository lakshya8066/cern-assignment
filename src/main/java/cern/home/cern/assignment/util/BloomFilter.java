package cern.home.cern.assignment.util;

import java.util.BitSet;

/**
 * A Bloom filter implementation for efficient duplicate detection.
 * Uses multiple hash functions to reduce false positives.
 */
public class BloomFilter {
    private static final int NUM_HASH_FUNCTIONS = 5;
    private static final int[] HASH_PRIMES = {31, 37, 41, 43, 47};

    private final BitSet seenOnce;
    private final BitSet seenTwice;
    private final int capacity;

    public BloomFilter(int capacity) {
        this.capacity = capacity;
        this.seenOnce = new BitSet(capacity);
        this.seenTwice = new BitSet(capacity);
    }

    public boolean isAlreadyReported(Object element) {
        int[] hashes = computeHashes(element);
        for (int hash : hashes) {
            if (!seenTwice.get(hash)) {
                return false;
            }
        }
        return true;
    }

    public boolean isDuplicate(Object element) {
        int[] hashes = computeHashes(element);
        for (int hash : hashes) {
            if (!seenOnce.get(hash)) {
                return false;
            }
        }
        return true;
    }

    public void markAsSeen(Object element) {
        int[] hashes = computeHashes(element);
        for (int hash : hashes) {
            seenOnce.set(hash);
        }
    }

    public void markAsReported(Object element) {
        int[] hashes = computeHashes(element);
        for (int hash : hashes) {
            seenTwice.set(hash);
        }
    }

    private int[] computeHashes(Object element) {
        int[] hashes = new int[NUM_HASH_FUNCTIONS];
        for (int i = 0; i < NUM_HASH_FUNCTIONS; i++) {
            hashes[i] = hashPosition(element, i, capacity);
        }
        return hashes;
    }

    private static int hashPosition(Object element, int seed, int capacity) {
        int hash = element == null ? 0 : element.hashCode();
        int prime = HASH_PRIMES[seed];
        hash = hash ^ (seed * prime);
        hash = hash * prime + seed;
        return Math.abs(hash) % capacity;
    }
}
