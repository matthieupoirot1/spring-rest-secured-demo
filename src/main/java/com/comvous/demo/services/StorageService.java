package com.comvous.demo.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile filename);

    Stream<Path> loadAll();

    Resource loadAsResource(String filename);

    void deleteAll();
}
