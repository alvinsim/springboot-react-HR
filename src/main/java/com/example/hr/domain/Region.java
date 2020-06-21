package com.example.hr.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<Country> countries;
}
