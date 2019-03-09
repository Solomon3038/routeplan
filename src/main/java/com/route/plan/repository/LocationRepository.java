package com.route.plan.repository;

import com.route.plan.domain.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Location findLocationById(long id);
}
