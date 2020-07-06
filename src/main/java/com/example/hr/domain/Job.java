package com.example.hr.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "jobs")
public class Job {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name = "job_title", nullable = false)
    @NotNull
    private String jobTitle;
    @Column(name = "min_salary", nullable = false)
    @NotNull
    @Min(value = 1)
    private double minSalary;
    @Column(name = "max_salary", nullable = false)
    @NotNull
    @Min(value = 1)
    private double maxSalary;
}
