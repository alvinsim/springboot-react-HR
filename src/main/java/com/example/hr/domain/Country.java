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
@Table(name = "countries")
public class Country {

    @Id
    private String id;
    @Column(name = "country_name", nullable = false)
    @NotNull
    private String countryName;
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "region_id")
    @ManyToOne
    private Region region;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private List<Location> locations;
}
