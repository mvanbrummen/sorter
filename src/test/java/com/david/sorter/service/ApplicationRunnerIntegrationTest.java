package com.david.sorter.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest(args = "/Users/mvanbrummen/workspace/sorter/src/test/resources/names.txt")
class ApplicationRunnerIntegrationTest {

    @Autowired
    ApplicationArguments args;

    @SpyBean
    ApplicationRunner applicationRunner;

    @SpyBean
    ReadAndWriteService readAndWriteService;

    @Autowired
    private ApplicationContext context;

    @Test
    public void verify_application_runner_runs() throws IOException {
        verify(applicationRunner, times(1)).run(any());
        verify(readAndWriteService, atLeastOnce()).writeToFile(any(String.class), any());
        verify(readAndWriteService, atLeastOnce()).readFromFile(any(String.class));
    }
}