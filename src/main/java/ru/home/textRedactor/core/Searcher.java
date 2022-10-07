package ru.home.textRedactor.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*; // TreeSet глючит
import java.util.stream.Collectors;

@Component
public class Searcher {
    private TreeMap<String, Set<Map.Entry<String, Integer>>> cacheMap = new TreeMap<>();
    private Sieve sieve;
    private InitialData initialData;

    @Autowired
    public void setInitialData(InitialData initialData) {
        this.initialData = initialData;
    }

    @Autowired
    public void setSieve(Sieve sieve) {
        this.sieve = sieve;
    }

    public Set<Map.Entry<String, Integer>> getTopWords(String prefix) {
        Set<Map.Entry<String, Integer>> cachedList = cacheMap.get(prefix);
        if (cachedList != null) {
            return cachedList;
        }
        Set<Map.Entry<String, Integer>> topWords = searchTopWords(prefix);
        cacheMap.put(prefix, topWords);
        return topWords;
    }

    private Set<Map.Entry<String, Integer>> searchTopWords(String prefix) {
        Map<String, Integer> allWordsStartsWithPrefix = getAllWordsStartsWithPrefix(prefix, initialData.getAllWords());
        TreeSet<Map.Entry<String, Integer>> topWords = new TreeSet<>(comparator);
        for (Map.Entry<String, Integer> entry : allWordsStartsWithPrefix.entrySet()) {
            if (sieve.isInTop(entry.getValue())) {
                topWords.add(entry);
                if (topWords.size() > 10) {
                    topWords.remove(topWords.last());
                }
            }
        }
        sieve.reset();
        return topWords;
    }

    private Set<Map.Entry<String, Integer>> searchTopWordsByStream(String prefix) {
        String nextElement = prefix;

        while (initialData.getAllWords().higherKey(nextElement) != null && nextElement.startsWith(prefix)) {
            nextElement = initialData.getAllWords().higherKey(nextElement);
        }
        Map<String, Integer> wordsStartsWithPrefix = initialData.getAllWords().subMap(prefix, nextElement);
        TreeSet<Map.Entry<String, Integer>> topSortedWords = wordsStartsWithPrefix.entrySet().stream()
                .sorted(comparator)
                .limit(10)
                .collect(Collectors.toCollection(() -> new TreeSet<>(comparator)));

        return topSortedWords;
    }

    private Map<String, Integer> getAllWordsStartsWithPrefix(String prefix, TreeMap<String, Integer> allWords) {
        String nextElement = prefix;
        while (allWords.higherKey(nextElement) != null && nextElement.startsWith(prefix)) {
            nextElement = allWords.higherKey(nextElement);
        }
        return allWords.subMap(prefix, nextElement);
    }

    private Comparator<Map.Entry<String, Integer>> comparator = new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            int result = o2.getValue().compareTo(o1.getValue());
            result = result == 0 ? o1.getKey().compareTo(o2.getKey()) : result;
            return result;
        }
    };

}
