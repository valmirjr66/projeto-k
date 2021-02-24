package com.exemplo.iot;

import java.awt.image.BufferedImage;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;

import com.microsoft.azure.sdk.iot.device.IotHubMessageResult;
import com.microsoft.azure.sdk.iot.device.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MainInputMessageReceiver {
    private static final Logger logger = LoggerFactory.getLogger(MainInputMessageReceiver.class);
    private static final String INPUT_TO_MONITOR = "mainInput";
    private final IoTHubInterface interfaceForIotHub;
    private final OperationalSystemInterface operationalSystemInterface;
    private final ImageProcessor imageProcessor;

    public MainInputMessageReceiver(IoTHubInterface messageService,
            OperationalSystemInterface operationalSystemInterface, ImageProcessor imageProcessor) {
        this.interfaceForIotHub = messageService;
        this.operationalSystemInterface = operationalSystemInterface;
        this.imageProcessor = imageProcessor;
    }

    @PostConstruct
    public void setMessageCallback() {
        interfaceForIotHub.setMessageCallback(INPUT_TO_MONITOR, this::messageCallback);
    }

    public IotHubMessageResult messageCallback(Message message, Object callbackContext) {
        logger.info("Message received by Watcher.");

        CompletableFuture.runAsync(() -> {
            BufferedImage cameraSnapshot = this.operationalSystemInterface.getSnapshotFromCamera();
            String imageClassification = imageProcessor.classifyImage(cameraSnapshot);
            logger.info("Snapshot image classification is {}.", imageClassification);
        });

        return IotHubMessageResult.COMPLETE;
    }
}
