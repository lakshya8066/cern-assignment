package cern.home.cern.assignment.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BloomFilter Tests")
class BloomFilterTest {

    @Test
    @DisplayName("Should mark element as seen and detect it as duplicate")
    void givenElementMarkedAsSeen_whenCheckingIfDuplicate_thenReturnsTrue() {
        BloomFilter filter = new BloomFilter(1000);
        String element = "test";

        filter.markAsSeen(element);
        boolean isDuplicate = filter.isDuplicate(element);

        assertThat(isDuplicate).isTrue();
    }

    @Test
    @DisplayName("Should return false for element not seen before")
    void givenElementNotSeen_whenCheckingIfDuplicate_thenReturnsFalse() {
        BloomFilter filter = new BloomFilter(1000);
        String element = "test";

        boolean isDuplicate = filter.isDuplicate(element);

        assertThat(isDuplicate).isFalse();
    }

    @Test
    @DisplayName("Should mark element as reported and detect it")
    void givenElementMarkedAsReported_whenCheckingIfAlreadyReported_thenReturnsTrue() {
        BloomFilter filter = new BloomFilter(1000);
        String element = "test";

        filter.markAsReported(element);
        boolean isReported = filter.isAlreadyReported(element);

        assertThat(isReported).isTrue();
    }

    @Test
    @DisplayName("Should handle null elements correctly")
    void givenNullElement_whenMarkingAndChecking_thenHandlesNullProperly() {
        BloomFilter filter = new BloomFilter(1000);

        filter.markAsSeen(null);
        boolean isDuplicate = filter.isDuplicate(null);

        assertThat(isDuplicate).isTrue();
    }

    @Test
    @DisplayName("Should differentiate between seen and reported elements")
    void givenElementOnlySeen_whenCheckingIfReported_thenReturnsFalse() {
        BloomFilter filter = new BloomFilter(1000);
        String element = "test";

        filter.markAsSeen(element);
        boolean isReported = filter.isAlreadyReported(element);

        assertThat(isReported).isFalse();
    }
}
