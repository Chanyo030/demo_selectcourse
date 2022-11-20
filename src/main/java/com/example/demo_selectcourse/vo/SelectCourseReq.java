package com.example.demo_selectcourse.vo;

import java.util.List;
import java.util.Set;

//�ШD  
public class SelectCourseReq {              
	
	//SCHOOL
	
	//�ҵ{�N�X (�ѼƦW�ٻݻPJSON�@�P)
	private String courseCode;
	
	//Set�����\���ƲM��
	private Set<String> courseCodeSet;      
	
	//List�M��
	private List<String> courseCodeList;     
	
	//�ҵ{�W��
	private String course;
	
	//�W�ҬP��
	private String classDay;
	
	//�W�Үɶ�
	private String classTime;
	
	//�U�Үɶ�
	private String recess;
	
	//�ҵ{�Ǥ�
	private int units;

	/*==========================================================*/
	
	//STUDENT
	
	//�Ǹ�
	private String studentId;
	
	//�ǥͩm�W
	private String studentName;

	/*================�غc��k and get/set==================*/
	//���B�L�Ѽƪ��غc��k�ηN�O���F����@���]�w�h���ݩʪ���
	
	public SelectCourseReq() {
		
	}

	public SelectCourseReq(String courseCode, Set<String> courseCodeSet, List<String> courseCodeList, String course,
			String classDay, String classTime, String recess, int units, String studentId, String studentName) {
		this.courseCode = courseCode;
		this.courseCodeSet = courseCodeSet;
		this.courseCodeList = courseCodeList;
		this.course = course;
		this.classDay = classDay;
		this.classTime = classTime;
		this.recess = recess;
		this.units = units;
		this.studentId = studentId;
		this.studentName = studentName;
	}

	//Get�BSet��k ����Lclass�i�H�]�w�Ψ��o�o���ݩʪ���
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public Set<String> getCourseCodeSet() {
		return courseCodeSet;
	}

	public void setCourseCodeSet(Set<String> courseCodeSet) {
		this.courseCodeSet = courseCodeSet;
	}

	public List<String> getCourseCodeList() {
		return courseCodeList;
	}

	public void setCourseCodeList(List<String> courseCodeList) {
		this.courseCodeList = courseCodeList;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getClassDay() {
		return classDay;
	}

	public void setClassDay(String classDay) {
		this.classDay = classDay;
	}

	public String getClassTime() {
		return classTime;
	}

	public void setClassTime(String classTime) {
		this.classTime = classTime;
	}

	public String getRecess() {
		return recess;
	}

	public void setRecess(String recess) {
		this.recess = recess;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
}
