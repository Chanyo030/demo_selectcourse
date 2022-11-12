package com.example.demo_selectcourse.vo;

import java.util.List;
import java.util.Set;

public class SelectCourseReq {                 //參數名稱與Json一致
	
	//SCHOOL
	
	//課程代碼
	private String coursecode;
	
	private Set<String> coursecodeSet;
	
	private List<String> coursecodeList;
	
	//課程名稱
	private String course;
	
	//上課星期
	private String classday;
	
	//上課時間
	private String classtime;
	
	//下課時間
	private String recess;
	
	//課程學分
	private int units;

	/*==========================================================*/
	
	//STUDENT
	
	private String studentId;
	
	private String studentName;

	/*================建構 and get/set==================*/

	public SelectCourseReq() {
		
	}

	public SelectCourseReq(String coursecode, String course, String classday, String classtime, String recess,
			int units, String studentId, String studentName) {
		this.coursecode = coursecode;
		this.course = course;
		this.classday = classday;
		this.classtime = classtime;
		this.recess = recess;
		this.units = units;
		this.studentId = studentId;
		this.studentName = studentName;
	}

	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public Set<String> getCoursecodeSet() {
		return coursecodeSet;
	}

	public void setCoursecodeSet(Set<String> coursecodeSet) {
		this.coursecodeSet = coursecodeSet;
	}

	public List<String> getCoursecodeList() {
		return coursecodeList;
	}

	public void setCoursecodeList(List<String> coursecodeList) {
		this.coursecodeList = coursecodeList;
	}

	public String getClassday() {
		return classday;
	}

	public void setClassday(String classday) {
		this.classday = classday;
	}

	public String getClasstime() {
		return classtime;
	}

	public void setClasstime(String classtime) {
		this.classtime = classtime;
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
