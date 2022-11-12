package com.example.demo_selectcourse.service.ifs;

import java.util.List;
import java.util.Set;

import com.example.demo_selectcourse.vo.SelectCourseRes;

public interface SelectCourseService {
	// SCHOOL
	// 新增課程 Create
	public SelectCourseRes createCours(String courscode, String course, String classday, String classtime,
			String recess, int units);

	// 修改課程 Update
	public SelectCourseRes updateCours(String courscode, String course, String classday, String classtime,
			String recess, int units);

	// 刪除課程 Delete
	public SelectCourseRes deleteCours(String courscode);

	// 課程查詢 class query (透過代碼查詢)
	public SelectCourseRes classQuery(String courscode);

	// 課程查詢 class name query (透過課堂名稱查詢)
	public SelectCourseRes classnameQuery(String course);

	/* ============================================================= */
	// STUDENT

	// 新增學生選課資訊
	public SelectCourseRes addStudentSelectCourse(String studentId, String studentName);

	// 修改學生選課資訊
	public SelectCourseRes updateStudentSelectCourse(String studentId, String studentName);

	// 刪除學生選課資訊
	public SelectCourseRes deleteStudentSelectCourse(String studentId, String studentName);

	// 選課 course selection
	public SelectCourseRes courseSelection(String studentId, Set<String> courscode);

	// 加選 add class
	public SelectCourseRes addClass(String studentId, Set<String> courscode);
	
	// 退選 withdraw class
	public SelectCourseRes WithdrawClass(String studentId, List<String> courscode);
	
	// 學生所選課程總覽 class Overview (透過學號查詢)
	public SelectCourseRes classOverview(String studentId);

}
