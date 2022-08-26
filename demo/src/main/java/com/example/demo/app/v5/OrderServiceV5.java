package com.example.demo.app.v5;

import com.example.demo.app.v1.OrderRepositoryV1;
import com.example.demo.trace.callback.TraceTemplate;
import com.example.demo.trace.logtrace.LogTrace;
import com.example.demo.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {
    public final OrderRepositoryV5 orderRepository;
    public final TraceTemplate template;
    public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace trace) {
        this.orderRepository = orderRepository;
        this.template = new TraceTemplate(trace);
    }
    public void orderItem(String itemId) {
        template.execute("OrderService.orderItem()", ()-> {
            orderRepository.save(itemId);
            return null;
        });

    }

}
