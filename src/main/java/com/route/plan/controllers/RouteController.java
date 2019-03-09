package com.route.plan.controllers;

import com.route.plan.domain.Location;
import com.route.plan.domain.Route;
import com.route.plan.repository.LocationRepository;
import com.route.plan.repository.RouteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@Controller
@RequestMapping("/routes")
public class RouteController {

    private final RouteRepository routeRepository;
    private final LocationRepository locationRepository;

    public RouteController(RouteRepository routeRepository, LocationRepository locationRepository) {
        this.routeRepository = routeRepository;
        this.locationRepository = locationRepository;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Route save(@RequestParam String name,
                      @RequestParam long headId,
                      @RequestParam Long[] locationsId) {

        Location[] locations = Stream.of(locationsId).map(locationRepository::findLocationById).toArray(Location[]::new);
        Location head = locationRepository.findLocationById(headId);
        head.setHead(true);
        Route route = new Route(name, locations, head);
        return routeRepository.save(route);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Route get(@PathVariable long id) {
        return routeRepository.findRouteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long id,
                       @RequestParam long headId) {
        Route route = routeRepository.findRouteById(id);
        Location headOld = route.getHead();
        headOld.setHead(false);
        Location headNew = locationRepository.findLocationById(headId);
        headNew.setHead(true);
        route.setHead(headNew);
        routeRepository.save(route);
    }

    //тут по умові завдання не вказано повертати значення head в false якщо жоден Route не містить її,але тоді з часом всі локаціі стануть head-true
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        Route route = routeRepository.findRouteById(id);
        routeRepository.deleteById(id);

        Location head = route.getHead();
        boolean existRoute = routeRepository.existsRouteByHead_Id(head.getId());
        if (!existRoute) {
            head.setHead(false);
            locationRepository.save(head);
        }
    }
}
