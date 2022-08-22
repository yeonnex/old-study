package com.example.demo.app.v2;

import com.example.demo.trace.TraceStatus;
import com.example.demo.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {
    private final HelloTraceV2 trace;
    private final OrderServiceV2 orderService;

    @GetMapping("/v2/order")
    public String request(@RequestParam("itemId")String itemId) {
        TraceStatus status = null;

        try {
            status = trace.begin("OrderController.request");
            orderService.orderItem(status.getTraceId(), itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
