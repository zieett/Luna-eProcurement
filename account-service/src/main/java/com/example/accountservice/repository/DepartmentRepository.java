package com.example.accountservice.repository;

import com.example.accountservice.entity.Department;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,String> {
    List<Department> getDepartmentByLegalEntityCode(String legalEntityCode);
}
