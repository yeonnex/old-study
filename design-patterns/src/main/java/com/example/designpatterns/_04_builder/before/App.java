package com.example.designpatterns._04_builder.before;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        TourPlan tourPlan = new TourPlan();
        tourPlan.setTitle("칸쿤 여행");
        tourPlan.setNights(2);
        tourPlan.setDays(2);
        tourPlan.setStartDate(LocalDate.of(2022, 8,24));
        tourPlan.setWhereToStay("리조트");

        tourPlan.addPlan(0, "체크인 이후 짐풀기");
        tourPlan.addPlan(0, "뷔페에서 식사");
        tourPlan.addPlan(1, "해변가 거닐기");

    }
}
