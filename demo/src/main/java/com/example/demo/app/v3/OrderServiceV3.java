package com.example.demo.app.v3;

import com.example.demo.trace.TraceId;
import com.example.demo.trace.TraceStatus;
import com.example.demo.trace.hellotrace.HelloTraceV2;
import com.example.demo.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {
    public final OrderRepositoryV3 orderRepository;
    public final LogTrace trace;

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderService.orderItem");
            orderRepository.save(status.getTraceId(), itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }

    }
}
