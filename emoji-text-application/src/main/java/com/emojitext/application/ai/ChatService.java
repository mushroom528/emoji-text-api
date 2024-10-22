package com.emojitext.application.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient client;

    public String addEmoji(String text, String level) {
        var prompt = createPrompt(text, level);

        return client.prompt()
                .user(prompt)
                .call()
                .content();
    }

    private String createPrompt(String text, String level) {
        return """
                <query>
                %s
                </query>
                <level>
                %s
                </level>
                """.formatted(text, level);
    }
}
