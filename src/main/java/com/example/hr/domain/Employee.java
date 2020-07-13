package com.example.hr.domain;

import com.example.hr.serializer.CustomJsonDateDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "employees")
public class Employee {

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
    private String email;
    @Column(name = "hire_date", nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate hireDate;
    @Column(name = "phone_number")
    private String phoneNumber;
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
    @ManyToOne
    private Employee manager;
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "department_id")
    @ManyToOne
    @NotNull
    private Department department;
}
