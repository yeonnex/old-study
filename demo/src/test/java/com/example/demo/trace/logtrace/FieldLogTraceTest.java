package com.example.demo.trace.logtrace;

import com.example.demo.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class FieldLogTraceTest {
    FieldLogTrace trace = new FieldLogTrace();

    @Test
    void begin_end_level(){
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_exception() {
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }

}