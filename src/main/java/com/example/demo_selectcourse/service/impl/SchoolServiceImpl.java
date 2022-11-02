package com.example.demo_selectcourse.service.impl;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo_selectcourse.entity.School;
import com.example.demo_selectcourse.repository.SchoolDao;
import com.example.demo_selectcourse.service.ifs.SchoolService;

@Service
public class SchoolServiceImpl implements SchoolService {
	@Autowired
	private SchoolDao selectCourseDao;

	@Override
	public School setSchedlue(String coursecode, String course, String classday, LocalTime classtime,
			LocalTime recess, int units) {
		School selectCourse = new School();
		selectCourse.setCoursecode(coursecode);
		selectCourse.setCourse(course);
		selectCourse.setClassday(classday);
		selectCourse.setClasstime(classtime);
		return selectCourseDao.save(selectCourse);
	}

	

	
	}
	


