package org.example.parser;

public abstract class Parser {
    protected int position;
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
    public abstract void parseLine(String line);

}
