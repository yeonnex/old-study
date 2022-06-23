package com.example.logtracer.app.v1;

import com.example.logtracer.trace.TraceStatus;
import com.example.logtracer.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {
    private final OrderServiceV1 orderService;
    private final HelloTraceV1 trace;

    @GetMapping("/v1/request")
    public String request(String itemId){
        TraceStatus status = null;
        try {
            status = trace.begin("OrderConroller.reequest()");
            orderService.orderItem(itemId);
            trace.end(status);
        } catch (Exception e){
            trace.exception(status,e);
            throw e; // 예외를 꼭 다시 던져주어야 한다.
        }
        return "ok";
    }
}
