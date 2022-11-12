package com.example.demo_selectcourse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


//@SpringBootTest(classes = DemoSelectcourseApplication.class)    //classes =後放的是 專案底下Application的名稱
class DemoSelectcourseApplicationTests {

	@Test
	void contextLoads() { 
		List<String> list = new ArrayList<>();    //在for迴圈時不能刪除元素，會位置錯亂
		list.add("A");
		list.add("B");
		list.add("C");
		for(String item: list) {
			System.out.println(item);
			list.remove(item);
		}
	}

}
