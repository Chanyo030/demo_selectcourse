package com.example.demo_selectcourse.service.ifs;

import java.util.List;
import java.util.Set;

import com.example.demo_selectcourse.vo.SelectCourseRes;

public interface SelectCourseService {
	// SCHOOL
	
	// �s�W(�Ы�)�ҵ{ Create
	public SelectCourseRes createCourse(String coursCode, String courseName, String courseday, String startTime,
			String endTime, int units);

	// �ק�ҵ{ Update
	public SelectCourseRes updateCourse(String coursCode, String courseName, String courseday, String startTime,
			String endTime, int units);

	// �R���ҵ{ Delete
	public SelectCourseRes deleteCourse(String courseCode);

	// �ҵ{�d�� class query (�z�L�N�X�d��)
	public SelectCourseRes courseQuery(String courseCode);

	// �ҵ{�d�� class name query (�z�L�Ұ�W�٬d��)
	public SelectCourseRes courseNameQuery(String courseName);

	/* ============================================================= */
	// STUDENT

	// �s�W�ǥ͸�T
	public SelectCourseRes addStudentInfo(String studentId, String studentName);

	// �ק�ǥ͸�T
	public SelectCourseRes updateStudentInfo(String studentId, String studentName);

	// �R���ǥ͸�T
	public SelectCourseRes deleteStudentInfo(String studentId, String studentName);

	// ��� course selection
	public SelectCourseRes courseSelection(String studentId, Set<String> courseCode);

	// �[�� add class
	public SelectCourseRes addCourse(String studentId, Set<String> courseCode);
	
	// �h�� withdraw class
	public SelectCourseRes WithdrawCourse(String studentId, List<String> courseCode);
	
	// �ǥͩҿ�ҵ{�`�� class Overview (�z�L�Ǹ��d��)
	public SelectCourseRes courseOverview(String studentId);

}
