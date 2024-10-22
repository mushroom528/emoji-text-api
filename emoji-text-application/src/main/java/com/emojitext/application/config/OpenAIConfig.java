package com.emojitext.application.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Configuration
public class OpenAIConfig {

    @Value("classpath:prompt/system-prompt.txt")
    private Resource systemPrompt;

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        var system = loadSystemPrompt();
        return builder
                .defaultSystem(system)
                .build();
    }

    private String loadSystemPrompt() {
        try (Reader reader = new InputStreamReader(systemPrompt.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
