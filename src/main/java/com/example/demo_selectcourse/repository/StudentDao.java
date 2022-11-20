package com.example.demo_selectcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_selectcourse.entity.Student;

//是與資料庫溝通的中介，會被Service呼叫，透過Dao來實現對資料庫的增、刪、改、查)
@Repository       
public interface StudentDao extends JpaRepository<Student, String> {
	
}
