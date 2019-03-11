package com.route.plan.services;

import com.route.plan.domain.Location;
import com.route.plan.repository.LocationRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public Location get(long id) {
        return locationRepository.findLocationById(id);
    }

    @CacheEvict(value = "getRoutePlan", allEntries = true)
    public void update(long id, Location location) {

        Location current = locationRepository.findLocationById(id);

        if (current != null) {
            location.setId(id);
            locationRepository.save(location);
        }
    }

    @CacheEvict(value = "getRoutePlan", allEntries = true)
    public void delete(long id) {
        Location location = locationRepository.findLocationById(id);

        if (location != null && !location.isHead()) {
            locationRepository.delete(id);
        }
    }
}


