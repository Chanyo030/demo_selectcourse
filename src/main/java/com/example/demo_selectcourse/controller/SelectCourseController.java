package com.example.demo_selectcourse.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_selectcourse.service.ifs.SelectCourseService;
import com.example.demo_selectcourse.vo.SelectCourseReq;
import com.example.demo_selectcourse.vo.SelectCourseRes;

@RestController
public class SelectCourseController {

	@Autowired
	private SelectCourseService selectCourseService;

	/* ====================SCHOOL=======================*/
	// 新增課程 Create
	@PostMapping(value = "/api/createcourse")
	public SelectCourseRes createCourse(@RequestBody SelectCourseReq req) {
		SelectCourseRes selectCourseRes = new SelectCourseRes();
		selectCourseRes = selectCourseService.createCours(req.getCoursecode(), req.getCourse(), req.getClassday(),
				req.getClasstime(), req.getRecess(), req.getUnits());
		return selectCourseRes;
	}

	// ==============================================================================
	// 修改課程 update
	@PostMapping(value = "/api/updateCours")
	public SelectCourseRes updateCours(@RequestBody SelectCourseReq req) {
		SelectCourseRes selectCourseRes = new SelectCourseRes();
		selectCourseRes = selectCourseService.updateCours(req.getCoursecode(), req.getCourse(), req.getClassday(),
				req.getClasstime(), req.getRecess(), req.getUnits());
		return selectCourseRes;
	}

	// ==============================================================================/
	// 刪除課程 Delete
	@PostMapping(value = "/api/delete")
	public SelectCourseRes deleteCours(@RequestBody SelectCourseReq req) {
		SelectCourseRes selectCourseRes = new SelectCourseRes();
		selectCourseRes = selectCourseService.deleteCours(req.getCoursecode());
		return selectCourseRes;
	}

	// ==============================================================================
	// 課程查詢 class query
	// 透過代碼查詢
	@PostMapping(value = "/api/classQuery")
	public SelectCourseRes classQuery(@RequestBody SelectCourseReq req) {
		SelectCourseRes selectCourseRes = new SelectCourseRes();
		selectCourseRes = selectCourseService.classQuery(req.getCoursecode());
		return selectCourseRes;
	}

	// 透過課程名稱查詢
	@PostMapping(value = "/api/classnameQuery")
	public SelectCourseRes classnameQuery(@RequestBody SelectCourseReq req) {
		SelectCourseRes selectCourseRes = new SelectCourseRes();
		selectCourseRes = selectCourseService.classnameQuery(req.getCourse());
		return selectCourseRes;
	}
	
	
	/* ====================STUDENT=======================*/
	
	// 新增學生選課資訊
		@PostMapping(value = "/api/addStudentSelectCourse")
		public SelectCourseRes addStudentSelectCourse(@RequestBody SelectCourseReq req) {
			SelectCourseRes selectCourseRes = new SelectCourseRes();
			selectCourseRes = selectCourseService.addStudentSelectCourse(req.getStudentId(), req.getStudentName());
			return selectCourseRes;
		}
		
		// ==============================================================================
		// 修改學生選課資訊
		@PostMapping(value = "/api/updateStudentSelectCourse")
		public SelectCourseRes updateStudentSelectCourse(@RequestBody SelectCourseReq req) {
			SelectCourseRes selectCourseRes = new SelectCourseRes();
			selectCourseRes = selectCourseService.updateStudentSelectCourse(req.getStudentId(), req.getStudentName());
			return selectCourseRes;
		}
		
		// ==============================================================================
		// 刪除學生選課資訊
		@PostMapping(value = "/api/deleteStudentSelectCourse")
		public SelectCourseRes deleteStudentSelectCourse(@RequestBody SelectCourseReq req) {
			SelectCourseRes selectCourseRes = new SelectCourseRes();
			selectCourseRes = selectCourseService.deleteStudentSelectCourse(req.getStudentId(), req.getStudentName());
			return selectCourseRes;
		}

		// ==============================================================================
		// 選課 course selection  (不得超過10學分、不能選相同名稱的課程、不能衝堂)
		@PostMapping(value = "/api/courseSelection")
		public SelectCourseRes courseSelection(@RequestBody SelectCourseReq req) {
			SelectCourseRes selectCourseRes = new SelectCourseRes();
			selectCourseRes = selectCourseService.courseSelection(req.getStudentId(), req.getCoursecodeSet());
			return selectCourseRes;
		}

		// ==============================================================================
		// 加選 add class
		@PostMapping(value = "/api/addClass")
		public SelectCourseRes addClass(@RequestBody SelectCourseReq req) {
			SelectCourseRes selectCourseRes = new SelectCourseRes();
			selectCourseRes = selectCourseService.addClass(req.getStudentId(), req.getCoursecodeSet());
			return selectCourseRes;
		}
		
		// ==============================================================================
		//退選 withdraw class
		@PostMapping(value = "/api/WithdrawClass")
		public SelectCourseRes WithdrawClass(@RequestBody SelectCourseReq req) {
			SelectCourseRes selectCourseRes = new SelectCourseRes();
			selectCourseRes = selectCourseService.WithdrawClass(req.getStudentId(), req.getCoursecodeList());
			return selectCourseRes;
			
		}

		// ==============================================================================
			// 學生所選課程總覽 class Overview (透過學號查詢)
		@PostMapping(value = "/api/classOverview")
			public SelectCourseRes classOverview(@RequestBody SelectCourseReq req) {
			SelectCourseRes selectCourseRes = new SelectCourseRes();
			selectCourseRes = selectCourseService.classOverview(req.getStudentId());
				return selectCourseRes;
			
		}
	
}
