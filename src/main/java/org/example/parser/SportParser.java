package org.example.parser;

import org.example.dao.DaoFactory;
import org.example.dao.SportDao;
import org.example.entites.Sport;

public class SportParser extends Parser {

    private Sport sport;
    private SportDao sportDao = DaoFactory.getSportDao();
    private void initializeParsing(String text){
        this.text = text;
        position = 0;
        sport = new Sport();
    }
    @Override
    public void parseLine(String line) {
        initializeParsing(line);
        parseLibelleEn();
        nextChar();
        parseLibelleFR();
        endParsing();
    }

    private void endParsing(){
        sportDao.persist(sport);
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
