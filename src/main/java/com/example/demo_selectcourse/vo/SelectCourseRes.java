package com.example.demo_selectcourse.vo;

import java.util.List;

import com.example.demo_selectcourse.entity.School;
import com.example.demo_selectcourse.entity.Student;
import com.fasterxml.jackson.annotation.JsonInclude;

//隱藏NULL
@JsonInclude(JsonInclude.Include.NON_NULL)

//回傳
public class SelectCourseRes {

	// SCHOOL
	private School school;

	// 課程查詢
	private List<School> courseQuery;

//=========================================//		
	// STUDENT
	private Student student;

	// 共同錯誤訊息提示
	private String message;

//==================RthCode=======================//
	public SelectCourseRes(School school, String message) {
		this.school = school;
		this.message = message;
	}

	public SelectCourseRes(List<School> courseQuery, String message) {
		this.courseQuery = courseQuery;
		this.message = message;
	}

	public SelectCourseRes(List<School> courseQuery, Student student, String message) {
		this.courseQuery = courseQuery;
		this.student = student;
		this.message = message;
	}

	public SelectCourseRes(Student student, String message) {
		this.student = student;
		this.message = message;
	}

	public SelectCourseRes(String message) {
		this.message = message;
	}

	/* ================建構方法 and get/set================== */
	// 有、無參數的建構方法用意是為了能夠一次設定多個屬性的值
	public SelectCourseRes() {

	}

	public SelectCourseRes(School school, List<School> courseQuery, Student student, String message) {
		super();
		this.school = school;
		this.courseQuery = courseQuery;
		this.student = student;
		this.message = message;
	}

	// Get、Set方法 讓其他class可以設定及取得這些屬性的值

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public List<School> getCourseQuery() {
		return courseQuery;
	}

	public void setCourseQuery(List<School> courseQuery) {
		this.courseQuery = courseQuery;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
