package com.david.sorter.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest(args = "C:\\GDVA\\names.txt,C:\\GDVA\\sorted.txt")
class ApplicationRunnerIntegrationTest {

    @Autowired
    ApplicationArguments args;

    @SpyBean
    ApplicationRunner applicationRunner;

    @Autowired
    private ApplicationContext context;

    @Test
    public void verify_application_runner_runs() {
        verify(applicationRunner, times(1)).run(any());
    }
}