package com.route.plan.controllers;

import com.route.plan.domain.Location;
import com.route.plan.services.LocationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/locations")
public class LocationController {
    private static final Logger logger = LogManager.getLogger(LocationController.class);

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Location> save(@RequestParam String name,
                                         @RequestParam float x,
                                         @RequestParam float y) {
        logger.info("save {}", name, x, y);
        Location location = locationService.save(name, x, y);
        return (location != null ? new ResponseEntity<>(location, HttpStatus.CREATED) : new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Location> get(@PathVariable Long id) {
        logger.info("get {}", id);
        Location location = locationService.get(id);
        return (location != null ? new ResponseEntity<>(location, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> update(@PathVariable long id,
                                           @RequestParam String name,
                                           @RequestParam float x,
                                           @RequestParam float y) {
        logger.info("update {}", id, name, x, y);
        Location location = locationService.update(id, name, x, y);
        return (location != null ? new ResponseEntity<>(location, HttpStatus.NO_CONTENT) : new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        logger.info("delete {}", id);
        int status = locationService.delete(id);
        return (status != -1 ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
