package com.david.sorter.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReadAndWriteServiceTest {
    @Mock
    private Appender mockedAppender;

    @Captor
    private ArgumentCaptor<LoggingEvent> loggingEventCaptor;

    @InjectMocks
    private ReadAndWriteService service;

    @BeforeEach
    public void setup() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.addAppender(mockedAppender);
        root.setLevel(Level.INFO);
    }

    @Test
    public void generateOutputFileName() {
        final var fileName = "C:\\test\\names.txt";
        final var outputfileNameExpected = "C:\\test\\names-sorted.txt";

        final var result = service.generateOutputFileName.apply(fileName);

        assertThat(result.toString()).isEqualTo(outputfileNameExpected.toString());
    }

    @Test
    public void readFromFile() throws FileNotFoundException {
        final var fileName = "src/test/resources/names.txt";
        final var absPath = new File(fileName).getAbsolutePath();

        final var result = service.readFromFile(absPath.toString());

        assertThat(result).isNotNull();
    }

    @Test
    public void writeToFile() throws IOException {
        final var stream = Stream.of("BAKER, THEODORE", "SMITH, ANDREW","KENT, MADISON", "SMITH, FREDRICK");
        final var fileName = "src/test/resources/names.txt";
        final var absPath = new File(fileName).getAbsolutePath();

        service.writeToFile(absPath, stream);

        verify(mockedAppender, atLeastOnce()).doAppend(loggingEventCaptor.capture());
    }
}