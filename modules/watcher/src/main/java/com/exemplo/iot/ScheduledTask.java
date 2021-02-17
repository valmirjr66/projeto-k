package com.exemplo.iot;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import boofcv.abst.scene.ImageClassifier;
import boofcv.factory.scene.ClassifierAndSource;
import boofcv.factory.scene.FactoryImageClassifier;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.Planar;
import deepboof.io.DeepBoofDataBaseOps;

@Service
public class ScheduledTask {
    @Scheduled(fixedDelay = 300000)
    public void task() throws IOException {
        ClassifierAndSource classifierSource = FactoryImageClassifier.nin_imagenet();

        File modelPath = DeepBoofDataBaseOps.downloadModel(classifierSource.getSource(), new File("/models"));

        ImageClassifier<Planar<GrayF32>> classifier = classifierSource.getClassifier();
        classifier.loadModel(modelPath);

        byte[] imageByte = Base64.getDecoder().decode(getEncodedImage().getBytes());
        ByteArrayInputStream imageInputStream = new ByteArrayInputStream(imageByte);
        BufferedImage bufferedImage = ImageIO.read(imageInputStream);

        Planar<GrayF32> resultImage = new Planar<>(GrayF32.class, bufferedImage.getWidth(), bufferedImage.getHeight(),
                3);
        ConvertBufferedImage.convertFromPlanar(bufferedImage, resultImage, true, GrayF32.class);

        classifier.classify(resultImage);
    }

    private String getEncodedImage() throws FileNotFoundException {
        File imageFile = new File("image.txt");
        Scanner reader = new Scanner(imageFile);
        String line = reader.nextLine();
        reader.close();

        return line;
    }
}
