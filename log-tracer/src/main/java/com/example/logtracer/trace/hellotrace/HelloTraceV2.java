package com.example.logtracer.trace.hellotrace;

import com.example.logtracer.trace.TraceId;
import com.example.logtracer.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HelloTraceV2 {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X--";

    public TraceStatus begin(String message) {
        TraceId traceId = new TraceId();
        long startTimeMs = System.currentTimeMillis();
        log.info("[" + traceId.getId() + "] " + addSpace(START_PREFIX, traceId.getLevel()) + message);

        return new TraceStatus(traceId, startTimeMs, message);
    }

    // V2 에서 추가
    public TraceStatus beginSync(TraceId beforeTraceId, String message){
        TraceId nextId = beforeTraceId.createNextId();
        long startTimeMs = System.currentTimeMillis();
        log.info("[" + beforeTraceId.getId() + "] " + addSpace(START_PREFIX, nextId.getLevel()) + message);

        return new TraceStatus(nextId, startTimeMs, message);
    }

    public void end(TraceStatus status){
        complete(status, null);
    }
    public void exception(TraceStatus status, Exception e){
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e){
        long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null){
            log.info("[" + traceId.getId() + "] " + addSpace(COMPLETE_PREFIX, traceId.getLevel()) +
                    status.getMessage() + " time=" + resultTimeMs + "ms");
        } else {
            log.info("[" + traceId.getId() + "] " + addSpace(EX_PREFIX, traceId.getLevel()) + status.getMessage()
            + " time=" + resultTimeMs + "ms" + " ex=" + e);
        }
    }
    
    private String addSpace(String prefix, int level){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++){
            sb.append((i == level -1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
