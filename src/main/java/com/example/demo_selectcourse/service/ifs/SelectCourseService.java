package com.example.demo_selectcourse.service.ifs;

import java.util.List;
import java.util.Set;

import com.example.demo_selectcourse.vo.SelectCourseRes;

public interface SelectCourseService {
	// SCHOOL
	
	// 新增(創建)課程 Create
	public SelectCourseRes createCourse(String coursCode, String courseName, String courseday, String startTime,
			String endTime, int units);

	// 修改課程 Update
	public SelectCourseRes updateCourse(String coursCode, String courseName, String courseday, String startTime,
			String endTime, int units);

	// 刪除課程 Delete
	public SelectCourseRes deleteCourse(String courseCode);

	// 課程查詢 class query (透過代碼查詢)
	public SelectCourseRes courseQuery(String courseCode);

	// 課程查詢 class name query (透過課堂名稱查詢)
	public SelectCourseRes courseNameQuery(String courseName);
	
	// 課程總覽 CourseAllInfo
	public SelectCourseRes courseAllInfo();

	/* ============================================================= */
	// STUDENT

	// 新增學生資訊
	public SelectCourseRes addStudentInfo(String studentId, String studentName);

	// 修改學生資訊
	public SelectCourseRes updateStudentInfo(String studentId, String studentName);

	// 刪除學生資訊
	public SelectCourseRes deleteStudentInfo(String studentId, String studentName);
	
	// 學生查詢 studentIdQuery (透過學號查詢)
	public SelectCourseRes studentIdQuery(String studentId);

	// 學生查詢 studentNameQuery (透過姓名查詢)
	public SelectCourseRes studentNameQuery(String studentName);
	
	// 學生總覽	studentAllInfo
	public SelectCourseRes studentAllInfo();

	// 選課 course selection
	public SelectCourseRes courseSelection(String studentId, Set<String> courseCode);

	// 加選 add class
	public SelectCourseRes addCourse(String studentId, Set<String> courseCode);
	
	// 退選 withdraw class
	public SelectCourseRes withdrawCourse(String studentId, List<String> courseCode);
	
	// 學生所選課程總覽 class Overview (透過學號查詢)
	public SelectCourseRes courseOverview(String studentId);
	
	
	
}
