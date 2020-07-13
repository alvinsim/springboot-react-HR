package com.example.hr.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "countries")
public class Country {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name = "country_code", nullable = false)
    private String countryCode;
    @Column(name = "country_name", nullable = false)
    @NotNull
    private String countryName;
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "region_id")
    @ManyToOne
    private Region region;
}
