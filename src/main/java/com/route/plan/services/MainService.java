package com.route.plan.services;

import com.route.plan.domain.Route;
import com.route.plan.model.RoutePlan;
import com.route.plan.repository.RouteRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    private final RouteRepository routeRepository;

    public MainService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Cacheable("getRoutePlan")
    public RoutePlan getRoutePlan(long id) {
        Route route = routeRepository.findRouteById(id);

        if (route == null) {
            return null;
        }
        return new RoutePlan(route.getLocations(), route.getHead());
    }
}
