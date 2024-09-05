package com.krist.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringParser {
    public static Map<Long, List<Long>> parseAttributesWithMultipleValues(String attributes) {
        Map<Long, List<Long>> filtersMap = new HashMap<>();
        if (attributes != null && !attributes.isEmpty()) {
            String[] filters = attributes.split("_");
            for (String filter : filters) {
                String[] parts = filter.split("-");
                if (parts.length == 2) {
                    Long attributeId = Long.valueOf(parts[0]);
                    List<Long> valueIds = Arrays.stream(parts[1].split(",")).map(Long::valueOf).toList();
                    filtersMap.put(attributeId, valueIds);
                }
            }
        }
        return filtersMap;
    }

    public static Map<Long, Long> parseAttributesWithSingleValue(String attributes) {
        Map<Long, Long> filtersMap = new HashMap<>();
        if (attributes != null && !attributes.isEmpty()) {
            String[] filters = attributes.split("_");
            for(String filter : filters) {
                String[] parts = filter.split("-");
                if (parts.length == 2) {
                    Long attributeId = Long.valueOf(parts[0]);
                    Long valueId = Long.valueOf(parts[1]);
                    filtersMap.put(attributeId, valueId);
                }
            }
        }
        return filtersMap;
    }
}
