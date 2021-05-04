package com.shop.model.service.impl;

import com.shop.model.domain.Activation;
import com.shop.model.service.ActivationService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.nio.file.Files;

@Service
public class ActivationServiceImpl implements ActivationService {
    private static final String FILE_PATH = "src\\main\\resources\\activation.txt";
    public static final String FILE_NOT_INIT = "File not init";
    public static final String FILE_NOT_DESTROY = "File not destroy";
    public static final String NOT_ACTIVATION_FILE = "Not Activation File";

    @Override
    @PostConstruct
    public void createFirstData() {
        try {
            File file = new File(FILE_PATH);
            Files.write(file.toPath(), Activation.NONACTIVE.toString().getBytes());
        } catch (Exception e) {
            throw new RuntimeException(FILE_NOT_INIT + FILE_PATH);
        }
    }

    @Override
    public Activation getActivation() {
        try {
            File file = new File(FILE_PATH);
            String activation = String.valueOf(new String(Files.readAllBytes(file.toPath())));
            return Activation.valueOf(activation);
        } catch (Exception e) {
            throw new RuntimeException(NOT_ACTIVATION_FILE + FILE_PATH);
        }
    }

    @Override
    public void setActivation(Activation activation) {
        try {
            File file = new File(FILE_PATH);
            Files.write(file.toPath(), activation.toString().getBytes());
        } catch (Exception e) {
            throw new RuntimeException(FILE_NOT_INIT + FILE_PATH);
        }
    }

    @PreDestroy
    @Override
    public void destroy() {
        try {
            File file = new File(FILE_PATH);
            Files.deleteIfExists(file.toPath());
        } catch (Exception e) {
            throw new RuntimeException(FILE_NOT_DESTROY + FILE_PATH);
        }
    }
}
