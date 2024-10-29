package com.emojitext.application.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatClient client;

    public String addEmoji(String text, String level) {
        log.info("addEmoji: text={}, level={}", text, level);
        try {
            var prompt = createPrompt(text, level);
            return doChat(client, prompt);
        } catch (NonTransientAiException e) {
            if (e.getMessage().contains("429")) {
                log.warn("Rate limit exceeded.");
                return recover(e, text, level);
            }
            throw e;
        }
    }

    private String doChat(ChatClient client, String prompt) {
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

    public String recover(NonTransientAiException e, String text, String level) {
        log.error("All retry attempts failed for text: {}, level: {}, error: {}",
                text, level, e.getMessage());
        return switch (level.toLowerCase()) {
            case "small" -> "Sorry, please try again later 😢";
            case "medium" -> "Server is busy, please try again later 😢 🙏";
            case "large" -> "Server is currently experiencing heavy traffic, please try again later 😢 🙏 ❤️";
            default -> "Please try again later 😢";
        };
    }
}