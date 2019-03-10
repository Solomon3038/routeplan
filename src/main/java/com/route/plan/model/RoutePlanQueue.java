package com.route.plan.model;

public class RoutePlanQueue {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RoutePlanQueue{" +
                "status='" + status + '\'' +
                '}';
    }
}
