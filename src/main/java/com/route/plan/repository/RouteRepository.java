package com.route.plan.repository;

import com.route.plan.domain.Route;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RouteRepository extends CrudRepository<Route, Long> {

    @Override
    @Transactional
    <S extends Route> S  save(S item);

    @Transactional
    @Modifying
    @Query("DELETE FROM Route  r WHERE r.id=:id")
    void delete(@Param("id") long id);

    Route findRouteById(long id);

    boolean existsRouteByHead_Id(long headId);
}
