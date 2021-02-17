package com.exemplo.iot;

import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.device.Message;
import com.microsoft.azure.sdk.iot.device.MessageCallback;
import com.microsoft.azure.sdk.iot.device.ModuleClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

// @Service
public class IotHubMessageService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(IotHubMessageService.class);

    private static ModuleClient client;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            IotHubClientProtocol protocol = IotHubClientProtocol.MQTT;
            client = ModuleClient.createFromEnvironment(protocol);
            client.open();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void sendMessage(Message message, String output) {
        client.sendEventAsync(message, (IotHubStatusCode status, Object context) -> {
            logger.info("Message set with status {}.", status);
        }, message, output);
    }

    public void setMessageCallback(String inputName, MessageCallback callback) {
        client.setMessageCallback(inputName, callback, client);
    }
}
