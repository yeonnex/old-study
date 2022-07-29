package com.example.designpatterns._04_builder.after;

import com.example.designpatterns._04_builder.before.TourPlan;

import java.time.LocalDate;

public class TourDirector {
    private TourPlanBuilder tourPlanBuilder;

    public TourDirector(TourPlanBuilder builder) {
        this.tourPlanBuilder = builder;
    }

    public TourPlan cancunTrip() {
        return  tourPlanBuilder.title("칸쿤여행")
                .nightsAndDays(2, 3)
                .startDate(LocalDate.of(2022, 12, 15))
                .whereToStay("리조트")
                .addPlan(0, "체크인하고 짐풀기")
                .getPlan();
    }

    public TourPlan logBeachTrip() {
        return tourPlanBuilder.title("롱비치")
                .startDate(LocalDate.of(2022, 8, 14))
                .getPlan();
    }
}
