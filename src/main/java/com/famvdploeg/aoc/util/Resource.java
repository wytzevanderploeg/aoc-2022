package com.famvdploeg.aoc.util;

import com.famvdploeg.aoc.exception.ApplicationException;
import io.reactivex.rxjava3.core.Flowable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Resource {

    public static void flowable(String resource, ResourceHandler resourceHandler) {
        try (InputStream is = ResourceReader.class.getResourceAsStream(resource)) {
            if (is == null) {
                throw new ApplicationException(String.format("Resource not found: [%s]", resource));
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                Flowable<String> lines$ = Flowable.fromStream(br.lines());
                resourceHandler.handle(lines$);
            }
        } catch (IOException ex) {
            throw new ApplicationException(String.format("Could not read resource: [%s]", resource));
        }
    }

    @FunctionalInterface
    public interface ResourceHandler {
        void handle(Flowable<String> lines$);
    }
}
