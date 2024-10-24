package com.emojitext.aws.function.emoji;

import com.emojitext.application.ai.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmojiAppender implements Function<EmojiAppenderRequest, String> {

    private final ChatService chatService;

    @Override
    public String apply(EmojiAppenderRequest request) {
        return chatService.addEmoji(request.text(), request.level());
    }
}
