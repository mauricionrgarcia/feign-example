package com.example.feign.Infrastructure;

import org.springframework.http.HttpHeaders;

import lombok.Getter;

/**
 * @author mauricionrgarcia
 *
 */
@Getter
public class FeignBadResponseWrapper extends RuntimeException {


    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7839931669015202350L;

    private final int status;
    private final HttpHeaders headers;
    private final String body;

    public FeignBadResponseWrapper(int status, HttpHeaders headers, String body) {
        super("Bad request");
        this.status = status;
        this.headers = headers;
        this.body = body;
    }


}
