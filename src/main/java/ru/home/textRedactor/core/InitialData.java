package ru.home.textRedactor.core;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Component
public class InitialData {

    private final TreeMap<String, Integer> allWords = new TreeMap<>();
    private final List<String> allPrefix = new ArrayList<>();

    public TreeMap<String, Integer> getAllWords() {
        return allWords;
    }

    public List<String> getAllPrefix() {
        return allPrefix;
    }

    public void putWord(String key, int value) {
        allWords.put(key, value);
    }

    public void addPrefix(String value) {
        allPrefix.add(value);
    }
}
