package org.example.parser;

import org.example.dao.*;
import org.example.entites.*;
import org.example.types.Medaille;
import org.example.types.Season;
import org.example.types.Sex;

import java.util.List;

public class EvenementParser extends Parser {

    private AthleteDao athleteDao = DaoFactory.getAthleteDao();
    private EpreuveDao epreuveDao = DaoFactory.getEpreuveDao();
    private OrganisationDao organisationDao = DaoFactory.getOrganisationDao();
    private ResultatDao resultatDao = DaoFactory.getResultatDao();
    private SessionDao sessionDao = DaoFactory.getSessionDao();
    private SportDao sportDao = DaoFactory.getSportDao();
    private Athlete athlete;
    private Epreuve epreuve;
    private Session session;
    private Resultat resultat;
    private Sport sport;
    private Organisation organisation;

    private void initializeParsing(String text){
        this.text = text;
        position = 0;
        session = new Session();
        athlete = new Athlete();
        epreuve = new Epreuve();
        resultat = new Resultat();
    }

    private void parseNomAthlete(){
        var nomAthlete = getBasicFieldString();
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
        String height = getBasicFieldString();
        athlete.setHeight(Integer.parseInt(height));
    }

    private void parseWeightForSession() {
        var weight = getBasicFieldString();
        resultat.setPoids(Double.parseDouble(weight));
    }

    private void setTeam(){
        String team = getBasicFieldString();
        String query = "select o from Organisation o where o.codeCIO = :identifier0";
        organisation = organisationDao.extraireParId(team, query, List.of(team));
    }

    private void ignoreGames(){
        while (text.charAt(position) != CSV_SEPARATOR){
            nextChar();
        }
    }

    private void parseYear() {
        String year = getBasicFieldString();
        session.setYear(Integer.parseInt(year));
    }

    private void parseSeasons(){
        var season = getBasicFieldString();
        if (season.equals("Summer")) {
            session.setSeason(Season.SUMMER);
        } else {
            session.setSeason(Season.WINTER);
        }
    }

    private void parseCity(){
        String city = getBasicFieldString();
        session.setCity(city);
    }

    private void getSport(){
        String nomSport = getBasicFieldString();
        String query = "select s from Sport s where s.libelleEN = :indentifier0";
        sport = sportDao.extraireParId(nomSport, query, List.of(nomSport));
        epreuve.setSport(sport);
    }

    private void parseEpreuve(){
        String nomEpreuve = getBasicFieldString();
        epreuve.setEvent(nomEpreuve);
    }

    private void parseResultat(){
        String nom = text.substring(position);
        switch (nom){
            case "Gold" -> resultat.setMedaille(Medaille.OR);
            case "Silver" -> resultat.setMedaille(Medaille.ARGENT);
            case "Bronze" -> resultat.setMedaille(Medaille.BRONZE);
        }
    }

    private void endParsing(){
        resultat.setSession(session);
        resultat.setAthlete(athlete);
        resultat.setEpreuve(epreuve);
        resultat.setOrganisation(organisation);
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
