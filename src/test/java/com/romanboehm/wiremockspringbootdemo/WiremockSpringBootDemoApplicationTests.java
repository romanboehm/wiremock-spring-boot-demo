package com.romanboehm.wiremockspringbootdemo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({ RemoteServiceATestConfiguration.class, RemoteServiceBTestConfiguration.class })
class WiremockSpringBootDemoApplicationTests {

    @Autowired
    @Qualifier("wireMockServerA")
    WireMockServer wireMockServerA;
    @Autowired
    @Qualifier("restTemplateForRemoteServiceA")
    RestTemplate restTemplateForRemoteServiceA;

    @Autowired
    @Qualifier("wireMockServerB")
    WireMockServer wireMockServerB;
    @Autowired
    @Qualifier("restTemplateForRemoteServiceB")
    RestTemplate restTemplateForRemoteServiceB;

    @Test
    void stubsA() {
        wireMockServerA.stubFor(WireMock.get("/a/hello").willReturn(WireMock.ok("Hello from service A")));

        assertThat(restTemplateForRemoteServiceA.getForEntity("/hello", String.class)).satisfies(res -> {
            assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(res.getBody()).isEqualTo("Hello from service A");
        });
    }

    @Test
    void stubsB() {
        wireMockServerB.stubFor(WireMock.get("/b/hello").willReturn(WireMock.ok("Hello from service B")));

        assertThat(restTemplateForRemoteServiceB.getForEntity("/hello", String.class)).satisfies(res -> {
            assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(res.getBody()).isEqualTo("Hello from service B");
        });
    }

}
