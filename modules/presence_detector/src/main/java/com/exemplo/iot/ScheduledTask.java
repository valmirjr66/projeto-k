package com.exemplo.iot;

import java.io.IOException;

import com.microsoft.azure.sdk.iot.device.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    private static final String MAIN_OUTPUT = "mainOutput";
    private final IoTHubInterface IoTHubInterface;

    public ScheduledTask(IoTHubInterface IoTHubInterface) {
        this.IoTHubInterface = IoTHubInterface;
    }

    @Scheduled(fixedDelay = 10000)
    public void task() throws IOException {
        Message messageToSend = new Message("foo bar");
        IoTHubInterface.sendMessage(messageToSend, MAIN_OUTPUT);
    }
}
