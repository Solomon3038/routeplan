package com.route.plan.controllers;

import com.route.plan.domain.Route;
import com.route.plan.services.RouteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/routes")
public class RouteController {
    private static final Logger logger = LogManager.getLogger(RouteController.class);
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Route> save(@RequestBody String jsonObj) {

        JSONObject jsonObject = new JSONObject(jsonObj);
        final String name = jsonObject.getString("name");
        final Long headId = jsonObject.getLong("head");
        final JSONArray locationsIds = jsonObject.getJSONArray("locations");

        Long[] locationsId = new Long[locationsIds.length()];

        for (int i = 0; i < locationsIds.length(); i++) {
            locationsId[i] = locationsIds.getLong(i);
        }

        logger.info("save name={} , headId={} , locationsId={}", name, headId, locationsId);

        Route created = routeService.save(name, headId, locationsId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/routes" + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Route get(@PathVariable long id) {
        logger.info("get {}", id);
        return routeService.get(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody String jsonObj, @PathVariable long id) {
        JSONObject jsonObject = new JSONObject(jsonObj);
        final long headId = jsonObject.getLong("head");
        logger.info("update headId={} with id={}", headId, id);
        routeService.update(id, headId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        logger.info("delete {}", id);
        routeService.delete(id);
    }
}
