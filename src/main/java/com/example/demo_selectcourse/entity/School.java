package com.example.demo_selectcourse.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // ��Spring Boot���o��class�O������
@Table(name = "school") // �i�Dspring boot�n�s�ʨ�DB��school�o�i��
public class School {

	@Id        //�bDB��course_code�O�o�i���D��(PK)
	@Column(name = "course_code") // �ҵ{�N�X       @Column�O�ΨӼ��ѹ������ݩʻP��ƪ���쪺�������Y�C
	private String courseCode;        //DB��̪�course_code�PcourseCode�ݩʬۤ��������Y�C

	@Column(name = "course") // �ҵ{�W��
	private String course;       //DB��̪�course�Pcourse�ݩʬ��ۤ��������Y�C

	@Column(name = "class_day") // �W�ҬP��
	private String classDay;        //DB��̪�class_day�PclassDay�ݩʬ��ۤ��������Y�C

	@Column(name = "class_time") // �W�Үɶ�
	private LocalTime classTime;   //DB��̪�class_time�PclassTime�ݩʬ��ۤ��������Y�C

	@Column(name = "recess") // �U�Үɶ�
	private LocalTime recess;   //DB��̪�recess�Precess�ݩʬ��ۤ��������Y�C

	@Column(name = "units") // �ҵ{�Ǥ�
	private int units;           //DB��̪�units�Punits�ݩʬ��ۤ��������Y�C

	//���B�L�Ѽƪ��غc��k�ηN�O���F����@���]�w�h���ݩʪ���
	public School() {           

	}

	public School(String courseCode, String course, String classDay, LocalTime classTime, LocalTime recess, int units) {
		this.courseCode = courseCode;
		this.course = course;
		this.classDay = classDay;
		this.classTime = classTime;
		this.recess = recess;
		this.units = units;
	}

	//Get�BSet��k ����Lclass�i�H�]�w�Ψ��o�o���ݩʪ���
	
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
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

	public LocalTime getClassTime() {
		return classTime;
	}

	public void setClassTime(LocalTime classTime) {
		this.classTime = classTime;
	}

	public LocalTime getRecess() {
		return recess;
	}

	public void setRecess(LocalTime recess) {
		this.recess = recess;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

}
