package com.emojitext.application.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class DefaultChatClientFactory implements ChatClientFactory {

    @Value("${spring.ai.openai.chat.options.model}")
    private String model;
    private final String systemPrompt;

    public DefaultChatClientFactory(@Value("classpath:prompt/system-prompt.txt") Resource systemPromptResource) {
        this.systemPrompt = loadSystemPrompt(systemPromptResource);
    }

    @Override
    public ChatClient create(String apiKey) {
        OpenAiApi openAiApi = new OpenAiApi(apiKey);
        OpenAiChatOptions options = OpenAiChatOptions.builder().withModel(model).build();
        log.info("api: {}", apiKey);

        return ChatClient.builder(new OpenAiChatModel(openAiApi, options))
                .defaultSystem(systemPrompt)
                .build();
    }

    private String loadSystemPrompt(Resource systemPromptResource) {
        try (Reader reader = new InputStreamReader(systemPromptResource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
