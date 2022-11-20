package com.example.demo_selectcourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_selectcourse.service.ifs.SelectCourseService;
import com.example.demo_selectcourse.vo.SelectCourseReq;
import com.example.demo_selectcourse.vo.SelectCourseRes;

@RestController // 用於回傳JSON、XML等資料。相當於@Controller + @RequestBody
public class SelectCourseController {

	@Autowired // 依賴 預設會依注入對象的類別型態來選擇容器中相符的物件來注入。
	private SelectCourseService selectCourseService;

	/* ====================SCHOOL======================= */
	// 新增課程 Create

	// @PostMapping用來處理post類型的http请求
	@PostMapping(value = "/api/createcourse")

	// @RequestBody用來標記資料存取層(介面層interface) req是用來接收外部資料並放入api裡使用
	public SelectCourseRes createCourse(@RequestBody SelectCourseReq req) {

		// new一個selectCourseRes的空間(new的作用是為一個物件（Object）分配記憶體)
		SelectCourseRes selectCourseRes = new SelectCourseRes();

		// 輸入想要新增的課程並套用Service方法
		selectCourseRes = selectCourseService.createCours(req.getCourseCode(), req.getCourse(), req.getClassDay(),
				req.getClassTime(), req.getRecess(), req.getUnits());

		// 最後回傳
		return selectCourseRes;
	}

	// ==============================================================================
	// 修改課程 update
	// @PostMapping用來處理post類型的http请求
	@PostMapping(value = "/api/updateCours") 
	
	// @RequestBody用來標記資料存取層(介面層interface)  req是用來接收外部資料並放入api裡使用
	public SelectCourseRes updateCours(@RequestBody SelectCourseReq req) { 
									
		// new一個selectCourseRes的空間(new的作用是為一個物件（Object）分配記憶體)
		SelectCourseRes selectCourseRes = new SelectCourseRes(); 
		
		// 輸入想要修改的課程並套用Service方法
		selectCourseRes = selectCourseService.updateCours(req.getCourseCode(), req.getCourse(), req.getClassDay(), 
				req.getClassTime(), req.getRecess(), req.getUnits());
		
		// 最後回傳
		return selectCourseRes; 
	}

	// ==============================================================================/
	// 刪除課程 Delete
	
	// @PostMapping用來處理post類型的http请求
	@PostMapping(value = "/api/delete") 
	
	// @RequestBody用來標記資料存取層(介面層interface) // req是用來接收外部資料並放入api裡使用
	public SelectCourseRes deleteCours(@RequestBody SelectCourseReq req) { 
																			
		// new一個selectCourseRes的空間(new的作用是為一個物件（Object）分配記憶體)
		SelectCourseRes selectCourseRes = new SelectCourseRes(); 
		
		// 輸入想要刪除的課程並套用Service方法
		selectCourseRes = selectCourseService.deleteCours(req.getCourseCode()); 
		
		// 最後回傳
		return selectCourseRes; 
	}

	// ==============================================================================
	// 課程查詢 class query
	
	// 透過代碼查詢
	// @PostMapping用來處理post類型的http请求
	@PostMapping(value = "/api/classQuery") 
	public SelectCourseRes classQuery(@RequestBody SelectCourseReq req) { 
		
		// @RequestBody用來標記資料存取層(介面層interface) req是用來接收外部資料並放入api裡使用
		
		// new一個selectCourseRes的空間(new的作用是為一個物件（Object）分配記憶體)
		SelectCourseRes selectCourseRes = new SelectCourseRes(); 
		
		// 輸入想要查詢的課程並套用Service方法
		selectCourseRes = selectCourseService.classQuery(req.getCourseCode()); // 輸入想要查詢的課程並套用Service方法
		
		// 最後回傳
		return selectCourseRes;
	}

	// 透過課程名稱查詢
	
	// @PostMapping用來處理post類型的http请求
	@PostMapping(value = "/api/classnameQuery") 
	
	// @RequestBody用來標記資料存取層(介面層interface) req是用來接收外部資料並放入api裡使用
	public SelectCourseRes classnameQuery(@RequestBody SelectCourseReq req) { 						
		
		// new一個selectCourseRes的空間(new的作用是為一個物件（Object）分配記憶體)
		SelectCourseRes selectCourseRes = new SelectCourseRes(); 
		
		// 輸入想要查詢的課程並套用Service方法
		selectCourseRes = selectCourseService.classnameQuery(req.getCourse()); 
		
		// 最後回傳
		return selectCourseRes; 
	}

	/* ====================STUDENT======================= */

	// 新增學生選課資訊
	
	// @PostMapping用來處理post類型的http请求
	@PostMapping(value = "/api/addStudentSelectCourse") 
	
	// @RequestBody用來標記資料存取層(介面層interface) req是用來接收外部資料並放入api裡使用
	public SelectCourseRes addStudentSelectCourse(@RequestBody SelectCourseReq req) { 
		
		// new一個selectCourseRes的空間(new的作用是為一個物件（Object）分配記憶體)
		SelectCourseRes selectCourseRes = new SelectCourseRes();
		
		// 輸入想要新增的學生選課資訊並套用Service方法
		selectCourseRes = selectCourseService.addStudentSelectCourse(req.getStudentId(), req.getStudentName()); 
		
		// 最後回傳
		return selectCourseRes;
	}

