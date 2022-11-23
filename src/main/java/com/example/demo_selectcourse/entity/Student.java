package com.example.demo_selectcourse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 跟Spring Boot說這個class是實體類
@Entity   

//告訴spring boot要連動到DB的student這張表
@Table(name = "student")    

public class Student {
	
	//在DB裡student_id是這張表的主鍵(PK)
	//學號   @Column是用來標識實體類屬性與資料表中欄位的對應關係。
	//DB表裡的屬性相互對應關係。
	@Id                                  
	@Column(name="student_id")       
	private String studentId;       
	
	//學生姓名
	@Column(name = "student_name") 
	private String studentName;        
	
	//學生選課的課程代碼
	@Column(name = "course_code")       
	private String courseCode;    

	
	public Student() {
	
	}

	public Student(String studentId, String studentName) {
		this.studentId = studentId;
		this.studentName = studentName;
	}
	

	public Student(String studentId, String studentName, String courseCode) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.courseCode = courseCode;
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


	public String getCourseCode() {
		return courseCode;
	}


	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	
}
