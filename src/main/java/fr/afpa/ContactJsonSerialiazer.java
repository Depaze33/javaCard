package fr.afpa;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ContactJsonSerialiazer implements Serializer<Contact> {

    @Override
    public void saveList(String filePath, ArrayList<Contact> objectToSave) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // writerWithDefaultPrettyPrinter add indentation & breaklines
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), objectToSave);
    }

    @Override
    public void save(String filePath, Contact objectToSave) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // writerWithDefaultPrettyPrinter add indentation & breaklines
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), objectToSave);
    }

}
