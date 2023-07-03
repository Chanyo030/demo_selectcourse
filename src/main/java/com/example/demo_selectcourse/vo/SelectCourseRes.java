package com.example.demo_selectcourse.vo;

import java.util.List;

import com.example.demo_selectcourse.entity.School;
import com.example.demo_selectcourse.entity.Student;
import com.fasterxml.jackson.annotation.JsonInclude;

//����NULL
@JsonInclude(JsonInclude.Include.NON_NULL)

//�^��
public class SelectCourseRes {

	// SCHOOL
	private School school;

	// �ҵ{�d��
	private List<School> courseQuery;
	
	private List<School> selectCourseList;

//=========================================//		
	// STUDENT
	private Student student;
	
	private List<Student> studentAllList;

	// �@�P���~�T������
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
	
	public SelectCourseRes(List<School> selectCourseList) {
		this.selectCourseList = selectCourseList;
	}

	public SelectCourseRes(String message) {
		this.message = message;
	}

	/* ================�غc��k and get/set================== */
	// ���B�L�Ѽƪ��غc��k�ηN�O���F����@���]�w�h���ݩʪ���
	public SelectCourseRes() {

	}
	
	public SelectCourseRes(String message, List<Student> studentAllList) {
		this.message = message;
		this.studentAllList = studentAllList;
	}
	
	public SelectCourseRes(School school, List<School> courseQuery, Student student, String message) {
		super();
		this.school = school;
		this.courseQuery = courseQuery;
		this.student = student;
		this.message = message;
	}

	// Get�BSet��k ����Lclass�i�H�]�w�Ψ��o�o���ݩʪ���

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
	
	public List<School> getSelectCourseList() {
		return selectCourseList;
	}

	public void setSelectCourseList(List<School> selectCourseList) {
		this.selectCourseList = selectCourseList;
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

	public List<Student> getStudentAllList() {
		return studentAllList;
	}

	public void setStudentAllList(List<Student> studentAllList) {
		this.studentAllList = studentAllList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
