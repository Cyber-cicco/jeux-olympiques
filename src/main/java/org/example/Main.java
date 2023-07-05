package org.example;

import lombok.SneakyThrows;
import org.example.parser.FileParser;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        FileParser fileParser = new FileParser();
        fileParser.readCSV();
    }
}