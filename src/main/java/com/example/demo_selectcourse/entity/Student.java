package com.example.demo_selectcourse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// ��Spring Boot���o��class�O������
@Entity   

//�i�Dspring boot�n�s�ʨ�DB��student�o�i��
@Table(name = "student")    

public class Student {
	
	//�bDB��student_id�O�o�i���D��(PK)
	//�Ǹ�   @Column�O�ΨӼ��ѹ������ݩʻP��ƪ���쪺�������Y�C
	//DB��̪��ݩʬۤ��������Y�C
	@Id                                  
	@Column(name="student_id")       
	private String studentId;       
	
	//�ǥͩm�W
	@Column(name = "student_name") 
	private String studentName;        
	
	//�ǥͿ�Ҫ��ҵ{�N�X
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
