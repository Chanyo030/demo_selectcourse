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
	@Id                                  
	
	//�Ǹ�   @Column�O�ΨӼ��ѹ������ݩʻP��ƪ���쪺�������Y�C
	@Column(name="student_id")       
	
	//DB��̪�student_id�PstudentId�ݩʬۤ��������Y�C
	private String studentId;       
	
	//�ǥͩm�W
	@Column(name = "student_name") 
	
	//DB��̪�student_name�PstudentName�ݩʬۤ��������Y�C
	private String studentName;        
	
	//�ǥͿ�Ҫ��ҵ{�N�X
	@Column(name = "class_code")       
	
	//DB��̪�class_code�PclassCode�ݩʬۤ��������Y�C
	private String studentClassCode;    

	
	// ���B�L�Ѽƪ��غc��k�ηN�O���F����@���]�w�h���ݩʪ���
	public Student() {
	
	}

	public Student(String studentId, String studentName) {
		this.studentId = studentId;
		this.studentName = studentName;
	}

	
	//Get�BSet��k ����Lclass�i�H�]�w�Ψ��o�o���ݩʪ���
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
