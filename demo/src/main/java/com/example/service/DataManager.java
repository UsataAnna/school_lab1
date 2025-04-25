package com.example.service;

import com.example.model.School;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class DataManager {
    private final ObjectMapper mapper;

    public DataManager() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
   
    public void exportToJson(School school, String filePath) throws IOException {
        mapper.writeValue(new File(filePath), school);
    }

    public School importFromJson(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), School.class);
    }
}
