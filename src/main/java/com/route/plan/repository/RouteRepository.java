package com.route.plan.repository;

import com.route.plan.domain.Route;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RouteRepository extends CrudRepository<Route, Long> {
    Route findRouteById(long id);

    Set<Route> findAllByHead_Id(long headId);

    boolean existsRouteByHead_Id(long headId);
}
