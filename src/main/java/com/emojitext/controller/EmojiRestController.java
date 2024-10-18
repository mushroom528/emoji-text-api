package com.emojitext.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmojiRestController {

    private final ChatClient client;

    @GetMapping("/api/chat")
    public String emoji(@RequestParam String text) {
        var prompt = """
                text: %s
                emoji level: large
                """.formatted(text);
        
        return client.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
