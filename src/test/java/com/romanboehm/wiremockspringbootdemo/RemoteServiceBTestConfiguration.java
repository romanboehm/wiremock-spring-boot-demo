package com.romanboehm.wiremockspringbootdemo;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.internal.WireMockServerCreator;

@TestConfiguration
class RemoteServiceBTestConfiguration {

    @Bean("wireMockServerB")
    WireMockServer wireMockServerB(ConfigurableApplicationContext ctx) {
        @ConfigureWireMock(name = "wiremock-server-b", baseUrlProperties = "remote-service-b-baseurl")
        record ConfigureWireMockHolder() {}

        return new WireMockServerCreator("wiremock-server-b").createWireMockServer(ctx, ConfigureWireMockHolder.class.getAnnotation(ConfigureWireMock.class));
    }

    @Bean
    DynamicPropertyRegistrar remoteServiceBBaseUrlDynamicPropertyRegistrar(@Qualifier("wireMockServerB") WireMockServer wireMockServerB) {
        return registry -> {
            registry.add("remote-service-b-baseurl", wireMockServerB::baseUrl);
        };
    }

}
