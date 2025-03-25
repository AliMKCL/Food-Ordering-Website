package com.example.demo.service;


import java.util.*;

public class SortService {

    //Sorts the map in a descending order
    public static Map<String, Double> sortByValueDescending(HashMap<String, Double> hm) {
        return hm.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(LinkedHashMap::new,
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                        LinkedHashMap::putAll);
    }
}

