package com.example.demo_selectcourse.vo;

import java.util.List;
import java.util.Set;

//請求  
public class SelectCourseReq {              
	
	//SCHOOL
	
	//課程代碼 (參數名稱需與JSON一致)
	private String courseCode;
	
	//Set不允許重複清單
	private Set<String> courseCodeSet;      
	
	//List清單
	private List<String> courseCodeList;     
	
	//課程名稱
	private String course;
	
	//上課星期
	private String courseDay;
	
	//上課時間
	private String startTime;
	
	//下課時間
	private String endTime;
	
	//課程學分
	private int units;

	/*==========================================================*/
	
	//STUDENT
	
	//學號
	private String studentId;
	
	//學生姓名
	private String studentName;

	/*================建構方法 and get/set==================*/
	//有、無參數的建構方法用意是為了能夠一次設定多個屬性的值
	
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
