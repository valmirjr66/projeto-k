package com.exemplo.iot;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.device.Message;
import com.microsoft.azure.sdk.iot.device.MessageCallback;
import com.microsoft.azure.sdk.iot.device.ModuleClient;
import com.microsoft.azure.sdk.iot.device.exceptions.ModuleClientException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class IoTHubInterface {
    private static final Logger logger = LoggerFactory.getLogger(IoTHubInterface.class);

    private static ModuleClient client;

    @PostConstruct
    public void createIotHubClient() {
        try {
            IotHubClientProtocol protocol = IotHubClientProtocol.MQTT;
            client = ModuleClient.createFromEnvironment(protocol);
            client.open();
        } catch (ModuleClientException | IOException exception) {
            logger.error("Error while creating client for IoT Hub.", exception);
        }
    }

    public void sendMessage(Message message, String output) {
        client.sendEventAsync(message, this::sendMessageCallback, message, output);
    }

    private void sendMessageCallback(IotHubStatusCode status, Object context) {
        logger.info("Message sent with status {}.", status);
    }

    public void setMessageCallback(String inputName, MessageCallback callback) {
        client.setMessageCallback(inputName, callback, client);
    }
}
