package com.route.plan.controllers;

import com.route.plan.domain.Location;
import com.route.plan.repository.LocationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/locations")
public class LocationController {
    private final LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Location save(@RequestParam String name,
                         @RequestParam float x,
                         @RequestParam float y) {

        Location location = new Location(name, x, y);
        return locationRepository.save(location);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Location get(@PathVariable long id) {
        return locationRepository.findLocationById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long id,
                       @RequestParam String name,
                       @RequestParam float x,
                       @RequestParam float y) {

        Location location = locationRepository.findLocationById(id);
        location.setName(name);
        location.setX(x);
        location.setY(y);
        locationRepository.save(location);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        Location location = locationRepository.findLocationById(id);
        if (!location.isHead()) {
            locationRepository.deleteById(id);
        }
    }
}
