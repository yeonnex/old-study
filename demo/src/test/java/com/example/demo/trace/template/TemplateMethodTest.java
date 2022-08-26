package com.example.demo.trace.template;

import com.example.demo.trace.template.code.AbstractTemplate;
import com.example.demo.trace.template.code.SubclassLogic1;
import com.example.demo.trace.template.code.SubclassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    /**
     * 템플릿 메서드 패턴을 적용
     */
    @Test
    void templateMethodV1() {
        AbstractTemplate logic1 = new SubclassLogic1();
        AbstractTemplate logic2 = new SubclassLogic2();

        logic1.execute();
        logic2.execute();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직 1 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        log.info("resultTime = {}", endTime - startTime);
    }
    private void logic2() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직 2 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        log.info("resultTime = {}", endTime - startTime);
    }

    @Test
    void templateMethodV2(){
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직 1 실행");
            }
        };

        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직 2 실행");
            }
        };

        template1.execute();
        template2.execute();
    }

}
