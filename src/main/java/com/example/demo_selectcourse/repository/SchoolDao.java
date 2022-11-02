package com.example.demo_selectcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo_selectcourse.entity.School;

public interface SchoolDao extends JpaRepository<School, String> {
	

}
