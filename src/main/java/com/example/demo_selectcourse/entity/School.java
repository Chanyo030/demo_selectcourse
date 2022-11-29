package com.example.demo_selectcourse.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//��Spring Boot���o��class�O������
@Entity 

//�i�Dspring boot�n�s�ʨ�DB��school���i��
@Table(name = "school") 
public class School {
	
	// �bDB��course_code�O�o�i���D��(PK)
	// �ҵ{�N�X (@Column�O�ΨӼ��ѹ������ݩʻP��ƪ���쪺�������Y)
	// DB��̪��ݩʬۤ��������Y�C
	
	@Id        
	@Column(name = "course_code") 
	private String courseCode;        

	// �ҵ{�W��
	@Column(name = "course_name") 
	private String courseName;       

	// �W�ҬP��
	@Column(name = "course_day") 
	private String courseDay;        

	// �}�l�ɶ�
	@Column(name = "start_time") 
	private LocalTime startTime;   
	
    // �����ɶ�
	@Column(name = "end_time") 
	private LocalTime endTime;   

	// �ҵ{�Ǥ�
	@Column(name = "units") 
	private int units;           

	//���B�L�Ѽƪ��غc��k�ηN�O���F����@���]�w�h���ݩʪ���
	public School() {           

	}
	
	public School(String courseName, String courseDay, LocalTime startTime, LocalTime endTime,
			int units) {
		this.courseName = courseName;
		this.courseDay = courseDay;
		this.startTime = startTime;
		this.endTime = endTime;
		this.units = units;
	}
	
	public void updateSchool(String courseName, String courseDay, LocalTime startTime, LocalTime endTime,
			int units) {
		this.courseName = courseName;
		this.courseDay = courseDay;
		this.startTime = startTime;
		this.endTime = endTime;
		this.units = units;
	}
	

	public School(String courseCode, String courseName, String courseDay, LocalTime startTime, LocalTime endTime,
			int units) {
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.courseDay = courseDay;
		this.startTime = startTime;
		this.endTime = endTime;
		this.units = units;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDay() {
		return courseDay;
	}

	public void setCourseDay(String courseDay) {
		this.courseDay = courseDay;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}


}
