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


    protected void readWriteFile(String fileNames) throws IOException {
        var split = fileNames.split(",");
        if (Arrays.stream(split).count() != 2) {
           log.error("Filenames to read from and to write to are required");
            return;
        }

        var readFromFileName = split[0];
        var writeToFileName = split[1];

        final var readlines = readAndWriteService.readFromFile(readFromFileName);

        final var sortedLines = getFullNameListSorted.apply(readlines);

        readAndWriteService.writeToFile(writeToFileName, sortedLines);

    }


    public static Function<Stream<String>, Stream<String>> getFullNameListSorted =
            stringStream -> stringStream.map(FullName::from)
                .sorted(Comparator.comparing(FullName::lastName)
                        .thenComparing(FullName::firstName))
                .map(FullName::format);


    public record FullName (String firstName, String lastName) {
        static FullName from(String personName) {
            var split = personName.split(",");
            var firstName = split[1];
            var lastName = split[0];

            return new FullName(firstName, lastName);
        }

        public String format() {
            return "%s, %s".formatted(this.lastName, this.firstName);
        }
    }
}
