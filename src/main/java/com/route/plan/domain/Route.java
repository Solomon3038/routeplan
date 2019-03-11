package com.route.plan.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
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

    @EqualsAndHashCode.Exclude
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Location head;

    @EqualsAndHashCode.Exclude
    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    @OrderColumn(name = "id")
    @JoinTable(name = "route_location",
            joinColumns = @JoinColumn(name = "route_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id")
    )
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Location[] locations;

    public Route(String name, Location[] locations, Location head) {
        this.name = name;
        this.locations = locations;
        this.head = head;
    }
}
