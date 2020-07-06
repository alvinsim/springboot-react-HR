package com.example.hr.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "departments")
public class Department {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name = "department_name", nullable = false)
    @NotNull
    private String departmentName;
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "location_id")
    @NotNull
    @ManyToOne
    private Location location;
}
