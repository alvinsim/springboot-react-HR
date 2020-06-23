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
@Table(name = "departments")
public class Department {

    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Employee> employees;
}
