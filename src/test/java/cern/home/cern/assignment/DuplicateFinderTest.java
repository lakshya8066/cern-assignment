package cern.home.cern.assignment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("DuplicateFinder Tests")
class DuplicateFinderTest {

    @Test
    @DisplayName("Should find duplicates in the example case")
    void testExampleCase() {
        Stream<String> input = Stream.of("b", "a", "c", "c", "e", "a", "c", "d", "c", "d");
        List<String> result = DuplicateFinder.findDuplicates(input).toList();

        assertThat(result).containsExactly("a", "c", "d");
    }

    @Test
    @DisplayName("Should return empty stream when no duplicates exist")
    void testNoDuplicates() {
        Stream<String> input = Stream.of("a", "b", "c", "d", "e");
        List<String> result = DuplicateFinder.findDuplicates(input).toList();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return empty stream for empty input")
    void testEmptyStream() {
        Stream<String> input = Stream.empty();
        List<String> result = DuplicateFinder.findDuplicates(input).toList();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should preserve order of first appearance")
    void testOrderPreservation() {
        Stream<String> input = Stream.of("z", "a", "m", "b", "z", "m", "a");
        List<String> result = DuplicateFinder.findDuplicates(input).toList();

        assertThat(result).containsExactly("z", "a", "m");
    }

    @Test
    @DisplayName("Should handle null elements in stream")
    void testWithNullElements() {
        Stream<String> input = Stream.of("a", null, "b", null, "a");
        List<String> result = DuplicateFinder.findDuplicates(input).toList();

        assertThat(result).containsExactly("a", null);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when stream is null")
    void testNullStream() {
        Stream<String> input = null;

        assertThatThrownBy(() -> DuplicateFinder.findDuplicates(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Input stream must not be null");
    }

    @Test
    @DisplayName("Should work with custom Serializable objects")
    void testWithCustomSerializableObjects() {
        TestObject obj1 = new TestObject(1, "first");
        TestObject obj2 = new TestObject(2, "second");
        TestObject obj1Duplicate = new TestObject(1, "first");

        Stream<TestObject> input = Stream.of(obj1, obj2, obj1Duplicate, obj2);
        List<TestObject> result = DuplicateFinder.findDuplicates(input).toList();

        assertThat(result).containsExactly(obj1, obj2);
    }

    /**
     * Test class for verifying behavior with custom Serializable objects.
     */
    private static class TestObject implements Serializable {
        private static final long serialVersionUID = 1L;

        private final int id;
        private final String name;

        TestObject(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestObject that = (TestObject) o;
            return id == that.id && name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return 31 * id + name.hashCode();
        }

        @Override
        public String toString() {
            return "TestObject{id=" + id + ", name='" + name + "'}";
        }
    }
}
