package com.enigma.sepotipi.utility;

import org.springframework.core.io.Resource;

import java.net.MalformedURLException;

public interface FileReadUtil {

    public Resource read(String file) throws MalformedURLException;
}
