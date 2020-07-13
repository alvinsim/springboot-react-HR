package com.example.hr.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "dependents")
public class Dependent {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String relationship;
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "employee_id")
    @ManyToOne
    @NotNull
    private Employee employee;
}
