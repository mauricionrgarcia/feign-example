package com.example.feign;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import com.example.FeignExampleApplication;
import com.example.feign.Infrastructure.FeignBadResponseWrapper;
import com.example.feign.domain.ErrorResponse;
import com.example.feign.domain.FooFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

/**
 * @author mauricionrgarcia
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = { WireMockInitializer.class }, classes = { FeignExampleApplication.class })
@AutoConfigureMockMvc
public class FooFeignClientTest {

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private FooFeignClient fooFeignClient;

    @AfterEach
    public void afterEach() {
        this.wireMockServer.resetAll();
    }

    @Test
    public void testResponseUnauthorized() throws Exception {
        ErrorResponse response = ErrorResponse.builder()
                .error("401 - unauthorized")
                .build();
        wireMockServer.stubFor(get(WireMock.urlEqualTo("/unauthorized"))
                .willReturn(aResponse().withStatus(HttpStatus.UNAUTHORIZED.value())
                        .withHeader("content-type", "application/json")
                        .withBody(new ObjectMapper().writeValueAsBytes(response))));


        FeignBadResponseWrapper error = assertThrows(FeignBadResponseWrapper.class,() -> fooFeignClient.getUnauthorized());
        ErrorResponse resultError = new ObjectMapper().readValue(error.getBody(), ErrorResponse.class);
        assertThat(response).isEqualTo(resultError);
    }

}