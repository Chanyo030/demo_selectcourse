package com.example.demo_selectcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_selectcourse.entity.Student;

//�O�P��Ʈw���q�������A�|�QService�I�s�A�z�LDao�ӹ�{���Ʈw���W�B�R�B��B�d)
@Repository       
public interface StudentDao extends JpaRepository<Student, String> {
	
}