	// ==============================================================================
	// 修改學生選課資訊
	
	// @PostMapping用來處理post類型的http请求
	@PostMapping(value = "/api/updateStudentSelectCourse") 
	public SelectCourseRes updateStudentSelectCourse(@RequestBody SelectCourseReq req) {
		
		// new一個selectCourseRes的空間(new的作用是為一個物件（Object）分配記憶體)
		SelectCourseRes selectCourseRes = new SelectCourseRes(); 
		
		// 輸入想要修改的學生選課資訊並套用Service方法
		selectCourseRes = selectCourseService.updateStudentSelectCourse(req.getStudentId(), req.getStudentName()); 
		
		// 最後回傳
		return selectCourseRes;
	}

	// ==============================================================================
	// 刪除學生選課資訊
	
	// @PostMapping用來處理post類型的http请求
	@PostMapping(value = "/api/deleteStudentSelectCourse") 
	
	// @RequestBody用來標記資料存取層(介面層interface)  req是用來接收外部資料並放入api裡使用
	public SelectCourseRes deleteStudentSelectCourse(@RequestBody SelectCourseReq req) { 
		
		// new一個selectCourseRes的空間(new的作用是為一個物件（Object）分配記憶體)
		SelectCourseRes selectCourseRes = new SelectCourseRes(); 
		
		// 輸入想要刪除的學生選課資訊並套用Service方法
		selectCourseRes = selectCourseService.deleteStudentSelectCourse(req.getStudentId(), req.getStudentName()); 
		
		// 最後回傳
		return selectCourseRes; 
	}

	// ==============================================================================
	// 選課 course selection (不得超過10學分、不能選相同名稱的課程、不能衝堂)
	
	// @PostMapping用來處理post類型的http请求
	@PostMapping(value = "/api/courseSelection") 
	
	// @RequestBody用來標記資料存取層(介面層interface)  req是用來接收外部資料並放入api裡使用
	public SelectCourseRes courseSelection(@RequestBody SelectCourseReq req) { 
		
		// new一個selectCourseRes的空間(new的作用是為一個物件（Object）分配記憶體)
		SelectCourseRes selectCourseRes = new SelectCourseRes(); 
		
		// 輸入想要選課資訊並套用Service方法
		selectCourseRes = selectCourseService.courseSelection(req.getStudentId(), req.getCourseCodeSet()); 
		
		// 最後回傳
		return selectCourseRes; 
	}

	// ==============================================================================
	// 加選 add class
	
	// @PostMapping用來處理post類型的http请求
	@PostMapping(value = "/api/addClass") 
	
	// @RequestBody用來標記資料存取層(介面層interface)  req是用來接收外部資料並放入api裡使用
	public SelectCourseRes addClass(@RequestBody SelectCourseReq req) { 
		
		// new一個selectCourseRes的空間(new的作用是為一個物件（Object）分配記憶體)
		SelectCourseRes selectCourseRes = new SelectCourseRes(); 
		
		// 輸入想要加選的課並套用Service方法
		selectCourseRes = selectCourseService.addClass(req.getStudentId(), req.getCourseCodeSet()); 
		
		// 最後回傳
		return selectCourseRes; 
	}

	// ==============================================================================
	// 退選 withdraw class
	
	// @PostMapping用來處理post類型的http请求
	@PostMapping(value = "/api/WithdrawClass") 
	
	// @RequestBody用來標記資料存取層(介面層interface)  req是用來接收外部資料並放入api裡使用
	public SelectCourseRes WithdrawClass(@RequestBody SelectCourseReq req) { 
		
		// new一個selectCourseRes的空間(new的作用是為一個物件（Object）分配記憶體)
		SelectCourseRes selectCourseRes = new SelectCourseRes(); 
		
		// 輸入想要退選的課並套用Service方法
		selectCourseRes = selectCourseService.WithdrawClass(req.getStudentId(), req.getCourseCodeList()); 
		
		// 最後回傳
		return selectCourseRes; 
	}

	// ==============================================================================
	// 學生所選課程總覽 class Overview (透過學號查詢)
	
	// @PostMapping用來處理post類型的http请求
	@PostMapping(value = "/api/classOverview")
	
	// @RequestBody用來標記資料存取層(介面層interface)  req是用來接收外部資料並放入api裡使用
	public SelectCourseRes classOverview(@RequestBody SelectCourseReq req) { 
		
		// new一個selectCourseRes的空間(new的作用是為一個物件（Object）分配記憶體)
		SelectCourseRes selectCourseRes = new SelectCourseRes(); 
		
		// 輸入想要查詢的學生學號並套用Service方法
		selectCourseRes = selectCourseService.classOverview(req.getStudentId()); 
		
		// 最後回傳
		return selectCourseRes; 
	}

}
