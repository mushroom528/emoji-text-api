package com.emojitext.rest.config;

import com.emojitext.application.config.ApplicationConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ApplicationConfig.class)
public class WebAppConfig {
}
