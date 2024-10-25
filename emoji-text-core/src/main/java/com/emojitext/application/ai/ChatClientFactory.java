package com.emojitext.application.ai;

import org.springframework.ai.chat.client.ChatClient;

public interface ChatClientFactory {

    ChatClient create(String apiKey);
}
