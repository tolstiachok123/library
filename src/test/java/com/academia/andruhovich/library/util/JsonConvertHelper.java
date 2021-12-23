package com.academia.andruhovich.library.util;

import com.academia.andruhovich.library.dto.AuthResponseDto;
import com.academia.andruhovich.library.dto.AuthorDto;
import com.academia.andruhovich.library.dto.BookDto;
import com.academia.andruhovich.library.dto.OrderResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.io.IOException;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import static com.academia.andruhovich.library.exception.ErrorMessages.CANNOT_READ_JSON_VALUE;
import static com.academia.andruhovich.library.util.DateHelper.CURRENT_LOCATION;

@Data
public class JsonConvertHelper {

    private static final String CONTENT = "content";

    private final static ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setTimeZone(TimeZone.getTimeZone(ZoneId.of(CURRENT_LOCATION)))
            .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)
            .configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static String convertToJsonString(final Object obj) throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);
    }

    public static AuthResponseDto getAuthResponseDto(String response) {
        AuthResponseDto authResponseDto;
        try {
            JsonNode responseBody = objectMapper.readTree(response);
            authResponseDto = objectMapper.convertValue(responseBody, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format(CANNOT_READ_JSON_VALUE, response));
        }
        return authResponseDto;
    }

    public static AuthorDto getAuthorDto(String response) {
        AuthorDto authorDto;
        try {
            JsonNode responseBody = objectMapper.readTree(response);
            authorDto = objectMapper.convertValue(responseBody, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format(CANNOT_READ_JSON_VALUE, response));
        }
        return authorDto;
    }

    public static List<AuthorDto> getAuthorDtos(String response) {
        List<AuthorDto> authors;
        try {
            JsonNode responseBody = objectMapper.readTree(response);
            authors = objectMapper.convertValue(responseBody, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format(CANNOT_READ_JSON_VALUE, response));
        }
        return authors;
    }

    public static BookDto getBookDto(String response) {
        BookDto bookDto;
        try {
            JsonNode responseBody = objectMapper.readTree(response);
            bookDto = objectMapper.convertValue(responseBody, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format(CANNOT_READ_JSON_VALUE, response));
        }
        return bookDto;
    }

    public static List<BookDto> getBookDtos(String response) {
        List<BookDto> books;
        try {
            JsonNode responseBody = objectMapper.readTree(response);
            books = objectMapper.convertValue(responseBody, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format(CANNOT_READ_JSON_VALUE, response));
        }
        return books;
    }

    public static OrderResponseDto getOrderResponseDto(String response) {
        OrderResponseDto orderResponseDto;
        try {
            JsonNode responseBody = objectMapper.readTree(response);
            orderResponseDto = objectMapper.convertValue(responseBody, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format(CANNOT_READ_JSON_VALUE, response));
        }
        return orderResponseDto;
    }

    public static Set<OrderResponseDto> getOrderResponseDtos(String response) {
        Set<OrderResponseDto> orders;
        try {
            JsonNode responseBody = objectMapper.readTree(response);
            orders = objectMapper.convertValue(responseBody, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format(CANNOT_READ_JSON_VALUE, response));
        }
        return orders;
    }
}
