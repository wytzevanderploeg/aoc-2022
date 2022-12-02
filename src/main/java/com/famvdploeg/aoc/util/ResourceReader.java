package com.famvdploeg.aoc.util;

import com.famvdploeg.aoc.exception.ApplicationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ResourceReader {

    public static List<String> readResource(String resource) {
        List<String> result = new ArrayList<>();

        try (InputStream is = ResourceReader.class.getResourceAsStream(resource)) {
            if (is == null) {
                throw new ApplicationException(String.format("Resource not found: [%s]", resource));
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = br.readLine()) != null) {
                    result.add(line);
                }
            }
        } catch (IOException ex) {
            throw new ApplicationException(String.format("Could not read resource: [%s]", resource));
        }

        return result;
    }

}
