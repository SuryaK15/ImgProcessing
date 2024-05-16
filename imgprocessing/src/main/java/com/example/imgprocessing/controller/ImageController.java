package com.example.imgprocessing.controller;

import com.example.imgprocessing.service.ImageProcessingService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    @Autowired
    private ImageProcessingService imageProcessingService;

    @PostMapping("/grayscale")
    public void convertToGrayScale(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        byte[] grayImage = imageProcessingService.convertToGrayScale(file.getBytes());
        response.setContentType("image/png");
        response.getOutputStream().write(grayImage);
    }

    @PostMapping("/edge-detection")
    public void detectEdges(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        byte[] edgeImage = imageProcessingService.detectEdges(file.getBytes());
        response.setContentType("image/png");
        response.getOutputStream().write(edgeImage);
    }
}
