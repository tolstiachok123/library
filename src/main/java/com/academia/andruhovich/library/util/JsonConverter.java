package com.academia.andruhovich.library.util;

import com.academia.andruhovich.library.dto.OrderContentWrapper;
import com.academia.andruhovich.library.exception.InvalidJsonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.academia.andruhovich.library.exception.ErrorMessages.CANNOT_READ_JSON_VALUE;
import static com.academia.andruhovich.library.exception.ErrorMessages.CANNOT_CONVERT_TO_JSON;

@Slf4j
@Component
@Data
@RequiredArgsConstructor
public class JsonConverter {

    private final ObjectMapper objectMapper;

    @Named("jsonStringToCollection")
    public Set<OrderContentWrapper> jsonStringToCollection(String string) {
        try {
            JsonNode jsonNode = objectMapper.readTree(string);
            return objectMapper.convertValue(jsonNode, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error(String.format(CANNOT_READ_JSON_VALUE, string));
            throw new InvalidJsonException(String.format(CANNOT_READ_JSON_VALUE, string));
        }
    }

    @Named("collectionToJsonString")
    public String collectionToJsonString(Set<OrderContentWrapper> synchronisedOrderContentWrappers) {
        try {
            return objectMapper.writeValueAsString(synchronisedOrderContentWrappers);
        } catch (JsonProcessingException e) {
            log.error(String.format(CANNOT_CONVERT_TO_JSON, synchronisedOrderContentWrappers.toString()));
            throw new InvalidJsonException(String.format(CANNOT_CONVERT_TO_JSON, synchronisedOrderContentWrappers));
        }
    }
}
