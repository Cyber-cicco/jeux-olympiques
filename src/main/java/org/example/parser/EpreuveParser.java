package org.example.parser;

import org.example.config.CSVConfig;
import org.example.dao.DaoFactory;
import org.example.dao.EpreuveDao;
import org.example.entites.Epreuve;

import java.util.ArrayList;
import java.util.List;

public class EpreuveParser extends Parser<Epreuve>{
    private boolean proceedParsing;
    private boolean hasToPersist;
    private Epreuve epreuve;
    private EpreuveDao epreuveDao = DaoFactory.getEpreuveDao();
    private List<Epreuve> epreuves = new ArrayList<>();
    private void initializeParsing(String text, int lineNumber){
        proceedParsing = lineNumber > 3;
        hasToPersist = lineNumber == CSVConfig.EPREUVE_LINES;
        this.text = text;
        position = 0;
        epreuve = new Epreuve();
    }
    @Override
    public void parseLine(String line, int lineNumber) {
        initializeParsing(line, lineNumber);
        if(proceedParsing){
            parseEventEN();
            nextChar();
            parseEventFR();
            nextChar();
            endParsing();
        }
    }
    private void endParsing(){
        epreuves.add(epreuve);
        if(hasToPersist) epreuveDao.persist(epreuves);
    }

    private void parseEventFR() {
        int start = position;
        while(position < text.length()-1){
            nextChar();
        }
        String eventFR = text.substring(start, position);
        epreuve.setEventFR(eventFR);
    }

    private void parseEventEN() {
        String eventEN = getBasicFieldString();
        epreuve.setEventEN(eventEN);
    }
}
