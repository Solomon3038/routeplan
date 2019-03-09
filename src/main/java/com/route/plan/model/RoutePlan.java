package com.route.plan.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.route.plan.domain.Location;

import java.util.Arrays;

public class RoutePlan {

    private Location[] plan;

    public RoutePlan(Location[] locations) {
        this.plan = locations;
    }

    public void sort(){
        Arrays.sort(plan);
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    public Location[] getPlan() {
        return plan;
    }

    @Override
    public String toString() {
        return "RoutePlan{" +
                "plan=" + Arrays.toString(plan) +
                '}';
    }
}
