package com.example.demo.app.v3;

import com.example.demo.trace.TraceStatus;
import com.example.demo.trace.hellotrace.HelloTraceV2;
import com.example.demo.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {
    private final LogTrace trace;
    private final OrderServiceV3 orderService;

    @GetMapping("/v3/order")
    public String request(@RequestParam("itemId")String itemId) {
        TraceStatus status = null;

        try {
            status = trace.begin("OrderController.request");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
