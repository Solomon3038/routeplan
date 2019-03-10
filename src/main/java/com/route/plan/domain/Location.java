package com.route.plan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@TableGenerator(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please, set name for Location")
    @Length(max = 255, message = "Location name is too long")
    @Column(unique = true)
    private String name;

    @NotNull
    private float x;

    @NotNull
    private float y;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "locations")
    private Set<Route> routes;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private Route route;

    private boolean head;

    public Location() {

    }

    public Location(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @JsonIgnore
    public boolean isHead() {
        return head;
    }

    @JsonSetter
    public void setHead(boolean head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
