package com.route.plan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
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

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "locations")
    private Set<Route> routes;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private Route route;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @NotNull
    private boolean head;

    public Location(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
}
