package com.example.demo_selectcourse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

	@Id
	@Column(name = "id")            //�Ǹ�
	private String id;
	
	@Column(name = "name")         //�m�W
	private String name;
	
	@Column(name = "class_code")         //�ҵ{�N�X
	private String classcode;
	

	public Student() {
		
	}

	public Student(String id, String name, String classcode) {
		this.id = id;
		this.name = name;
		this.classcode = classcode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClasscode() {
		return classcode;
	}

	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}

}
