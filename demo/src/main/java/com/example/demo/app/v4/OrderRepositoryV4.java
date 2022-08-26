package com.example.demo.app.v4;

import com.example.demo.trace.TraceId;
import com.example.demo.trace.TraceStatus;
import com.example.demo.trace.logtrace.LogTrace;
import com.example.demo.trace.template.AbstractTemplate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class OrderRepositoryV4 {
    private final LogTrace trace;
    public void save(String itemId) {

        AbstractTemplate<Void> template = new AbstractTemplate(trace) {
            @Override
            protected Void call() {
                if ("ex".equals(itemId)) {
                    throw new IllegalStateException("예외 발생");
                }
                sleep();
                return null;
            }
        };

        template.execute("OrderRepository.save()");
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
