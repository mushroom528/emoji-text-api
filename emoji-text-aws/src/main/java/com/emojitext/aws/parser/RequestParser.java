package com.emojitext.aws.parser;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RequestParser {

    private final ObjectMapper objectMapper;

    public <T> T toObject(APIGatewayProxyRequestEvent request, Class<T> valueType) {
        String body = request.getBody();
        if (body == null || body.isEmpty()) {
            throw new IllegalArgumentException("Request body cannot be null or empty");
        }
        try {
            return objectMapper.readValue(body, valueType);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to parse request body: " + e.getMessage(), e);
        }
    }
}
