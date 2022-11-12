package com.example.demo_selectcourse.vo;

import java.util.List;

import com.example.demo_selectcourse.entity.School;
import com.example.demo_selectcourse.entity.Student;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelectCourseRes {

	//SCHOOL
	private School school;
	
	//課程查詢
		private List<School> classQuery;

//=========================================//		
	//STUDENT
		private Student student;
	
	// 共同錯誤訊息提示
	private String message;
	
//==================RthCode=======================//
	public SelectCourseRes(School school, String message ) {
		this.school = school;
		this.message = message;
	}
	
	public SelectCourseRes(List<School> classQuery, String message ) {
		this.classQuery = classQuery;
		this.message = message;
	}
	
	public SelectCourseRes(List<School> classQuery, Student student, String message ) {
		this.classQuery = classQuery;
		this.student = student;
		this.message = message;
	}
	
	public SelectCourseRes(Student student, String message ) {
		this.student = student;
		this.message = message;
	}
	
	
	public SelectCourseRes(String message) {
		this.message = message;
	}
	/*================建構 and get/set==================*/

	public SelectCourseRes() {
		
	}

	public SelectCourseRes(School school, List<School> classQuery, Student student, String message) {
		super();
		this.school = school;
		this.classQuery = classQuery;
		this.student = student;
		this.message = message;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public List<School> getClassQuery() {
		return classQuery;
	}

	public void setClassQuery(List<School> classQuery) {
		this.classQuery = classQuery;
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
