package com.exemplo.iot;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import boofcv.abst.scene.ImageClassifier;
import boofcv.factory.scene.ClassifierAndSource;
import boofcv.factory.scene.FactoryImageClassifier;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.Planar;
import deepboof.io.DeepBoofDataBaseOps; 

@Component
public class ScheduledTask {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    private final IoTHubInterface IoTHubInterface;

    public ScheduledTask (IoTHubInterface IoTHubInterface) {
        this.IoTHubInterface=IoTHubInterface;
    }

    @Scheduled(fixedDelay = 300000)
    public void task() throws IOException {
        Message messageToSend = new Message();
         IoTHubInterface.sendMessage();
        logger.info("Foo bar.");
    }
}
