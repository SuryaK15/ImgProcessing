package com.example.imgprocessing.service;

import java.awt.image.BufferedImage;

public class ColouredToGrayScale {
    public BufferedImage convert(BufferedImage coloredImage) {
        // Your grayscale conversion logic here
        int width = coloredImage.getWidth();
        int height = coloredImage.getHeight();
        int[] pixels = coloredImage.getRGB(0, 0, width, height, null, 0, width);

        for (int i = 0; i < pixels.length; i++) {
            int p = pixels[i];
            int a = (p >> 24) & 0xff;
            int r = (p >> 16) & 0xff;
            int g = (p >> 8) & 0xff;
            int b = p & 0xff;
            int avg = (r + g + b) / 3;
            p = (a << 24) | (avg << 16) | (avg << 8) | avg;
            pixels[i] = p;
        }
        coloredImage.setRGB(0, 0, width, height, pixels, 0, width);
        
        return coloredImage; // Replace with actual converted image
    }
}
