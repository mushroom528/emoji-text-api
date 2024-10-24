package com.emojitext.aws.function.emoji;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.emojitext.application.ai.ChatService;
import com.emojitext.aws.parser.RequestParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@RequiredArgsConstructor
@Component
@Slf4j
public class EmojiAppender implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final RequestParser requestParser;
    private final ChatService chatService;

    @Override
    public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent request) {
        try {
            EmojiTextRequest body = requestParser.toObject(request, EmojiTextRequest.class);
            String result = chatService.addEmoji(body.text(), body.level());
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(result);
        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody(e.getMessage());
        }
    }
}
