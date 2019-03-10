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

    public Location save(String name, float x, float y) {

        if (name != null && name.trim().length() != 0) {
            Location location = new Location(name.trim(), x, y);
            return locationRepository.save(location);
        }
        return null;
    }

    public Location get(Long id) {
        return locationRepository.findLocationById(id);
    }

    @CacheEvict(value = "getRoutePlan", allEntries = true)
    public Location update(long id, String name, float x, float y) {

        if (name != null && name.trim().length() != 0) {
            Location location = locationRepository.findLocationById(id);

            if (location != null) {
                location.setName(name.trim());
                location.setX(x);
                location.setY(y);
                return locationRepository.save(location);
            }
        }
        return null;
    }

    @CacheEvict(value = "getRoutePlan", allEntries = true)
    public int delete(long id) {
        Location location = locationRepository.findLocationById(id);

        if (location != null && !location.isHead()) {
            locationRepository.delete(id);
            return 1;
        }
        return -1;
    }
}


