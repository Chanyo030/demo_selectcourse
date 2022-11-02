package com.example.demo_selectcourse.service.ifs;

import java.time.LocalTime;

import com.example.demo_selectcourse.entity.School;

public interface SchoolService {
	//setSchedlue 設置課程資訊     Schedlue 課程表 
	public School setSchedlue (String coursecode, String course, String classday, LocalTime classtime, LocalTime recess, int units);
}
