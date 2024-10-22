package com.emojitext.rest.api;

import com.emojitext.application.ai.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmojiRestController {

    private final ChatService chatService;

    @PostMapping("/api/chat")
    public String emoji(@RequestBody EmojiTextRequest request) {
        return chatService.addEmoji(request.text(), request.level());
    }
}
