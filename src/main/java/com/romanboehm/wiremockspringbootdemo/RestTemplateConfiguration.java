package com.romanboehm.wiremockspringbootdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestTemplateConfiguration {

    @Bean("restTemplateForRemoteServiceA")
    @Lazy
    RestTemplate restTemplateForRemoteServiceA(@Value("${remote-service-a-endpoint}") String remoteServiceAEndpoint, RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                            .rootUri(remoteServiceAEndpoint)
                            .build();
    }

    @Bean("restTemplateForRemoteServiceB")
    RestTemplate restTemplateForRemoteServiceB(@Value("${remote-service-b-endpoint}") String remoteServiceBEndpoint, RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                            .rootUri(remoteServiceBEndpoint)
                            .build();
    }
}
