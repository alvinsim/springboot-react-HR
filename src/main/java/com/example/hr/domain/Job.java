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
@Table(name = "jobs")
public class Job {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    @Column(name = "job_title", nullable = false)
    @NotNull
    private String jobTitle;
    @Column(name = "min_salary", nullable = false)
    @NotNull
    private double minSalary;
    @Column(name = "max_salary", nullable = false)
    @NotNull
    private double maxSalary;
    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
    private List<Employee> employees;
}
