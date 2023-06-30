package org.example.parser;

import org.example.entites.Organisation;

public class OrganisationParser extends Parser {
    private Organisation organisation;
    private void initializeParsing(String text){
        this.text = text;
        position = 0;
        organisation = new Organisation();
    }
    @Override
    public void parseLine(String line) {
        initializeParsing(line);
    }
}
