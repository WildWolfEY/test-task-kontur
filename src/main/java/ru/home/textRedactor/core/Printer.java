package ru.home.textRedactor.core;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

@Component
public class Printer {
    private PrintWriter writer;

    public Printer() {
    }

    public Printer(String filePath) {
        try {
            writer = new PrintWriter(new FileWriter(new File(filePath)));
        } catch (Exception exception) {
            System.err.println("Ошибка доступа к файлу " + filePath);
            System.err.println(exception.getMessage());
            System.err.println("Будет использован вывод в консоль");
        }
    }

    public void print(String prefix, Set<Map.Entry<String, Integer>> topWords) {
        System.out.println(prefix);
        for (Map.Entry<String, Integer> word : topWords) {
            System.out.println(word);
        }
        System.out.println();
    }

    public void printInFile(String prefix, Set<Map.Entry<String, Integer>> topWords) {
        if (writer == null) {
            print(prefix, topWords);
        } else {
            writer.append(prefix + "\n");
            for (Map.Entry<String, Integer> word : topWords) {
                writer.append(word.toString() + "\n");
            }
            writer.flush();
            writer.close();
        }
    }
}
