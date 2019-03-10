package com.route.plan.services;

import com.route.plan.domain.Location;
import com.route.plan.domain.Route;
import com.route.plan.repository.LocationRepository;
import com.route.plan.repository.RouteRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final LocationRepository locationRepository;

    public RouteService(RouteRepository routeRepository, LocationRepository locationRepository) {
        this.routeRepository = routeRepository;
        this.locationRepository = locationRepository;
    }

    @CacheEvict(value = "getRoutePlan", allEntries = true)
    public Route save(String name, long headId, Long[] locationsId) {

        if (name == null || name.trim().length() == 0 || locationsId == null || locationsId.length == 0) {
            return null;
        }
        ArrayList<Long> arrayList = new ArrayList<>(Arrays.asList(locationsId));
        ArrayList<Location> locationsList = (ArrayList<Location>) locationRepository.findAllById(arrayList);

        if (locationsId.length != locationsList.size()) {
            return null;
        }
        Location[] locations = new Location[locationsList.size()];

        for (int i = 0; i < locations.length; i++) {
            locations[i] = locationsList.get(i);
        }
        Location head = locationRepository.findLocationById(headId);

        if (head == null) {
            return null;
        }
        head.setHead(true);
        Route route = new Route(name.trim(), locations, head);
        return routeRepository.save(route);
    }

    public Route get(long id) {
        return routeRepository.findRouteById(id);
    }

    @CacheEvict(value = "getRoutePlan", allEntries = true)
    public Route update(long id, long headId) {
        Route route = routeRepository.findRouteById(id);

        if (route == null) {
            return null;
        }
        Location headOld = route.getHead();
        headOld.setHead(false);
        Location headNew = locationRepository.findLocationById(headId);

        if (headNew == null) {
            return null;
        }
        headNew.setHead(true);
        route.setHead(headNew);
        return routeRepository.save(route);
    }

    //тут по умові завдання не вказано повертати значення head в false якщо жоден Route не містить її,але тоді з часом всі локаціі стануть head-true
    //добавила очистку кеша, тому що якщо маршруту вже нема, то і план його не повинен бут доступний
    @CacheEvict(value = "getRoutePlan", allEntries = true)
    public int delete(long id) {
        Route route = routeRepository.findRouteById(id);

        if (route == null) {
            return -1;
        }
        routeRepository.delete(id);
        Location head = route.getHead();
        boolean existRoute = routeRepository.existsRouteByHead_Id(head.getId());
        if (!existRoute) {
            head.setHead(false);
            locationRepository.save(head);
        }
        return 1;
    }
}
