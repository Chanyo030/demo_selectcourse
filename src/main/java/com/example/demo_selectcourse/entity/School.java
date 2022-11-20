package com.example.demo_selectcourse.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//跟Spring Boot說這個class是實體類
@Entity 

//告訴spring boot要連動到DB的school哪張表
@Table(name = "school") 
public class School {
	
	//在DB裡course_code是這張表的主鍵(PK)
	@Id        
	
	// 課程代碼       @Column是用來標識實體類屬性與資料表中欄位的對應關係。
	@Column(name = "course_code") 
	
	//DB表裡的course_code與courseCode屬性相互對應關係。
	private String courseCode;        

	// 課程名稱
	@Column(name = "course") 
	
	//DB表裡的course與course屬性為相互對應關係。
	private String course;       

	// 上課星期
	@Column(name = "class_day") 
	
	//DB表裡的class_day與classDay屬性為相互對應關係。
	private String classDay;        

	// 上課時間
	@Column(name = "class_time") 
	
	//DB表裡的class_time與classTime屬性為相互對應關係。
	private LocalTime classTime;   
	
    // 下課時間
	@Column(name = "recess") 
	
	//DB表裡的recess與recess屬性為相互對應關係。
	private LocalTime recess;   

	// 課程學分
	@Column(name = "units") 
	
	//DB表裡的units與units屬性為相互對應關係。
	private int units;           

	//有、無參數的建構方法用意是為了能夠一次設定多個屬性的值
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
	
	public School(String course, String classDay, LocalTime classTime, LocalTime recess, int units) {
		this.course = course;
		this.classDay = classDay;
		this.classTime = classTime;
		this.recess = recess;
		this.units = units;
	}

	//Get、Set方法 讓其他class可以設定及取得這些屬性的值
	
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
