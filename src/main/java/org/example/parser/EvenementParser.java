package org.example.parser;

import org.example.dao.*;
import org.example.entites.*;
import org.example.types.Season;
import org.example.types.Sex;

import java.util.List;

public class EvenementParser {

    private int position;
    private final char CSV_SEPARATOR = ';';

    private AthleteDao athleteDao = DaoFactory.getAthleteDao();
    private EpreuveDao epreuveDao = DaoFactory.getEpreuveDao();
    private OrganisationDao organisationDao = DaoFactory.getOrganisationDao();
    private ResultatDao resultatDao = DaoFactory.getResultatDao();
    private SessionDao sessionDao = DaoFactory.getSessionDao();
    private SportDao sportDao = DaoFactory.getSportDao();
    private String text;
    private Athlete athlete;
    private Epreuve epreuve;
    private Session session;
    private Resultat resultat;
    private Sport sport;

    private void initializeParsing(String text){
        this.text = text;
        position = 0;
        session = new Session();
        athlete = new Athlete();
        epreuve = new Epreuve();
        resultat = new Resultat();
    }

    private void nextChar(){
        position++;
    }

    private void parseNomAthlete(){
        int start = position;
        while (text.charAt(position) != CSV_SEPARATOR){
            nextChar();
        }
        var nomAthlete = text.substring(start, position);
        athlete.setName(nomAthlete);
    }

    private void parseSexeAthelete(){
        if(text.charAt(position) == 'M'){
            athlete.setSexe(Sex.MALE);
        } else {
            athlete.setSexe(Sex.FEMALE);
        }
        nextChar();
    }

    private void skipId(){
        while (text.charAt(position) != CSV_SEPARATOR){
            nextChar();
        }
    }
    private void parseHeightAthlete() {
        int start = position;
        while (text.charAt(position) != CSV_SEPARATOR){
            nextChar();
        }
        String height = text.substring(start, position);
        athlete.setHeight(Integer.parseInt(height));
    }

    private void parseWeightForSession() {
        int start = position;
        while (text.charAt(position)!= CSV_SEPARATOR){
            nextChar();
        }
        var weight = text.substring(start, position);
        resultat.setPoids(Double.parseDouble(weight));
    }

    private void setTeam(){
        int start = position;
        while (text.charAt(position) != CSV_SEPARATOR){
            nextChar();
        }
        String team = text.substring(start, position);
        String query = "select o from Organisation o where o.codeCIO = :identifier0";
        organisationDao.extraireParId(team, query, List.of(team));
    }

    private void ignoreGames(){
        while (text.charAt(position) != CSV_SEPARATOR){
            nextChar();
        }
    }

    private void parseYear() {
        int start = position;
        while (text.charAt(position) != CSV_SEPARATOR){
            nextChar();
        }
        String year = text.substring(start, position);
        session.setYear(Integer.parseInt(year));
    }

    private void parseSeasons(){
        int start = position;
        while(text.charAt(position) != CSV_SEPARATOR){
            nextChar();
        }
        var season = text.substring(start, position);
        if (season.equals("Summer")) {
            session.setSeason(Season.SUMMER);
        } else {
            session.setSeason(Season.WINTER);
        }
    }

    private void parseCity(){
        int start = position;
        while(text.charAt(position) != CSV_SEPARATOR){
            nextChar();
        }
        String city = text.substring(start, position);

    }

    private void getSport(){

    }

    private void parseEpreuve(){

    }

    private void parseResultat(){

    }

    private void endParsing(){

    }

    public void parseLine(String text){
        initializeParsing(text);
        skipId();
        nextChar();
        parseNomAthlete();
        nextChar();
        parseSexeAthelete();
        nextChar();
        parseHeightAthlete();
        nextChar();
        parseWeightForSession();
        nextChar();
    }


}
