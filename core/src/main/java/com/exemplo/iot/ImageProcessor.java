package com.exemplo.iot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import boofcv.abst.scene.ImageClassifier;
import boofcv.factory.scene.ClassifierAndSource;
import boofcv.factory.scene.FactoryImageClassifier;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.Planar;
import deepboof.io.DeepBoofDataBaseOps;

@Component
public class ImageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ImageProcessor.class);

    public String classifyImage(BufferedImage imageToProcess) {
        Planar<GrayF32> resultImage = new Planar<>(GrayF32.class, imageToProcess.getWidth(), imageToProcess.getHeight(),
                3);
        ConvertBufferedImage.convertFromPlanar(imageToProcess, resultImage, true, GrayF32.class);

        ImageClassifier<Planar<GrayF32>> classifier = getClassifier();
        classifier.classify(resultImage);

        String bestCategoryResult = classifier.getCategories().get(classifier.getBestResult());

        return bestCategoryResult;
    }

    private ImageClassifier<Planar<GrayF32>> getClassifier() {
        ClassifierAndSource classifierSource = FactoryImageClassifier.nin_imagenet();

        logger.info("Downloading image classification model.");

        File modelPath = DeepBoofDataBaseOps.downloadModel(classifierSource.getSource(),
                new File("/models/trained_model"));

        ImageClassifier<Planar<GrayF32>> classifier = classifierSource.getClassifier();

        try {
            classifier.loadModel(modelPath);
        } catch (IOException exception) {
            logger.error("Error while loading model for classification.", exception);
        }

        return classifier;
    }
}
