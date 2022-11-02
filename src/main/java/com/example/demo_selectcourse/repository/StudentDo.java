package com.example.demo_selectcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo_selectcourse.entity.Student;

public interface StudentDo extends JpaRepository<Student, String>{

}
