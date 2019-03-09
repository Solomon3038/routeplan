package com.route.plan.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@TableGenerator(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please, set name for Route")
    @Length(max = 255, message = "Route name is too long")
    @Column(unique = true)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Location head;

    @ManyToMany(fetch = FetchType.LAZY)
    @OrderColumn(name = "id")
    @JoinTable(name="route_location",
            joinColumns = @JoinColumn(name="route_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="location_id", referencedColumnName="id")
    )
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Location[] locations;

    public Route() {
    }

    public Route(String name, Location[] locations, Location head) {
        this.name = name;
        this.locations = locations;
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location[] getLocations() {
        return locations;
    }

    public void setLocations(Location[] locations) {
        this.locations = locations;
    }

    public Location getHead() {
        return head;
    }

    public void setHead(Location head) {
        this.head = head;
    }
}
