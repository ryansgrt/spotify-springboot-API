package com.enigma.sepotipi.utility;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileReadUtilImpl implements FileReadUtil{
    private final Path storageLocation = Paths.get("").toAbsolutePath().normalize();

    @Override
    public Resource read(String file) throws MalformedURLException {
        String exceptionMessage = String.format("File %s not found", file);
        Path fileName = storageLocation.resolve(file).normalize();
        Resource resource = new UrlResource(fileName.toUri());

        if(!resource.exists()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, exceptionMessage);
        return resource;
    }
}
