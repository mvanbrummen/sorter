package com.david.sorter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@Slf4j
@Service
public class ReadAndWriteService {

    public static final String EXT_PATTERN = "(?<!^)[.]" + (true ? ".*" : "[^.]*$");

    public Stream<String> readFromFile(final String fileName) throws FileNotFoundException {
        RandomAccessFile reader = new RandomAccessFile(fileName, "r");
        FileChannel channel = reader.getChannel();

        BufferedReader br = new BufferedReader(Channels.newReader(channel, StandardCharsets.UTF_8));
        return br.lines().onClose(() -> {
            try {
                br.close();
            } catch (IOException ex) {
                log.error(String.valueOf(ex));
            }
        });
    }

    public void writeToFile(final String fileName, final Stream<String> sortedLines) throws IOException {
        final var copyForWritingToFile = List.copyOf(sortedLines.toList());
        final var copyForWritingToConsole = List.copyOf(copyForWritingToFile);

        final String outputFileName = generateOutputFileName.apply(fileName);
        final var outPutPath = new File(outputFileName).toPath();

        try (BufferedWriter bw = Files.newBufferedWriter(outPutPath)) {
            copyForWritingToFile.forEach(line -> ReadAndWriteService.writeToFile(bw, line));
        }

        log.info("the sorted file is at : %s".formatted(outputFileName));
        copyForWritingToConsole.forEach(log::info);
    }

    public static void writeToFile(final BufferedWriter bw, final String line) {
        try {
            bw.write(line);
            bw.write("\r\n");
        } catch (IOException e) {
            log.error("A error has ocurred while writing to file: %s".formatted(e));
        }
    }

    public Function<String, String> generateOutputFileName = name -> {
        final var inputFileFullPath = new File(name).toPath();
        final var fileNameWithoutExtension = inputFileFullPath.getFileName().toString().replaceAll(EXT_PATTERN, "");

        return "%s/%s-sorted.txt".formatted(inputFileFullPath.getParent().toString(), fileNameWithoutExtension);
    };
}
