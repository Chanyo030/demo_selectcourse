package com.example.demo_selectcourse.service.ifs;

import java.time.LocalTime;

import com.example.demo_selectcourse.entity.School;

public interface SchoolService {
	//setSchedlue �]�m�ҵ{��T     Schedlue �ҵ{�� 
	public School setSchedlue (String coursecode, String course, String classday, LocalTime classtime, LocalTime recess, int units);
}
