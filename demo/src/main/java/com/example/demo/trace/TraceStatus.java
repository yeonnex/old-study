package com.example.demo.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TraceStatus {
    private TraceId traceId;
    private long starTimeMs;
    private String message;
}
