package com.example.demo_selectcourse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
	
	@Id
	@Column(name="student_id")
	private String studentId;
	
	@Column(name = "student_name")
	private String studentName;
	
	@Column(name = "class_code")
	private String studentClassCode;

	public Student() {
	
	}

	public Student(String studentId, String studentName) {
		this.studentId = studentId;
		this.studentName = studentName;
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

	public String getStudentClassCode() {
		return studentClassCode;
	}

	public void setStudentClassCode(String studentClassCode) {
		this.studentClassCode = studentClassCode;
	}
	
}
