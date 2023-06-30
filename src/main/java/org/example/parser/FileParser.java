package org.example.parser;

import lombok.SneakyThrows;
import org.example.config.CSVConfig;
import org.example.threader.VirtualThread;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class FileParser {

    private final EpreuveParser epreuveParser = new EpreuveParser();
    private final SportParser sportParser = new SportParser();
    private final OrganisationParser organisationParser = new OrganisationParser();

    @SneakyThrows
    public void readCSV() {
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
            try(Stream<String> lines = Files.lines(epreuves)){
                lines.forEach(epreuveParser::parseLine);
            }
        });
        Thread organisationThread = VirtualThread.getThread("organisation", () -> {
            try(Stream<String> lines = Files.lines(epreuves)){
                lines.forEach(organisationParser::parseLine);
            }
        });
        Thread sportThread = VirtualThread.getThread("sport", () -> {
            try(Stream<String> lines = Files.lines(epreuves)){
                lines.forEach(sportParser::parseLine);
            }
        });
        epreuveThread.join();
        sportThread.join();
        organisationThread.join();
    }
}
