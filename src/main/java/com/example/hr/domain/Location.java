package com.example.hr.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "locations")
public class Location {

    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "location")
    private List<Department> departments;
}
