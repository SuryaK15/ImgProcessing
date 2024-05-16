package com.example.imgprocessing.service;

import org.springframework.stereotype.Service;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageProcessingService {
    private final ColouredToGrayScale colouredToGrayScale;
    private final GrayScaleToEdge grayScaleToEdge;

    public ImageProcessingService() {
        this.colouredToGrayScale = new ColouredToGrayScale();
        this.grayScaleToEdge = new GrayScaleToEdge();
    }

    public byte[] convertToGrayScale(byte[] imageBytes) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        BufferedImage grayImage = colouredToGrayScale.convert(image);
        return bufferedImageToByteArray(grayImage);
    }

    public byte[] detectEdges(byte[] imageBytes) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        BufferedImage edgeImage = grayScaleToEdge.detectEdges(image);
        return bufferedImageToByteArray(edgeImage);
    }

    private byte[] bufferedImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
