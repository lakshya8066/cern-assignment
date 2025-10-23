package cern.home.cern.assignment;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public final class DuplicateFinder {

    private DuplicateFinder() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static <T extends Serializable> Stream<T> findDuplicates(Stream<T> list){

        if (list == null) {
            throw new IllegalArgumentException("Input stream must not be null");
        }

        //Using a LinkedHashMap to preserve insertion order
        Map<T, Long> elementFrequency = new LinkedHashMap<>();
        list.forEach(element ->
                elementFrequency.merge(element, 1L, Long::sum)
        );

        //Return the elements that have a count greater than 1
        return elementFrequency.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey);
    }
}
