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
	private String classDay;
	
	//上課時間
	private String classTime;
	
	//下課時間
	private String recess;
	
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

	//Get、Set方法 讓其他class可以設定及取得這些屬性的值
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
