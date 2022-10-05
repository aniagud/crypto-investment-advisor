package com.crypto.cryptoinvestmentadvisor.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class FileReader {

    private final String dataBaseDir;


    public List<String> readFile(String fileName) {

        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(dataBaseDir + "/" + fileName));
            List<String> data = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
            return data;
        } catch (Exception e) {
            log.error("Could not read file: {}", fileName, e);
            return Collections.emptyList();
        }
    }

    public Set<String> findAllDataFiles() {
        return Stream.of(new File(dataBaseDir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }
}
