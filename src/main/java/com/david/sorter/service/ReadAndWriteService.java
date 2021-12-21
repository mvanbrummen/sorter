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
import java.util.stream.Stream;

@Slf4j
@Service
public class ReadAndWriteService {

    public Stream<String> readFromFile(String fileName) throws FileNotFoundException {
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

    public void writeToFile(String fileName, Stream<String> sortedLines) throws IOException {
        final var fullPath = new File(fileName).toPath();
        try (BufferedWriter bw = Files.newBufferedWriter(fullPath)) {
            sortedLines.forEach(line -> ReadAndWriteService.writeToFile(bw, line));
        }
    }


    public static void writeToFile(BufferedWriter bw, String line) {
        try {
            bw.write(line);
            bw.write("\r\n");
        } catch (IOException e) {
            log.error("A error has ocurred : " + e);
        }
    }
}
