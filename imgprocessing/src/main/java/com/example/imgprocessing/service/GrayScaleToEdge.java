package com.example.imgprocessing.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class GrayScaleToEdge {
   public BufferedImage detectEdges(BufferedImage grayImage) throws IOException {
        if (grayImage == null) {
            throw new IllegalArgumentException("Image cannot be null.");
        }

        // Apply Gaussian blur to the grayscale image
        BufferedImage blurredImg = applyGaussianBlur(grayImage);

        // Apply Sobel operator for edge detection
        BufferedImage edgeImg = applySobelOperator(blurredImg);

        return edgeImg;
    }

    // Apply Gaussian blur to the input grayscale image
    private BufferedImage applyGaussianBlur(BufferedImage img) {
        if (img == null) {
            throw new IllegalArgumentException("Input image cannot be null.");
        }

        // Implementation of Gaussian blur kernel
        float[][] kernel = {
            {1 / 16f, 2 / 16f, 1 / 16f}, 
            {2 / 16f, 4 / 16f, 2 / 16f}, 
            {1 / 16f, 2 / 16f, 1 / 16f}
        };
        return applyConvolution(img, kernel);
    }

    // Apply Sobel operator for edge detection on the input image
    private BufferedImage applySobelOperator(BufferedImage img) {
        if (img == null) {
            throw new IllegalArgumentException("Input image cannot be null.");
        }

        // Sobel operator kernels for X and Y directions
        int[][] sobelX = {
            {-1, 0, 1}, 
            {-2, 0, 2}, 
            {-1, 0, 1}
        };
        int[][] sobelY = {
            {-1, -2, -1}, 
            {0, 0, 0}, 
            {1, 2, 1}
        };

        // Create a new BufferedImage for the edge-detected image
        BufferedImage edgeImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        // Loop through each pixel in the image to apply the Sobel operator
        for (int y = 1; y < img.getHeight() - 1; y++) {
            for (int x = 1; x < img.getWidth() - 1; x++) {
                int gx = 0, gy = 0;

                // Apply the Sobel operator masks for X and Y directions
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        int pixel = img.getRGB(x + i, y + j);
                        int gray = (pixel >> 16) & 0xFF; // Extract red component for grayscale
                        gx += gray * sobelX[j + 1][i + 1];
                        gy += gray * sobelY[j + 1][i + 1];
                    }
                }

                // Compute the magnitude of the gradient
                int magnitude = (int) Math.sqrt(gx * gx + gy * gy);
                magnitude = Math.min(255, Math.max(0, magnitude)); // Ensure magnitude is in [0, 255]
                int edgePixel = (magnitude << 16) | (magnitude << 8) | magnitude; // Grayscale edge pixel
                edgeImg.setRGB(x, y, edgePixel);
            }
        }

        return edgeImg;
    }

    // Apply convolution operation with the specified kernel to the input image
    private BufferedImage applyConvolution(BufferedImage img, float[][] kernel) {
        if (img == null || kernel == null) {
            throw new IllegalArgumentException("Input image or kernel cannot be null.");
        }

        BufferedImage resultImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        int kernelSize = kernel.length;
        int kernelCenter = kernelSize / 2;

        // Loop through each pixel in the image to apply convolution
        for (int y = kernelCenter; y < img.getHeight() - kernelCenter; y++) {
            for (int x = kernelCenter; x < img.getWidth() - kernelCenter; x++) {
                float sum = 0;

                // Apply the convolution kernel to the neighborhood of the pixel
                for (int j = 0; j < kernelSize; j++) {
                    for (int i = 0; i < kernelSize; i++) {
                        int pixel = img.getRGB(x + i - kernelCenter, y + j - kernelCenter);
                        int gray = (pixel >> 16) & 0xFF; // Extract red component for grayscale
                        sum += gray * kernel[j][i];
                    }
                }

                // Clamp the result to [0, 255] and set the pixel value in the result image
                int resultPixel = Math.min(255, Math.max(0, Math.round(sum)));
                resultImg.setRGB(x, y, (resultPixel << 16) | (resultPixel << 8) | resultPixel);
            }
        }

        return resultImg;
    }
}
