package com.example.hr.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "locations")
public class Location {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name = "street_address")
    private String streetAddress;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(nullable = false)
    @NotNull
    private String city;
    @Column(name = "state_province")
    private String stateProvince;
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "country_id")
    @ManyToOne
    private Country country;
}
