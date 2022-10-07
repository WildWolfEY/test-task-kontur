package ru.home.textRedactor.core;

import org.springframework.stereotype.Component;

@Component
public class Sieve {
    private int[] maxElements;

    public Sieve() {
        maxElements = new int[10];
    }

    public boolean isInTop(int frequency) {
        for (int i = 0; i < maxElements.length; i++) {
            if (maxElements[i] < frequency) {
                maxElements[i] = frequency;
                return true;
            }
        }
        return false;
    }

    public void reset() {
        for (int i = 0; i < maxElements.length; i++) {
            maxElements[i] = 0;
        }
    }


}
