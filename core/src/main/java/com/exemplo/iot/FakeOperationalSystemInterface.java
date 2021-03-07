package com.exemplo.iot;

import static java.util.stream.Collectors.toList;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
            List<String> fileNamesList = Arrays.asList(new File("/images").list());
            String fileName = MessageFormat.format("/images/{0}", getLastFileName(fileNamesList));
            System.out.println(fileName);
            File imageFile = new File(fileName);
            bufferedImage = ImageIO.read(imageFile);
        } catch (IOException exception) {
            logger.error("Failed while trying to get image from camera.", exception);
        }

        return bufferedImage;
    }

    private String getLastFileName(List<String> fileNamesList) {
        List<String> filteredFileNamesList = new ArrayList<>();
        filteredFileNamesList = fileNamesList.stream().filter(x -> x.matches("^image\\d{1,}\\.(png|jpg|jpeg)$"))
                .collect(toList());
        Collections.sort(filteredFileNamesList);

        return filteredFileNamesList.get(filteredFileNamesList.size() - 1);
    }

    public void sendRadioSignal(String channel, byte[] bytesToSend) {
        Assert.notNull(bytesToSend, "BytesToSend must not be null.");
        Assert.isTrue(bytesToSend.length > 0, "BytesToSend must have some element.");

        logger.info("Sending radio signal for channel {}.", channel);
    }
}
