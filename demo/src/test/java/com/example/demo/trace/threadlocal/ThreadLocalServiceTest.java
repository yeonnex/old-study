package com.example.demo.trace.threadlocal;

import com.example.demo.trace.threadlocal.code.ThreadLocalService;
import org.junit.jupiter.api.Test;

public class ThreadLocalServiceTest {
    @Test
    void threadLocal(){
        ThreadLocalService threadLocalService = new ThreadLocalService();
        Runnable userA = () -> {
            threadLocalService.logic("userA");
        };
        Runnable userB = () -> {
            threadLocalService.logic("userB");
        };

        Thread threadB = new Thread(userB);
        Thread threadA = new Thread(userA);

        threadB.start();
        threadA.start();
    }
}
