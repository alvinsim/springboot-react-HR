package com.example.hr.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "employees")
public class Employee {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    @Column(name = "first_name", nullable = false)
    @NotNull
    private String firstName;
    @Column(name = "last_name", nullable = false)
    @NotNull
    private String lastName;
    @Column(nullable = false)
    @NotNull
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "hire_date", nullable = false)
    @NotNull
    private Date hireDate;
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "job_id")
    @ManyToOne
    @NotNull
    private Job job;
    @Column(nullable = false)
    @NotNull
    private double salary;
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "manager_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Employee managerId;
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "department_id")
    @ManyToOne(cascade = CascadeType.REFRESH)
    @NotNull
    private Department department;
    @OneToMany(mappedBy = "dependent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dependent> dependents;
    @OneToMany(mappedBy = "managerId")
    private List<Employee> employees;
}
