package com.example.demo.app.v5;

import com.example.demo.trace.callback.TraceTemplate;
import com.example.demo.trace.logtrace.LogTrace;
import com.example.demo.trace.template.AbstractTemplate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV5 {
    public final TraceTemplate template;
    public OrderRepositoryV5(LogTrace trace) {
        this.template = new TraceTemplate(trace);
    }
    public void save(String itemId) {

        template.execute("OrderRepository.save()", ()-> {
            if ("ex".equals(itemId)) {
                throw new IllegalStateException("예외 발생!");
            }
            sleep();
            return null;
        });
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
