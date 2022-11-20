package com.example.demo_selectcourse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 跟Spring Boot說這個class是實體類
@Entity   

//告訴spring boot要連動到DB的student這張表
@Table(name = "student")    

public class Student {
	
	//在DB裡student_id是這張表的主鍵(PK)
	@Id                                  
	
	//學號   @Column是用來標識實體類屬性與資料表中欄位的對應關係。
	@Column(name="student_id")       
	
	//DB表裡的student_id與studentId屬性相互對應關係。
	private String studentId;       
	
	//學生姓名
	@Column(name = "student_name") 
	
	//DB表裡的student_name與studentName屬性相互對應關係。
	private String studentName;        
	
	//學生選課的課程代碼
	@Column(name = "class_code")       
	
	//DB表裡的class_code與classCode屬性相互對應關係。
	private String studentClassCode;    

	
	// 有、無參數的建構方法用意是為了能夠一次設定多個屬性的值
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
