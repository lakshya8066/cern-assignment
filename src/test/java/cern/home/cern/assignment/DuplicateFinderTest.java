package cern.home.cern.assignment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DuplicateFinder Tests")
class DuplicateFinderTest {

    @Test
    @DisplayName("Should find duplicates in the example case")
    void testExampleCase() {
        Stream<String> input = Stream.of("b", "a", "c", "c", "e", "a", "c", "d", "c", "d");
        List<String> result = DuplicateFinder.findDuplicates(input).toList();

        assertThat(result).containsExactly("a", "c", "d");
    }
}
