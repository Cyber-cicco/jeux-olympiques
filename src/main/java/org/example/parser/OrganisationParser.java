package org.example.parser;

import org.example.config.CSVConfig;
import org.example.dao.DaoFactory;
import org.example.dao.OrganisationDao;
import org.example.entites.Organisation;

public class OrganisationParser extends Parser<Organisation> {
    private Organisation organisation;
    private boolean hasToPersist;
    private OrganisationDao organisationDao = DaoFactory.getOrganisationDao();
    private void parseEpreuve(){
        String codeCIO = getBasicFieldString();
        organisation.setCodeCIO(codeCIO);
    }
    private void parsePaysEN(){
        String pays = getBasicFieldString();
        organisation.setNomPaysEN(pays.replace("\u00a0",""));
    }
    private void parsePaysFR(){
        String pays = getBasicFieldString();
        organisation.setNomPaysFR(pays.replace("\u00a0",""));
    }
    private void parseCodeISOAlhpa(){
        String codeISOAlpha = getBasicFieldString();
        organisation.setCodeISOAlpha(codeISOAlpha);
    }
    private void parseObselete(){
        char obs = text.charAt(position);
        organisation.setObselete(obs == 'O');
    }
    private void initializeParsing(String text, int lineNumber){
        this.text = text;
        hasToPersist = lineNumber == CSVConfig.ORGANISATION_LINES;
        position = 0;
        organisation = new Organisation();
    }

    private void finalizeParsing(){
        entities.add(organisation);
        if(hasToPersist) organisationDao.persist(entities);
    }
    @Override
    public void parseLine(String line, int lineNumber) {
        initializeParsing(line, lineNumber);
        parseEpreuve();
        nextChar();
        parsePaysFR();
        nextChar();
        parsePaysEN();
        nextChar();
        parseCodeISOAlhpa();
        nextChar();
        parseObselete();
        finalizeParsing();
    }
}
