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
@Table(name = "regions")
public class Region {

    @GeneratedValue
    @Id
    private int id;
    @Column(name = "region_name", nullable = false)
    @NotNull
    private String regionName;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<Country> countries;
}
