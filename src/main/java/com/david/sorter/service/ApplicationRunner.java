package com.david.sorter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {
    public static final String STRING_DELIMITER = ",";
    private final ReadAndWriteService readAndWriteService;

    @Override
    public void run(String... args) {

        log.info("Start execution");
        process(args);
        log.info("Finish execution");

    }

    protected void process(String[] args) {
        Stream.of(args).findFirst()
                .ifPresentOrElse(s -> {
                            try {
                                readWriteFile(s);
                            } catch (IOException e) {
                                log.error(String.valueOf(e));
                            }
                        },
                        () -> log.error("the arguments are empty"));
    }


    protected void readWriteFile(final String fileName) throws IOException {

        final var readlines = readAndWriteService.readFromFile(fileName);

        final var sortedLines = getFullNameListSorted.apply(readlines);

        readAndWriteService.writeToFile(fileName, sortedLines);
    }


    public static Function<Stream<String>, Stream<String>> getFullNameListSorted =
            stringStream -> stringStream.map(FullName::from)
                .sorted(Comparator.comparing(FullName::lastName)
                        .thenComparing(FullName::firstName))
                .map(FullName::format);


    public record FullName (String firstName, String lastName) {
        static FullName from(String personName) {
            final var split = personName.split(STRING_DELIMITER);
            return new FullName( split[1],  split[0]);
        }

        public String format() {
            return "%s, %s".formatted(this.lastName, this.firstName);
        }
    }
}
