package com.example.logtracer.trace;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * 로그 추적기는 트랜잭션 ID 와 깊이를 표현하는 방법이 필요하다.
 * 트랜잭션 ID 와 깊이를 표현하는 level 을 묶어 TraceId 라는 개념을 만들었다.
 */
@Getter
@Setter
public class TraceId {
    private String id;
    private int level;

    public TraceId(){
        this.id = createId();
        this.level = 0;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0,8);
    }
    private TraceId(String id, int level){
        this.id = id;
        this.level = level;
    }

    public TraceId createNextId() {
        return new TraceId(id, level + 1);
    }

    public TraceId createPreviousId() {
        return new TraceId(id, level - 1);
    }

    public boolean isFirstLevel(){
        return level == 0;
    }
}
