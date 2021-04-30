package com.example.feign.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author mauricionrgarcia
 *
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class FooResponse {

    private String id;
    private String name;

}
