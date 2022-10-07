package ru.home.textRedactor.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;


@Component
public class Reader {
    private final BufferedReader buffReader;

    private InitialData initialData;

    public Reader(){
        buffReader = new BufferedReader(new InputStreamReader(System.in));
    }
    public Reader(String filePath) throws FileNotFoundException {
        buffReader = new BufferedReader(new FileReader(filePath));
    }

    @Autowired
    public void setInitialData(InitialData initialData) {
        this.initialData = initialData;
    }

    public void read() throws IOException {
        int wordsCount = Integer.parseInt(buffReader.readLine());
        for (int i = 0; i < wordsCount; i++) {
            String[] pair = buffReader.readLine().split(" ");
            initialData.putWord(pair[0], Integer.parseInt(pair[1]));
        }
        int prefixCount = Integer.parseInt(buffReader.readLine());
        for (int i = 0; i < prefixCount; i++) {
            String prefix = buffReader.readLine();
            initialData.addPrefix(prefix);
        }
        buffReader.close();
    }
}
