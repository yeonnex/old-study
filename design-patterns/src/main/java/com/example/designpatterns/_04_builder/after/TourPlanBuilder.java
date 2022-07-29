package com.example.designpatterns._04_builder.after;

import com.example.designpatterns._04_builder.before.TourPlan;

import java.time.LocalDate;

public interface TourPlanBuilder {
    TourPlanBuilder title(String title);
    TourPlanBuilder nightsAndDays(int nights, int days);
    TourPlanBuilder startDate(LocalDate localDate);
    TourPlanBuilder whereToStay(String whereToStay);
    TourPlanBuilder addPlan(int day, String plan);

    TourPlan getPlan(); // 인스턴스의 상태가 불완전한지/아닌지 검증 기능을 넣을수도 있음
}
