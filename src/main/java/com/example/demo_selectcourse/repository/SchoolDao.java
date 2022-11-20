package com.example.demo_selectcourse.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_selectcourse.entity.School;

//�O�P��Ʈw���q�������A�|�QService�I�s�A�z�LDao�ӹ�{���Ʈw���W�B�R�B��B�d)
@Repository     

public interface SchoolDao extends JpaRepository<School, String> {

	// �z�L�ҵ{�W�ٴM��ŦX���ҵ{ (course�ݥD���O������N��J����)
	public List<School> findAllByCourse(String course); 
	
	//�z�L�@�өΦh�ӽҵ{�N�X�M��ŦX���ҵ{         In�i�Ω�h�ӡA�]�N�O���X(��}�C�B�M�榳�������O)
	public List<School> findAllByCourseCodeIn(Set<String> allCourse);   
	
	//�z�L�@�өΦh�ӽҵ{�N�X�M��ŦX���ҵ{         In�i�Ω�h�ӡA�]�N�O���X(��}�C�B�M�榳�������O)
	public List<School> findAllByCourseCodeIn(List<String> CourseCode);
	
	
}
