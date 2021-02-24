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
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class FakeOperationalSystemInterface implements OperationalSystemInterface {
    private static final Logger logger = LoggerFactory.getLogger(FakeOperationalSystemInterface.class);

    public BufferedImage getSnapshotFromCamera() {
        BufferedImage bufferedImage = null;

        try {
            byte[] imageByte = Base64.getDecoder().decode(getEncodedImage().getBytes());
            ByteArrayInputStream imageInputStream = new ByteArrayInputStream(imageByte);

            bufferedImage = ImageIO.read(imageInputStream);
        } catch (IOException exception) {
            logger.error("Failed while trying to get image from camera.", exception);
        }

        return bufferedImage;
    }

    public void sendRadioSignal(String channel, byte[] bytesToSend) {
        Assert.notNull(bytesToSend, "BytesToSend must not be null.");
        Assert.isTrue(bytesToSend.length > 0, "BytesToSend must have some element.");

        logger.info("Sending radio signal for channel {}.", channel);
    }

    private String getEncodedImage() throws FileNotFoundException {
        File imageFile = new File("image.txt");
        Scanner reader = new Scanner(imageFile);
        String line = reader.nextLine();
        reader.close();

        return line;
    }
}
