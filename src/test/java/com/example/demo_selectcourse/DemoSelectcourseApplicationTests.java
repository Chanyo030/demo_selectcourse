package com.example.demo_selectcourse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


//@SpringBootTest(classes = DemoSelectcourseApplication.class)    //classes =��񪺬O �M�ש��UApplication���W��
class DemoSelectcourseApplicationTests {

	@Test
	void contextLoads() { 
		List<String> list = new ArrayList<>();    //�bfor�j��ɤ���R�������A�|��m����
		list.add("A");
		list.add("B");
		list.add("C");
		for(String item: list) {
			System.out.println(item);
			list.remove(item);
		}
	}

}
