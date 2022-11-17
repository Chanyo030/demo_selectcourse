package com.example.demo_selectcourse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity   // 跟Spring Boot說這個class是實體類
@Table(name = "student")    //告訴spring boot要連動到DB的student這張表
public class Student {
	
	@Id                                  //在DB裡student_id是這張表的主鍵(PK)
	@Column(name="student_id")  //學號       @Column是用來標識實體類屬性與資料表中欄位的對應關係。
	private String studentId;       //DB表裡的student_id與studentId屬性相互對應關係。
	
	@Column(name = "student_name") //學生姓名
	private String studentName;        //DB表裡的student_name與studentName屬性相互對應關係。
	
	@Column(name = "class_code")       //學生選課的課程代碼
	private String studentClassCode;    //DB表裡的class_code與classCode屬性相互對應關係。

	
	////有、無參數的建構方法用意是為了能夠一次設定多個屬性的值
	public Student() {
	
	}

	public Student(String studentId, String studentName) {
		this.studentId = studentId;
		this.studentName = studentName;
	}

	
	//Get、Set方法 讓其他class可以設定及取得這些屬性的值
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
