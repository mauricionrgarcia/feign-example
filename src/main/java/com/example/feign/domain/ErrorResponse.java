package com.example.feign.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author mauricionrgarcia
 *
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {

    private String error;

}
