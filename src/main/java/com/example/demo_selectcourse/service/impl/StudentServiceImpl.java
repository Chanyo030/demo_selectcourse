package com.example.demo_selectcourse.service.impl;

import com.example.demo_selectcourse.entity.Student;
import com.example.demo_selectcourse.service.ifs.StudentService;

public class StudentServiceImpl implements StudentService{

	@Override
	public Student setPii(String studentid, String name, String classcode) {
		Student student = new Student();
		student.setId(studentid);
		student.setName(name);
		student.setClasscode(classcode);
		return null;
	}

}
