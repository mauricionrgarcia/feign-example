package com.example.feign.domain;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author mauricionrgarcia
 *
 */
@FeignClient(name = "foo-service",  url = "http://localhost:9099",  configuration=FeignPropagateBadRequestsConfiguration.class)
public interface FooFeignClient {

    @GetMapping("unauthorized")
    FooResponse getUnauthorized();

}
