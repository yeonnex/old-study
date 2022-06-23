package com.example.logtracer.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그를 시작할 때의 상태 정보를 가지고 있다.
 * 이 상태 정보는 로그를 종료할 때 사용된다.
 */
@AllArgsConstructor
@Getter
public class TraceStatus {
    private TraceId traceId; // 내부에 트랜잭션 ID 와 level 을 가지고 있다
    private Long startTimeMs; // 로그 시작시간. 로그 종료시 시작시간을 기준으로 전체 수행시간을 구할 수 있다
    private String message; // 시작시 사용한 메시지. 로그 종료시에도 이 메시지 이용.
}
