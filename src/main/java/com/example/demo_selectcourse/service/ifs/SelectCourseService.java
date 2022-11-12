package com.example.demo_selectcourse.service.ifs;

import java.util.List;
import java.util.Set;

import com.example.demo_selectcourse.vo.SelectCourseRes;

public interface SelectCourseService {
	// SCHOOL
	// �s�W�ҵ{ Create
	public SelectCourseRes createCours(String courscode, String course, String classday, String classtime,
			String recess, int units);

	// �ק�ҵ{ Update
	public SelectCourseRes updateCours(String courscode, String course, String classday, String classtime,
			String recess, int units);

	// �R���ҵ{ Delete
	public SelectCourseRes deleteCours(String courscode);

	// �ҵ{�d�� class query (�z�L�N�X�d��)
	public SelectCourseRes classQuery(String courscode);

	// �ҵ{�d�� class name query (�z�L�Ұ�W�٬d��)
	public SelectCourseRes classnameQuery(String course);

	/* ============================================================= */
	// STUDENT

	// �s�W�ǥͿ�Ҹ�T
	public SelectCourseRes addStudentSelectCourse(String studentId, String studentName);

	// �ק�ǥͿ�Ҹ�T
	public SelectCourseRes updateStudentSelectCourse(String studentId, String studentName);

	// �R���ǥͿ�Ҹ�T
	public SelectCourseRes deleteStudentSelectCourse(String studentId, String studentName);

	// ��� course selection
	public SelectCourseRes courseSelection(String studentId, Set<String> courscode);

	// �[�� add class
	public SelectCourseRes addClass(String studentId, Set<String> courscode);
	
	// �h�� withdraw class
	public SelectCourseRes WithdrawClass(String studentId, List<String> courscode);
	
	// �ǥͩҿ�ҵ{�`�� class Overview (�z�L�Ǹ��d��)
	public SelectCourseRes classOverview(String studentId);

}
