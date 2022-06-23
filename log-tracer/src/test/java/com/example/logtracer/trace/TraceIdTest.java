package com.example.logtracer.trace;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TraceIdTest {

    @Test
    @DisplayName("TraceId 생성")
    void create(){
        TraceId traceId = new TraceId();
        String id = traceId.getId();
        Assertions.assertEquals(id.length(), 8);

        TraceId nextId = traceId.createNextId();
        assertEquals(nextId.getLevel(), traceId.getLevel() + 1);

        TraceId previousId = traceId.createPreviousId();
        assertEquals(previousId.getLevel(), traceId.getLevel() - 1);

    }
}