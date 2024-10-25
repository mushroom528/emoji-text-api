package com.emojitext.application.ai;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

class ChatClientManagerTest {

    private static final int NUMBER_OF_USERS = 10;
    private final ChatClientManager sut = new ChatClientManager(new SimpleChatClientFactory(), getTestApiKeys());

    @Test
    @DisplayName("유저들은 서로 다른 키를 사용해야한다.")
    void differentUsersShouldUseDistinctApiKeys() throws InterruptedException {
        // given
        CountDownLatch latch = new CountDownLatch(NUMBER_OF_USERS);
        Set<SimpleChatClient> result = ConcurrentHashMap.newKeySet();

        // when
        try (ExecutorService es = Executors.newFixedThreadPool(NUMBER_OF_USERS)) {
            for (int i = 0; i < NUMBER_OF_USERS; i++) {
                es.submit(() -> {
                    ChatClient chatClient = sut.getNextClient();
                    result.add((SimpleChatClient) chatClient);
                    latch.countDown();
                });
            }
        }
        latch.await();

        // then
        List<String> keys = result.stream().map(rs -> rs.apiKey).toList();
        assertThat(keys).hasSize(NUMBER_OF_USERS);
    }

    private static List<String> getTestApiKeys() {
        return List.of("A", "B", "C", "D", "E", "F", "G", "H", "J", "K");
    }

    static class SimpleChatClientFactory implements ChatClientFactory {

        @Override
        public ChatClient create(String apiKey) {
            return SimpleChatClient.create(apiKey);
        }
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    static class SimpleChatClient implements ChatClient {

        private String apiKey;

        public static SimpleChatClient create(String apiKey) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return new SimpleChatClient(apiKey);
        }

        @Override
        public ChatClientRequestSpec prompt() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChatClientRequestSpec prompt(String content) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ChatClientRequestSpec prompt(Prompt prompt) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Builder mutate() {
            throw new UnsupportedOperationException();
        }
    }
}
