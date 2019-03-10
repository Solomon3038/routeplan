package com.route.plan.controllers;

import com.route.plan.model.RoutePlan;
import com.route.plan.model.RoutePlanQueue;
import com.route.plan.services.MainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {
    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    //тут не корректна реалізація, тому що ще не доводилось працювати із встановленням часу очікування сервером + не встигла розібратись самостійно
    @GetMapping(value = "/routes/{id}/plan", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getRouteSuccess(HttpServletResponse response, @PathVariable long id) {
        RoutePlan routePlan = mainService.getRoutePlan(id);

        if (routePlan == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        int status = response.getStatus();
        if (status == 202) {
            return new ResponseEntity<>("Location:/routePlanQueue/100500/" + id, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(routePlan, HttpStatus.OK);
    }


    @GetMapping(value = "/routePlanQueue/100500/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Object> getRouteStatus(HttpServletResponse response, @PathVariable long id) {
        int status = response.getStatus();
        if (status == 303) {
            return new ResponseEntity<>("Location:/routes/" + id + "/plan", HttpStatus.SEE_OTHER);
        }
        RoutePlanQueue routePlanQueue = new RoutePlanQueue();
        routePlanQueue.setStatus("processing");
        return new ResponseEntity<>(routePlanQueue, HttpStatus.OK);
    }

}
