package com.example.demo_selectcourse.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//跟Spring Boot說這個class是實體類
@Entity 

//告訴spring boot要連動到DB的school哪張表
@Table(name = "school") 
public class School {
	
	// 在DB裡course_code是這張表的主鍵(PK)
	// 課程代碼 (@Column是用來標識實體類屬性與資料表中欄位的對應關係)
	// DB表裡的屬性相互對應關係。
	
	@Id        
	@Column(name = "course_code") 
	private String courseCode;        

	// 課程名稱
	@Column(name = "course_name") 
	private String courseName;       

	// 上課星期
	@Column(name = "course_day") 
	private String courseDay;        

	// 開始時間
	@Column(name = "start_time") 
	private LocalTime startTime;   
	
    // 結束時間
	@Column(name = "end_time") 
	private LocalTime endTime;   

	// 課程學分
	@Column(name = "units") 
	private int units;           

	//有、無參數的建構方法用意是為了能夠一次設定多個屬性的值
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
