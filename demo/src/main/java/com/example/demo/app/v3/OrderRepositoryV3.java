package com.example.demo.app.v3;

import com.example.demo.trace.TraceId;
import com.example.demo.trace.TraceStatus;
import com.example.demo.trace.hellotrace.HelloTraceV2;
import com.example.demo.trace.logtrace.LogTrace;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class OrderRepositoryV3 {
    private final LogTrace trace;
    public void save(TraceId traceId, String itemId) {
        TraceStatus status = null;

        try {
            status = trace.begin("OrderRepository.save");
            if ("ex".equals(itemId)) {
                throw new IllegalStateException("예외 발생");
            }
            //sleep();
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
