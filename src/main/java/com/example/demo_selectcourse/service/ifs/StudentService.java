package com.example.demo_selectcourse.service.ifs;

import com.example.demo_selectcourse.entity.Student;

public interface StudentService {
	//Pii個人資訊
		public Student setPii (String studentid, String name, String classcode);

}
