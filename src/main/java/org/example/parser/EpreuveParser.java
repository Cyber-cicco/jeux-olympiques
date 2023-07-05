package org.example.parser;

import org.example.dao.DaoFactory;
import org.example.dao.EpreuveDao;
import org.example.entites.Epreuve;

public class EpreuveParser extends Parser<Epreuve>{
    private boolean proceedParsing;
    private Epreuve epreuve;
    private EpreuveDao epreuveDao = DaoFactory.getEpreuveDao();
    private void initializeParsing(String text, int lineNumber){
        proceedParsing = lineNumber > 3;
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
        epreuveDao.putInCache(epreuve);
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
