package org.example.parser;

import java.util.ArrayList;
import java.util.List;

public abstract class Parser<T> {
    protected int position;
    protected List<T> entities = new ArrayList<>();
    protected final char CSV_SEPARATOR = ';';
    protected String text;
    protected String getBasicFieldString(){
        int start = position;
        while(text.charAt(position) != CSV_SEPARATOR){
            nextChar();
        }
        return text.substring(start, position).trim();
    }

    protected void nextChar(){
        position++;
    }
    public abstract void parseLine(String line, int lineNumber);

}
