package com.example.feign.domain;

import static org.apache.commons.lang3.StringUtils.join;

import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.example.feign.Infrastructure.FeignBadResponseWrapper;

import feign.codec.ErrorDecoder;

/**
 * @author mauricionrgarcia
 *
 */
@Component
public class FeignPropagateBadRequestsConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            int status = response.status();
            if (status > 400) {
                String body = "Bad request";
                try {
                    body = IOUtils.toString(response.body().asReader(Charset.defaultCharset()));
                } catch (Exception ignored) {
                }
                HttpHeaders httpHeaders = new HttpHeaders();
                response.headers().forEach((k, v) -> httpHeaders.add("feign-" + k, join(v, ",")));
                return new FeignBadResponseWrapper(status, httpHeaders, body);
            } else {
                return new RuntimeException("Response Code " + status);
            }
        };
    }
}
