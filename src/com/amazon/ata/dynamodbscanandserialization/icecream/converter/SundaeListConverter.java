package com.amazon.ata.dynamodbscanandserialization.icecream.converter;

import com.amazon.ata.dynamodbscanandserialization.icecream.exception.SundaeSerializationException;
import com.amazon.ata.dynamodbscanandserialization.icecream.model.Sundae;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;

import static org.junit.platform.commons.util.StringUtils.isBlank;


public class SundaeListConverter implements DynamoDBTypeConverter<String, List<Sundae>> {


    private final ObjectMapper mapper;
    public SundaeListConverter() {
        mapper = new ObjectMapper();

    }
    @Override
    public String convert(List<Sundae> sundaes) {
        if (sundaes == null) {
            return "";
        }
        try {
            return mapper.writeValueAsString(sundaes);
        } catch (Exception e) {
            throw new SundaeSerializationException("Error converting Sundae list to JSON", e);
        }
    }

    @Override
    public List<Sundae> unconvert(String s) {
        if (isBlank(s)) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(s, new TypeReference<List<Sundae>>() {});
        } catch (IOException e) {
            throw new SundaeSerializationException("Error converting JSON to Sundae list", e);
        }
    }
}

