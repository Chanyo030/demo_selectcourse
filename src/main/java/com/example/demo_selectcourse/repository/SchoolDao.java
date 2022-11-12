package com.example.demo_selectcourse.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_selectcourse.entity.School;

@Repository
public interface SchoolDao extends JpaRepository<School, String> {

	public List<School> findAllByCourse(String course); // course�ݥD���O������N��J����
	
	public List<School> findAllByCoursecodeIn(Set<String> allCourse);   //In�i�Ω�h�ӡA�]�N�O���X(��}�C�B�M�榳�������O)
	
	public List<School> findAllByCoursecodeIn(List<String> CourseCode); 
	
	
}
