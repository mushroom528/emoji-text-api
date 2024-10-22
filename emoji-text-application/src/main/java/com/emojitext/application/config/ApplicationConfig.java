package com.emojitext.application.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.emojitext.application")
@Import(OpenAIConfig.class)
public class ApplicationConfig {
}
