package com.example.demo_selectcourse.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "school")
public class School {
	
	@Id
	@Column(name = "course_code")   //課程代碼
	private String coursecode;    
	
	@Column(name = "course")         //課程名稱
	private String course;         
	
	@Column(name = "class_day")     //星期
	private String classday;
	
	@Column(name = "class_time")    //課堂開始時間
	private LocalTime classtime;
	
	@Column(name = "recess")         //課堂結束時間
	private LocalTime recess;
	
	@Column(name = "units")          //課程學分
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

	public void setCourse(String course) {
		this.course = course;
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

	public LocalTime getRecess() {
		return recess;
	}

	public void setRecess(LocalTime recess) {
		this.recess = recess;
	}

	public void setClasstime(LocalTime classtime) {
		this.classtime = classtime;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}
}
