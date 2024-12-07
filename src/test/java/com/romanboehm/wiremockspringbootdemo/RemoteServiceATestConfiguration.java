package com.romanboehm.wiremockspringbootdemo;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.internal.WireMockServerCreator;

@TestConfiguration
class RemoteServiceATestConfiguration {

    @Bean("wireMockServerA")
    WireMockServer wireMockServerA(ConfigurableApplicationContext ctx) {
        @ConfigureWireMock(name = "wiremock-server-a", baseUrlProperties = "remote-service-a-baseurl")
        record ConfigureWireMockHolder() {}

        return new WireMockServerCreator("wiremock-server-a").createWireMockServer(ctx, ConfigureWireMockHolder.class.getAnnotation(ConfigureWireMock.class));
    }

}
