package com.example.demo_selectcourse.repository;



//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_selectcourse.entity.Student;
@Repository
public interface StudentDao extends JpaRepository<Student, String> {
	
}
