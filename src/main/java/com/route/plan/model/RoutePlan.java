package com.route.plan.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.route.plan.domain.Location;

import java.util.*;

public class RoutePlan {
    private Location head;
    private double minLength;
    private List<Location> routePlan;
    private double road;
    private double bestRoad;
    private Location bestLocation;

    private Location[] plan;

    public RoutePlan(Location[] locations, Location headLocation) {
        this.plan = locations;
        doOptimalRout(headLocation);
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    public Location[] getPlan() {
        return plan;
    }

    private void doOptimalRout(Location headLocation) {
        if (plan.length > 1) {
            List<Location> recoverList = new ArrayList<>(Arrays.asList(plan));
            Map<Location, List<Location>> routePlansMap = new HashMap<>();

            for (Location location : recoverList) {
                chooseLocation(location, recoverList, headLocation);
                routePlansMap.put(location, routePlan);

                if (bestRoad >= road) {
                    bestRoad = road;
                    bestLocation = location;
                }
            }
            plan = routePlansMap.get(bestLocation).toArray(new Location[0]);
        }
    }

    private void chooseLocation(Location location, List<Location> recoverList, Location recoveryHead) {
        List<Location> listLocations = new ArrayList<>(recoverList);
        routePlan = new ArrayList<>();
        road = 0;
        head = recoveryHead;
        listLocations.remove(location);
        addLocation(listLocations);
        listLocations.add(location);

        while (listLocations.size() > 0) {
            addLocation(listLocations);
        }

        if (bestRoad == 0) {
            bestRoad = road;
        }
    }

    private void addLocation(List<Location> listLocations) {
        minLength = Math.sqrt(Math.pow(listLocations.get(0).getX() - head.getX(), 2) + Math.pow(listLocations.get(0).getY() - head.getY(), 2));
        head = getNearerLocation(listLocations);
        routePlan.add(head);
        listLocations.remove(head);
    }

    private Location getNearerLocation(List<Location> locations) {
        Location next = new Location();

        for (Location l : locations) {
            double length = Math.sqrt(Math.pow(l.getX() - head.getX(), 2) + Math.pow(l.getY() - head.getY(), 2));

            if (length <= minLength) {
                minLength = length;
                next = l;
            }
        }
        road += minLength;
        return next;
    }
}
