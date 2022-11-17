package com.example.demo_selectcourse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity   // ��Spring Boot���o��class�O������
@Table(name = "student")    //�i�Dspring boot�n�s�ʨ�DB��student�o�i��
public class Student {
	
	@Id                                  //�bDB��student_id�O�o�i���D��(PK)
	@Column(name="student_id")  //�Ǹ�       @Column�O�ΨӼ��ѹ������ݩʻP��ƪ���쪺�������Y�C
	private String studentId;       //DB��̪�student_id�PstudentId�ݩʬۤ��������Y�C
	
	@Column(name = "student_name") //�ǥͩm�W
	private String studentName;        //DB��̪�student_name�PstudentName�ݩʬۤ��������Y�C
	
	@Column(name = "class_code")       //�ǥͿ�Ҫ��ҵ{�N�X
	private String studentClassCode;    //DB��̪�class_code�PclassCode�ݩʬۤ��������Y�C

	
	////���B�L�Ѽƪ��غc��k�ηN�O���F����@���]�w�h���ݩʪ���
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
