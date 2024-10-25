package com.emojitext.application.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@ComponentScan(basePackages = "com.emojitext.application")
@Import(OpenAIConfig.class)
@EnableRetry
public class ApplicationConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 날짜/시간 처리를 위한 모듈 등록
        objectMapper.registerModule(new JavaTimeModule());

        // Serialization 설정
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        // Deserialization 설정
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);

        // NULL 처리 설정
        objectMapper.setDefaultPropertyInclusion(
                com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);

        return objectMapper;
    }
}
