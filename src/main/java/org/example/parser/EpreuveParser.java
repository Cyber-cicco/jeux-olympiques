package org.example.parser;


import org.example.entites.Epreuve;

public class EpreuveParser extends Parser{
    private Epreuve epreuve;
    private void initializeParsing(String text){
        this.text = text;
        position = 0;
        epreuve = new Epreuve();
    }
    @Override
    public void parseLine(String line) {
        initializeParsing(line);
    }
}
