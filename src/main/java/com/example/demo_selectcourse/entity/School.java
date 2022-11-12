package com.example.demo_selectcourse.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // ��Spring Boot���o��class�O������
@Table(name = "school") // �s�ʭ��@�i��
public class School {

	@Id
	@Column(name = "course_code") // �ҵ{�N�X
	private String coursecode;

	@Column(name = "course") // �ҵ{�W��
	private String course;

	@Column(name = "class_day") // �W�ҬP��
	private String classday;

	@Column(name = "class_time") // �W�Үɶ�
	private LocalTime classtime;

	@Column(name = "recess") // �U�Үɶ�
	private LocalTime recess;

	@Column(name = "units") // �ҵ{�Ǥ�
	private int units;

	public School() {

	}

	public School(String coursecode, String course, String classday, LocalTime classtime, LocalTime recess, int units) {
		this.coursecode = coursecode;
		this.course = course;
		this.classday = classday;
		this.classtime = classtime;
		this.recess = recess;
		this.units = units;
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

	public void setCourse(String cours) {
		this.course = cours;
	}

	public String getClassday() {
		return classday;
	}

	public void setClassday(String classday) {
		this.classday = classday;
	}

	public LocalTime getClasstime() {
		return classtime;
	}

	public void setClasstime(LocalTime classtime) {
		this.classtime = classtime;
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
