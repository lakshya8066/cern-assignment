package cern.home.cern.assignment.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for DuplicateIterator functionality.
 */
@DisplayName("DuplicateIterator Tests")
class DuplicateIteratorTest {

    @Test
    @DisplayName("Should find duplicates in correct order")
    void givenListWithDuplicates_whenIterating_thenReturnsDuplicatesInOrder() {
        List<String> input = Arrays.asList("a", "b", "c", "b", "a", "d");
        BloomFilter filter = new BloomFilter(1000);
        DuplicateIterator<String> iterator = new DuplicateIterator<>(input.iterator(), filter, 1000);

        List<String> duplicates = new ArrayList<>();
        while (iterator.hasNext()) {
            duplicates.add(iterator.next());
        }

        assertThat(duplicates).containsExactly("b", "a");
    }

    @Test
    @DisplayName("Should return false when no duplicates exist")
    void givenListWithNoDuplicates_whenCheckingHasNext_thenReturnsFalse() {
        List<String> input = Arrays.asList("a", "b", "c", "d");
        BloomFilter filter = new BloomFilter(1000);
        DuplicateIterator<String> iterator = new DuplicateIterator<>(input.iterator(), filter, 1000);

        boolean hasNext = iterator.hasNext();

        assertThat(hasNext).isFalse();
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when calling next without hasNext")
    void givenNoMoreDuplicates_whenCallingNext_thenThrowsException() {
        List<String> input = Arrays.asList("a", "b", "c");
        BloomFilter filter = new BloomFilter(1000);
        DuplicateIterator<String> iterator = new DuplicateIterator<>(input.iterator(), filter, 1000);

        assertThatThrownBy(() -> iterator.next())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No more duplicates");
    }

    @Test
    @DisplayName("Should handle null elements as duplicates")
    void givenListWithNullDuplicates_whenIterating_thenReturnsNull() {
        List<String> input = Arrays.asList("a", null, "b", null);
        BloomFilter filter = new BloomFilter(1000);
        DuplicateIterator<String> iterator = new DuplicateIterator<>(input.iterator(), filter, 1000);

        List<String> duplicates = new ArrayList<>();
        while (iterator.hasNext()) {
            duplicates.add(iterator.next());
        }

        assertThat(duplicates).hasSize(1);
        assertThat(duplicates.get(0)).isNull();
    }

    @Test
    @DisplayName("Should not return same duplicate multiple times")
    void givenElementDuplicatedMultipleTimes_whenIterating_thenReturnsOnlyOnce() {
        List<String> input = Arrays.asList("a", "a", "a", "a");
        BloomFilter filter = new BloomFilter(1000);
        DuplicateIterator<String> iterator = new DuplicateIterator<>(input.iterator(), filter, 1000);

        List<String> duplicates = new ArrayList<>();
        while (iterator.hasNext()) {
            duplicates.add(iterator.next());
        }

        assertThat(duplicates).containsExactly("a");
    }
}
