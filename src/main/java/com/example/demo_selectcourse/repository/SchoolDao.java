package com.example.demo_selectcourse.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_selectcourse.entity.School;

@Repository     //是與資料庫溝通的中介，會被Service呼叫，透過Dao來實現對資料庫的增、刪、改、查)
public interface SchoolDao extends JpaRepository<School, String> {

	public List<School> findAllByCourse(String course); // course看主類別打什麼就輸入什麼
	
	public List<School> findAllByCoursecodeIn(Set<String> allCourse);   //In可用於多個，也就是集合(跟陣列、清單有關的都是)
	
	public List<School> findAllByCoursecodeIn(List<String> CourseCode);  //In可用於多個，也就是集合(跟陣列、清單有關的都是)
	
	
}
