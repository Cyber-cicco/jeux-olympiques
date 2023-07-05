package org.example.parser;

import lombok.SneakyThrows;
import org.example.config.CSVConfig;
import org.example.dao.DaoFactory;
import org.example.threader.VirtualThread;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class FileParser {

    private final EpreuveParser epreuveParser = new EpreuveParser();
    private final SportParser sportParser = new SportParser();
    private final OrganisationParser organisationParser = new OrganisationParser();
    private final EvenementParser evenementParser = new EvenementParser();

    @SneakyThrows
    public void readCSV() throws IOException {
        Path evenements = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                        .getResource(CSVConfig.FICHIER_EVENEMENTS))
                .toURI());
        Path epreuves = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                        .getResource(CSVConfig.FICHIER_EPREUVES))
                .toURI());
        Path organisations = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                        .getResource(CSVConfig.FICHIER_ORGANISATIONS))
                .toURI());
        Path sports = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                        .getResource(CSVConfig.FICHIER_SPORTS))
                .toURI());
        Thread epreuveThread = VirtualThread.getThread("epreuve", () -> {
            AtomicInteger lineNumber = new AtomicInteger(1);
            try(Stream<String> lines = Files.lines(epreuves)){
                lines
                        .filter(line -> lineNumber.getAndIncrement() > 1 )
                        .forEach(line -> epreuveParser.parseLine(line, lineNumber.get()));
            } catch (IOException e){
                e.printStackTrace();
            }
        });
        Thread organisationThread = VirtualThread.getThread("organisation", () -> {
            AtomicInteger lineNumber = new AtomicInteger(1);
            try(Stream<String> lines = Files.lines(organisations)){
                lines
                        .filter(line -> lineNumber.getAndIncrement() > 1 )
                        .forEach(line -> organisationParser.parseLine(line, lineNumber.get()));
            } catch (IOException e){
                e.printStackTrace();
            }
        });
        Thread sportThread = VirtualThread.getThread("sport", () -> {
            AtomicInteger lineNumber = new AtomicInteger(1);
            try(Stream<String> lines = Files.lines(sports)){
                lines
                        .filter(line -> lineNumber.getAndIncrement() > 1 )
                        .forEach(line -> sportParser.parseLine(line, lineNumber.get()));
            } catch (IOException e){
                e.printStackTrace();
            }
        });
        epreuveThread.join();
        sportThread.join();
        organisationThread.join();
        Thread evenementThread = VirtualThread.getThread("evenement", () ->{
            AtomicInteger lineNumber = new AtomicInteger(1);
            try(Stream<String> lines = Files.lines(evenements)){
                lines
                        .filter(line -> lineNumber.getAndIncrement() > 1 )
                        .forEach(line -> evenementParser.parseLine(line, lineNumber.get()));
            } catch (IOException e){
                e.printStackTrace();
            }
        });
        evenementThread.join();
        DaoFactory.closeDaos();
    }
}
