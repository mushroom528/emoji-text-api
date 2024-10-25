package com.emojitext.application.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ChatClientManager {

    private final List<ChatClient> clients = new ArrayList<>();
    private final AtomicInteger currentIndex = new AtomicInteger(0);
    private final int poolSize;

    public ChatClientManager(ChatClientFactory chatClientFactory, @Value("${openai.api-key}") List<String> apiKeys) {
        this.poolSize = apiKeys.size();
        apiKeys.forEach(apiKey -> clients.add(chatClientFactory.create(apiKey)));
    }

    public ChatClient getNextClient() {
        int index = currentIndex.getAndIncrement() % poolSize;
        return clients.get(index);
    }
}
