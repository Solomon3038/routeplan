package com.route.plan.controllers;

import com.route.plan.domain.Route;
import com.route.plan.services.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/routes")
public class RouteController {
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Route> save(@RequestParam String name,
                                      @RequestParam long headId,
                                      @RequestParam Long[] locationsId) {
        Route route = routeService.save(name, headId, locationsId);
        return (route != null ? new ResponseEntity<>(route, HttpStatus.CREATED) : new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Route> get(@PathVariable long id) {
        Route route = routeService.get(id);
        return (route != null ? new ResponseEntity<>(route, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Route> update(@PathVariable long id,
                                        @RequestParam long headId) {
        Route route = routeService.update(id, headId);
        return (route != null ? new ResponseEntity<>(route, HttpStatus.NO_CONTENT) : new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Route> delete(@PathVariable long id) {
        int status = routeService.delete(id);
        return (status != -1 ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
