package com.moghoneim.roomily.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    // Default RestTemplate
    @Bean
    public RestTemplate defaultRestTemplate() {
        return createRestTemplate(3000, 3000);  // Default timeout of 3000ms for both connect and read
    }

    // Utility method to create RestTemplate with custom timeouts
    public RestTemplate createRestTemplate(int connectTimeout, int readTimeout) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        return new RestTemplate(requestFactory);
    }
}
