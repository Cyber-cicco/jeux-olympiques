package org.example.parser;

import org.example.config.CSVConfig;
import org.example.dao.DaoFactory;
import org.example.dao.SportDao;
import org.example.entites.Sport;

public class SportParser extends Parser<Sport> {

    private boolean hasToPersist;
    private Sport sport;
    private SportDao sportDao = DaoFactory.getSportDao();
    private void initializeParsing(String text, int lineNumber){
        hasToPersist = lineNumber == CSVConfig.SPORT_LINES;
        this.text = text;
        position = 0;
        sport = new Sport();
    }
    @Override
    public void parseLine(String line, int lineNumber) {
        initializeParsing(line, lineNumber);
        parseLibelleEn();
        nextChar();
        parseLibelleFR();
        endParsing();
    }

   private void endParsing(){
        entities.add(sport);
        if(hasToPersist) sportDao.persist(entities);
    }

    private void parseLibelleFR() {
        int start = position;
        while(position < text.length()){
            nextChar();
        }
        String libelleFR = text.substring(start, position);
        sport.setLibelleFR(libelleFR);
    }

    private void parseLibelleEn() {
        String libelleEN = getBasicFieldString();
        sport.setLibelleEN(libelleEN);
    }
}
