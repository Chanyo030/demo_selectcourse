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
	private String courseDay;
	
	//�W�Үɶ�
	private String startTime;
	
	//�U�Үɶ�
	private String endTime;
	
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
			String courseDay, String startTime, String endTime, int units, String studentId, String studentName) {
		this.courseCode = courseCode;
		this.courseCodeSet = courseCodeSet;
		this.courseCodeList = courseCodeList;
		this.course = course;
		this.courseDay = courseDay;
		this.startTime = startTime;
		this.endTime = endTime;
		this.units = units;
		this.studentId = studentId;
		this.studentName = studentName;
	}


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


	public String getCourseDay() {
		return courseDay;
	}


	public void setCourseDay(String courseDay) {
		this.courseDay = courseDay;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
