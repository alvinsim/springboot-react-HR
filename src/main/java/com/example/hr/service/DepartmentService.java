package com.example.hr.service;

import com.example.hr.controller.responsehandling.EntityNotFoundException;
import com.example.hr.domain.Department;
import com.example.hr.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository repository;

    public Department getDepartmentById(int id) {
        Optional<Department> department = repository.findById(id);
        department.orElseThrow(() -> new EntityNotFoundException(Department.class, "id", String.valueOf(id)));
        return department.get();
    }

    public Iterable<Department> getAllDepartments() {
        return repository.findAll();
    }

    public Department saveDepartment(Department department) {
        return repository.save(department);
    }

    public void deleteDepartmentById(int id) {
        Department department = getDepartmentById(id);
        repository.deleteById(department.getId());
    }
}
