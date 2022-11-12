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
	// �s�W�ҵ{ Create
	@PostMapping(value = "/api/createcourse")
	public SelectCourseRes createCourse(@RequestBody SelectCourseReq req) {
		SelectCourseRes selectCourseRes = new SelectCourseRes();
		selectCourseRes = selectCourseService.createCours(req.getCoursecode(), req.getCourse(), req.getClassday(),
				req.getClasstime(), req.getRecess(), req.getUnits());
		return selectCourseRes;
	}

	// ==============================================================================
	// �ק�ҵ{ update
	@PostMapping(value = "/api/updateCours")
	public SelectCourseRes updateCours(@RequestBody SelectCourseReq req) {
		SelectCourseRes selectCourseRes = new SelectCourseRes();
		selectCourseRes = selectCourseService.updateCours(req.getCoursecode(), req.getCourse(), req.getClassday(),
				req.getClasstime(), req.getRecess(), req.getUnits());
		return selectCourseRes;
	}

	// ==============================================================================/
	// �R���ҵ{ Delete
	@PostMapping(value = "/api/delete")
	public SelectCourseRes deleteCours(@RequestBody SelectCourseReq req) {
		SelectCourseRes selectCourseRes = new SelectCourseRes();
		selectCourseRes = selectCourseService.deleteCours(req.getCoursecode());
		return selectCourseRes;
	}

	// ==============================================================================
	// �ҵ{�d�� class query
	// �z�L�N�X�d��
	@PostMapping(value = "/api/classQuery")
	public SelectCourseRes classQuery(@RequestBody SelectCourseReq req) {
		SelectCourseRes selectCourseRes = new SelectCourseRes();
		selectCourseRes = selectCourseService.classQuery(req.getCoursecode());
		return selectCourseRes;
	}

	// �z�L�ҵ{�W�٬d��
	@PostMapping(value = "/api/classnameQuery")
	public SelectCourseRes classnameQuery(@RequestBody SelectCourseReq req) {
		SelectCourseRes selectCourseRes = new SelectCourseRes();
		selectCourseRes = selectCourseService.classnameQuery(req.getCourse());
		return selectCourseRes;
	}
	
	
	/* ====================STUDENT=======================*/
	
	// �s�W�ǥͿ�Ҹ�T
		@PostMapping(value = "/api/addStudentSelectCourse")
		public SelectCourseRes addStudentSelectCourse(@RequestBody SelectCourseReq req) {
			SelectCourseRes selectCourseRes = new SelectCourseRes();
			selectCourseRes = selectCourseService.addStudentSelectCourse(req.getStudentId(), req.getStudentName());
			return selectCourseRes;
		}
		
		// ==============================================================================
		// �ק�ǥͿ�Ҹ�T
		@PostMapping(value = "/api/updateStudentSelectCourse")
		public SelectCourseRes updateStudentSelectCourse(@RequestBody SelectCourseReq req) {
			SelectCourseRes selectCourseRes = new SelectCourseRes();
			selectCourseRes = selectCourseService.updateStudentSelectCourse(req.getStudentId(), req.getStudentName());
			return selectCourseRes;
		}
		
		// ==============================================================================
		// �R���ǥͿ�Ҹ�T
		@PostMapping(value = "/api/deleteStudentSelectCourse")
		public SelectCourseRes deleteStudentSelectCourse(@RequestBody SelectCourseReq req) {
			SelectCourseRes selectCourseRes = new SelectCourseRes();
			selectCourseRes = selectCourseService.deleteStudentSelectCourse(req.getStudentId(), req.getStudentName());
			return selectCourseRes;
		}

		// ==============================================================================
		// ��� course selection  (���o�W�L10�Ǥ��B�����ۦP�W�٪��ҵ{�B����İ�)
		@PostMapping(value = "/api/courseSelection")
		public SelectCourseRes courseSelection(@RequestBody SelectCourseReq req) {
			SelectCourseRes selectCourseRes = new SelectCourseRes();
			selectCourseRes = selectCourseService.courseSelection(req.getStudentId(), req.getCoursecodeSet());
			return selectCourseRes;
		}

		// ==============================================================================
		// �[�� add class
		@PostMapping(value = "/api/addClass")
		public SelectCourseRes addClass(@RequestBody SelectCourseReq req) {
			SelectCourseRes selectCourseRes = new SelectCourseRes();
			selectCourseRes = selectCourseService.addClass(req.getStudentId(), req.getCoursecodeSet());
			return selectCourseRes;
		}
		
		// ==============================================================================
		//�h�� withdraw class
		@PostMapping(value = "/api/WithdrawClass")
		public SelectCourseRes WithdrawClass(@RequestBody SelectCourseReq req) {
			SelectCourseRes selectCourseRes = new SelectCourseRes();
			selectCourseRes = selectCourseService.WithdrawClass(req.getStudentId(), req.getCoursecodeList());
			return selectCourseRes;
			
		}

		// ==============================================================================
			// �ǥͩҿ�ҵ{�`�� class Overview (�z�L�Ǹ��d��)
		@PostMapping(value = "/api/classOverview")
			public SelectCourseRes classOverview(@RequestBody SelectCourseReq req) {
			SelectCourseRes selectCourseRes = new SelectCourseRes();
			selectCourseRes = selectCourseService.classOverview(req.getStudentId());
				return selectCourseRes;
			
		}
	
}
