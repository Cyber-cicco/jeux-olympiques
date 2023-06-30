package org.example;

import org.example.parser.FileParser;

public class Main {
    public static void main(String[] args) {
        FileParser fileParser = new FileParser();
        fileParser.readCSV();
    }
}