package com.example.demo_selectcourse.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // 跟Spring Boot說這個class是實體類
@Table(name = "school") // 告訴spring boot要連動到DB的school這張表
public class School {

	@Id        //在DB裡course_code是這張表的主鍵(PK)
	@Column(name = "course_code") // 課程代碼       @Column是用來標識實體類屬性與資料表中欄位的對應關係。
	private String courseCode;        //DB表裡的course_code與courseCode屬性相互對應關係。

	@Column(name = "course") // 課程名稱
	private String course;       //DB表裡的course與course屬性為相互對應關係。

	@Column(name = "class_day") // 上課星期
	private String classDay;        //DB表裡的class_day與classDay屬性為相互對應關係。

	@Column(name = "class_time") // 上課時間
	private LocalTime classTime;   //DB表裡的class_time與classTime屬性為相互對應關係。

	@Column(name = "recess") // 下課時間
	private LocalTime recess;   //DB表裡的recess與recess屬性為相互對應關係。

	@Column(name = "units") // 課程學分
	private int units;           //DB表裡的units與units屬性為相互對應關係。

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
