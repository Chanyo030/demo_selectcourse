package com.example.demo_selectcourse.service.impl;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.demo_selectcourse.constants.SelectCourseRtnCode;
import com.example.demo_selectcourse.entity.School;
import com.example.demo_selectcourse.entity.Student;
import com.example.demo_selectcourse.repository.SchoolDao;
import com.example.demo_selectcourse.repository.StudentDao;
import com.example.demo_selectcourse.service.ifs.SelectCourseService;
import com.example.demo_selectcourse.vo.SelectCourseRes;

@Service     // ��Spring Boot�U�� �o�ˤ~����k�b��L�a��Q@Autowired
public class SelectCourseServiceImpl implements SelectCourseService {         //�鷺���i���@

	@Autowired           //�̿�    �w�]�|�̪`�J��H�����O���A�ӿ�ܮe�����۲Ū�����Ӫ`�J�C
	private SchoolDao schoolDao;

	@Autowired          //�̿�    �w�]�|�̪`�J��H�����O���A�ӿ�ܮe�����۲Ū�����Ӫ`�J�C
	private StudentDao studentDao;

	/* ================================================= */

	// SCHOOL
	// �s�W�ҵ{ Create
	@Override      //�мg�B���s�w�q
	public SelectCourseRes createCours(String courseCode, String course, String classDay, String classTime,
			String recess, int units) {
		
		boolean a = classDay(classDay);    //�Q�Υ��L�ȥh�P�_�W�ҬP���榡�O�_�ŦX�]�w���榡

		// �ҵ{�N�X���o���� || �ҵ{�W�٤��o���� || �ҵ{�P�����o����
		if (!StringUtils.hasText(courseCode) || !StringUtils.hasText(course) || !StringUtils.hasText(classDay)) {
//			SelectCourseRes selectCourseRes2 = new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage());
//			return selectCourseRes2;
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage());   //���ܰT�� �ҵ{�N�X���o����
		}

		 //�ҵ{�P���榡���~
		if (a != true) {                        //��P�_���G����true
			return new SelectCourseRes(SelectCourseRtnCode.FORMAT_ERROR.getRtnmessage()); //���ܰT�� �ҵ{�P���榡���~
		}
		
		LocalTime classtimeLocalTime = classTime(classTime);    // �W�Үɶ����~�����G�T�{�O�_���W�Үɶ��o�F��
		LocalTime recessLocalTime = classTime(recess);            // �U�Үɶ����~�����G�T�{�O�_���U�Үɶ��o�F��
		if (!StringUtils.hasLength(classTime) || !StringUtils.hasLength(recess)) {    //���W�B�U�Үɶ��C�p�G�W�B�U�Үɶ�����
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //���ܰT�� �ҵ{�ɶ����o����
		} else if (classtimeLocalTime == null || recessLocalTime == null) {               //�p�G�W�B�U�Үɶ���0
			return new SelectCourseRes(SelectCourseRtnCode.FORMAT_ERROR.getRtnmessage()); //���ܰT�� �ҵ{�ɶ��榡���~
		}

		// �Ǥ��ƿ��~����
		if (units == 0) {    //�p�G�Ǥ��Ƭ�0
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //���ܰT�� �Ǥ��Ƥ��o����
		}
		if (units > 3) {     //�p�G��ת��ҵ{�W�L3�Ǥ����d�򭭨�
			return new SelectCourseRes(SelectCourseRtnCode.CLASS_CODE_EXISTED.getRtnmessage()); //���ܰT�� �W�L�Ǥ��ƭ���
		}

		Optional<School> coursCode = schoolDao.findById(courseCode);      //�z�LDB�ҳ]��PK�h���o�Ǯժ��Ҧ��ҵ{��T

		if (coursCode.isPresent()) {              //�p�G�ڭ̦bPostman�]�w���ҵ{�N�X�w�g�s�b�άO�Q�ϥ�
			return new SelectCourseRes(SelectCourseRtnCode.CLASS_CODE_EXISTED.getRtnmessage()); //���ܰT�� �ҵ{�N�X���o����
		}

		School school = new School(courseCode, course, classDay, classtimeLocalTime, recessLocalTime, units);
		schoolDao.save(school);
		return new SelectCourseRes(school, SelectCourseRtnCode.SUCCESSFUL.getRtnmessage());//�ҵ{�Ыئ��\
	}

	// [�W�ҬP�� ���b]
	public boolean classDay(String classDay) {
		List<String> classWeekDay = Arrays.asList("�P����", "�P���@", "�P���G", "�P���T", "�P���|", "�P����", "�P����");
		return classWeekDay.contains(classDay); // �T�{�ϥΪ̿�J���ѼƬO�_�ŦXList�}�C���Ҧ����e
	} // ����k�u�n���@�ӰѼƤ��ŦX�Ҭ�false

	// [�ɶ� ���b]
	public LocalTime classTime(String allTime) { // (String allTime)���F����Postman��req�g�k�榡!!���ۭq�q�W�١A�P�W��]�m���ѼƵL��
		try {
			// ��r���ഫ��LocalTime
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // �]�w�ڭ̭n���榡("HH:mm")�A�é�J�W��formatter���Ŷ�
			LocalTime classTime = LocalTime.parse(allTime, formatter); // ���J���ɶ��榡(String),�N
																		// (classTime)�ন�ڭ̭n��formatter��"HH:mm"�榡
			return classTime; // �ŦX�N�O�^�ǡA���ŦX�N�Onull(�ߤ@)
		} catch (Exception e) {
			return null;
		}
	}

	// ==============================================================================
	// �ק�ҵ{ Update
	@Override     //�мg�B���s�w�q
	public SelectCourseRes updateCours(String coursCode, String cours, String classDay, String classTime, String recess,
			int units) {
		
		// �Ұ�W�٭ק藍�o����
		if (!StringUtils.hasText(cours)) { // �p�G�ҵ{�W�٬���
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //�ҵ{�W�٤��o����
		}
		
		
		// �W�Үɶ����ର�šA�W�Үɶ��]����ߩ�U�Үɶ�
		// �W�ҬP���榡�O�_�ŦX || �ҵ{�ɶ��ק���~
		LocalTime classtimeLocalTime = classTime(classTime); // LocalTime = 0���ɭ���ܤ覡��null
		LocalTime recessLocalTime = classTime(recess); // �r����LocalTime
		if (!classDay(classDay) || classtimeLocalTime == null || recessLocalTime == null || classtimeLocalTime.isAfter(recessLocalTime)) {
			return new SelectCourseRes(SelectCourseRtnCode.FORMAT_ERROR.getRtnmessage()); //�W�ҬP���榡���ŦX�W�d
		}

		
		// �Ǥ����o�֩�0�Τj��3
		if (units <= 0 || units > 3) {
			return new SelectCourseRes(SelectCourseRtnCode.CREDIT_ERROR.getRtnmessage()); //�Ǥ��ƭק���~
		}

		Optional<School> Update = schoolDao.findById(coursCode);
		if (!Update.isPresent()) { // �p�G�ҵ{���s�b
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //�d�L���ҵ{
		}else {
			School updateClass = Update.get();
			updateClass.setCourse(cours);
			updateClass.setClassDay(classDay);
			updateClass.setClassTime(classtimeLocalTime);
			updateClass.setRecess(recessLocalTime);
			updateClass.setUnits(units);
			schoolDao.save(updateClass);
			//�ҵ{�ק令�\
			return new SelectCourseRes(updateClass,SelectCourseRtnCode.REVISE_SUCCESSFUL.getRtnmessage()); // ���F��JSON�L�X
		} 
	}

	// ==============================================================================
	// �R���ҵ{ Delete
	@Override    //�мg�B���s�w�q
	public SelectCourseRes deleteCours(String coursCode) {

		SelectCourseRes selectCourseRes = new SelectCourseRes();

		if (!StringUtils.hasText(coursCode)) { // �p�G�ҵ{�N�X����
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //�ҵ{�N�X���o����
		}

		Optional<School> deleteCourse = schoolDao.findById(coursCode); // �ݸ�Ʈw�O�_���ҵ{�N�X

		if (!deleteCourse.isPresent()) { // �T�{�ڭn���ҵ{�N�X�O�_�s�b
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //�d�L���ҵ{
		}

		schoolDao.deleteById(coursCode); // �R������T
		selectCourseRes.setMessage("�ҵ{�w�R��");
		return new SelectCourseRes(SelectCourseRtnCode.DELETE_SUCCESSFUL.getRtnmessage());
	}

	// ==============================================================================
	// �ҵ{�d�� class query

	// �@�B�z�L�N�X�d��
	@Override    //�мg�B���s�w�q
	public SelectCourseRes classQuery(String coursCode) {
		
		if (!StringUtils.hasText(coursCode)) { // �p�G�ҵ{�N�X����
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //�ҵ{�N�X���o����
		}

		Optional<School> coursCodeQuery = schoolDao.findById(coursCode);
		if (!coursCodeQuery.isPresent()) {
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //�d�L�ҵ{�N�X
		}else {
			School school = coursCodeQuery.get();
			return new SelectCourseRes(school,SelectCourseRtnCode.QUERY_SUCCESSFUL.getRtnmessage()); 	//�d�ߦ��\
		}
	}

	// �z�L�ҵ{�W�٬d��
	@Override    //�мg�B���s�w�q
	public SelectCourseRes classnameQuery(String course) { // �d�ߪ��ҵ{�N�X�O�_�ŦXDB��Ʈw���N�X �ŦX�N���U�]

		if (!StringUtils.hasText(course)) { // �p�G�ҵ{�W�٬���
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //�ҵ{�W�٤��o����
		}

		List<School> coursNameQuery = schoolDao.findAllByCourse(course);   //�T�{DB�̬O�_���ҵ{�o�ӪF��
		if (coursNameQuery.isEmpty()) {                                             //���A ���n�d�ߪ��ҵ{�O�_����
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage());      //�d�L�ҵ{��T
		}
		return new SelectCourseRes(coursNameQuery,SelectCourseRtnCode.QUERY_SUCCESSFUL.getRtnmessage()); //�d�ߦ��\ �H�W���ҵ{��T
	}
	/*======================================*/

	// STUDENT
	// �s�W�ǥͿ�Ҹ�T
	@Override       //�мg�B���s�w�q
	public SelectCourseRes addStudentSelectCourse(String studentId, String studentName) {

		// �Ǹ��B�m�W���o����
		if (!StringUtils.hasText(studentId) || !StringUtils.hasText(studentName)) {
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //�Ǹ����o����
		}

		Optional<Student> Id = studentDao.findById(studentId);  //�P�_DB�O�_���Ǹ�
		if (Id.isPresent()) {              //���A �p�G�Ǹ��s�b
			return new SelectCourseRes(SelectCourseRtnCode.STUDENT_ID_EXISTED.getRtnmessage()); //���Ǹ��w�Q�ϥ�
		}

		Student student = new Student(studentId, studentName);
		studentDao.save(student);
		return new SelectCourseRes(student,SelectCourseRtnCode.SUCCESSFUL.getRtnmessage());  //�ǥ͸�T��J���\
	}

	// ======================================================================
	// �ק�ǥ͸�T
	@Override    //�мg�B���s�w�q
	public SelectCourseRes updateStudentSelectCourse(String studentId, String studentName) {

		//�Ǹ��B�ǥ͸�T���o����
		if (!StringUtils.hasText(studentId) || !StringUtils.hasText(studentName) ) { // �ǥ͸�T���o����
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //�Ǹ����o����
		}

		Optional<Student> update = studentDao.findById(studentId);
		if (!update.isPresent()) { // �T�{���ǥ͸�T�O�_�s�b
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //�d�L���H
		}else { // ��ǥ͸�T�s�b��
			Student student = new Student();
			student = update.get();
//			Student student = update.get();
			student.setStudentName(studentName);
			studentDao.save(student);
			return new SelectCourseRes(student,SelectCourseRtnCode.REVISE_SUCCESSFUL.getRtnmessage()); //�ǥ͸�T�ק令�\
		}
	}

	// ======================================================================
	// �R���ǥ͸�T
	@Override   //�мg�B���s�w�q
	public SelectCourseRes deleteStudentSelectCourse(String studentId, String studentName) {

		if (!StringUtils.hasText(studentId)) {
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //�Ǹ����o����
		}

		Optional<Student> deleteStudent = studentDao.findById(studentId);
		if (!deleteStudent.isPresent()) {
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //�d�L���H
		}
		studentDao.deleteById(studentId);
		return new SelectCourseRes(SelectCourseRtnCode.DELETE_SUCCESSFUL.getRtnmessage()); //�ǥ͸�T�w�R��
	}

	/* ======================================================== */
	// ��� course selection (���o�W�L10�Ǥ��B�����ۦP�W�٪��ҵ{�B����İ�)
	@Override   //�мg�B���s�w�q
	public SelectCourseRes courseSelection(String studentId, Set<String> coursCode) {
		SelectCourseRes selectCourseRes = new SelectCourseRes();

		// ��Ҿǥ͸�T���o���� || ������ҵ{���o���šC  �ɥR�G��W��.isEmpty() ���౵���šA���i�H����null
		if (!StringUtils.hasText(studentId) || CollectionUtils.isEmpty(coursCode)) {
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage());  //�Х���J�ӤH��T�A���
		}

		// �T�{�O�_�����ҵ{�N�X
		List<School> confirmClass = schoolDao.findAll(); // �Ҧ�DB Shool�����e
		List<String> keepCourse = new ArrayList<>();
		for (School schoolAllCurse : confirmClass) {
			keepCourse.add(schoolAllCurse.getCourseCode());
		}
//		if(!keepCourse.containsAll(courscode)) {
//			selectCourseRes.setMessage("�d�L�ҵ{�N�X �L�k���");
//		}
		boolean b = keepCourse.containsAll(coursCode); // ��J���ҵ{�O�_�ŦXDB�������Ҧ��N�X
		if (b == false) {
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //�d�L�ҵ{�N�X
		}

		// �T�{�O�_�����ǥ�
		Optional<Student> confirmStudentId = studentDao.findById(studentId); // �S��
		if (!confirmStudentId.isPresent()) {
			return new SelectCourseRes(SelectCourseRtnCode.FORMAT_ERROR.getRtnmessage()); //�Ǹ���J���~
		}
		Student getAllStudent = confirmStudentId.get(); // �� --> �z�L�Ǹ�(pk)���o���ǥͪ��Ҧ��ӤH��T

		if (StringUtils.hasText(getAllStudent.getStudentClassCode())) {
			return new SelectCourseRes(SelectCourseRtnCode.HAVE_CLASS_GO_ADDOROUT_CLASS.getRtnmessage()); //�A�w��L�� �Хh�[�h��
		}
		// ==============================================================
		// ��Ҥ��o�W�L10�Ǥ� and�����ۦP�W�٪��ҵ{
		List<String> courseList = new ArrayList<>(); // �Ҧ��ҵ{���M��
		Set<String> courseSet = new HashSet<>(); // ���o���ƪ��ҵ{�M��

		int allUnits = 0; // �N�`�Ǥ��w�]��0
		List<School> getAllCourseCode = schoolDao.findAllByCoursecodeIn(coursCode); // courscode�G�qPK���o�ҵ{��T
		for (School keepAllCourseCodeContent : getAllCourseCode) { // �q�ҵ{�N�X����ҵ{�W��
			courseList.add(keepAllCourseCodeContent.getCourse());
			courseSet.add(keepAllCourseCodeContent.getCourse());
			allUnits = allUnits + keepAllCourseCodeContent.getUnits(); // �q�ҵ{�N�X����Ǥ���T
//			allUnits += keepAllCourseCodeContent.getUnits();              
			keepAllCourseCodeContent.getClassDay(); // �q�ҵ{�N�X����W�ҬP��
			keepAllCourseCodeContent.getClassTime(); // �q�ҵ{�N�X����W�Үɶ�
			keepAllCourseCodeContent.getRecess(); // �q�ҵ{�N�X����U�Үɶ�
		}
		if (courseList.size() != courseSet.size()) { // �Q�βM�檺���� �w�ҵ{�W�٪��קP�_�O�_�����ۦP�W�٪��ҵ{
			return new SelectCourseRes(SelectCourseRtnCode.NOT_SAME_CLASS.getRtnmessage()); //�L�k�[��ۦP�W�٪��ҵ{
		}

		if (allUnits > 10) {
			return new SelectCourseRes(SelectCourseRtnCode.CREDIT_ERROR.getRtnmessage()); //�W�L10�Ǥ�����
		}

		// ==============================================================
		// ���o�İ�

		List<List<String>> lookDayAndGoOutTimeNo = new ArrayList<>();
		List<School> geAllCourseCode = schoolDao.findAllByCoursecodeIn(coursCode); // courscode�G�qPK���o�ҵ{��T
		for (School keepCourseCodeContent : geAllCourseCode) { // foreach�@�ӭӹM�� �a�X��T
			List<String> courseDayAndGoOutTime = new ArrayList<>(); // ��P�� + �W�U�ɶ���i�M���
			courseDayAndGoOutTime.add(keepCourseCodeContent.getClassDay());
			DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm"); // �]�w�r��榡
			String goTimeStr = df.format(keepCourseCodeContent.getClassTime()); // �NLocal time�ন�r��
			String outTimeStr = df.format(keepCourseCodeContent.getRecess());
			courseDayAndGoOutTime.add(goTimeStr); // �[�J�W�Үɶ�
			courseDayAndGoOutTime.add(outTimeStr); // �[�J�U�Үɶ�
			lookDayAndGoOutTimeNo.add(courseDayAndGoOutTime);
		}
		for (int i = 0; i < lookDayAndGoOutTimeNo.size(); i++) {
			String oneClassDay = lookDayAndGoOutTimeNo.get(i).get(0); // ���P��
			for (int j = i + 1; j < lookDayAndGoOutTimeNo.size(); j++) {
				String twoClassDay = lookDayAndGoOutTimeNo.get(j).get(0);
				if (oneClassDay == twoClassDay) {
					// �Ĥ@��
					String oneClassGoDay = lookDayAndGoOutTimeNo.get(i).get(1);
					int oneClassGoDayInt = Integer.parseInt(oneClassGoDay);
					String oneClassoutDay = lookDayAndGoOutTimeNo.get(i).get(2);
					int oneClassOutDayInt = Integer.parseInt(oneClassoutDay);
					// �ĤG��..........
					String twoClassGoDay = lookDayAndGoOutTimeNo.get(j).get(1);
					int twoClassGoDayInt = Integer.parseInt(twoClassGoDay);
					String twoClassoutDay = lookDayAndGoOutTimeNo.get(j).get(2);
					int twoClassOutDayInt = Integer.parseInt(twoClassoutDay);
					if (betweenExclude(oneClassGoDayInt, oneClassOutDayInt, twoClassGoDayInt, twoClassOutDayInt)) {
						return new SelectCourseRes(SelectCourseRtnCode.OUTFID.getRtnmessage()); //�İ� �L�k���
					}
				}
			}
		}
		String courscodeToString = coursCode.toString(); // �NSet<String>�ন�r��A�]���e���n�ᤩ�r�ꫬ�A
		String courscodeStr = courscodeToString.substring(1, courscodeToString.length() - 1); // �h�Y��(�A��)����k�G.substring(�Hindex�Ӻ�/���]�t,�}�C�O��size
																								// �r��O��.length
																								// -1/���]�t���Ӧ�l�����e); Ex
																								// (1,5) = �]�t1�A���]�t5
																								// -->���쪺�O 1- 4

		getAllStudent.setStudentClassCode(courscodeStr);
		studentDao.save(getAllStudent);
		return new SelectCourseRes(getAllStudent,SelectCourseRtnCode.SELECT_COURSE_SUCCESSFUL.getRtnmessage()); //��Ҧ��\
	}

	// ============�����ɶ���k============
	private boolean betweenExclude(int start1, int end1, int start2, int end2) {
//		return start1 >= end2 || end1 >= start2 ; //true= ���İ�
		return !(start1 >= end2) && !(end1 <= start2); // true= �İ�
	}
	// ============�����ɶ���k============

	/* ======================================================== */
	// �[��
	@Override   //�мg�B���s�w�q
	public SelectCourseRes addClass(String studentId, Set<String> coursCode) {
		// �P�_�Ǹ��B�ҵ{�N�X�O�_����
		SelectCourseRes selectCourseRes = new SelectCourseRes();
		String coursecodeStr = coursCode.toString();
		String coursecodeDelet = coursecodeStr.substring(1, coursecodeStr.length() - 1);
		if (!StringUtils.hasText(studentId) || CollectionUtils.isEmpty(coursCode) || !StringUtils.hasText(coursecodeDelet)) {
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage());
		}
		
		// �T�{�O�_�����ҵ{�N�X
				List<School> confirmClass = schoolDao.findAll(); // �Ҧ�DB Shool�����e
				List<String> keepCourse = new ArrayList<>();
				for (School schoolAllCurse : confirmClass) {
					keepCourse.add(schoolAllCurse.getCourseCode());
				}
//				if(!keepCourse.containsAll(courscode)) {
//					selectCourseRes.setMessage("�d�L�ҵ{�N�X �L�k���");
//				}
				boolean b = keepCourse.containsAll(coursCode); // ��J���ҵ{�O�_�ŦXDB�������Ҧ��N�X
				if (b == false) {
					return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //�d�L�ҵ{�N�X
				}

		// �T�{���ǥͬO�_�s�b
		Optional<Student> studentOp = studentDao.findById(studentId);
		if (!studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //�d�L���H
		}

		if (studentOp.isPresent()) {
			Student studentCoursCode = studentOp.get(); // ���o��@�ǥͪ��Ҧ��ӤH��T (�s��Ҹ��)
			if (!StringUtils.hasText(studentCoursCode.getStudentClassCode())) {
				return new SelectCourseRes(SelectCourseRtnCode.NOT_CLASS_GO_ADD_CLASS.getRtnmessage()); //�A�S��L�� �Цb���[��
			}

			String studentOldstr = studentOp.get().getStudentClassCode(); // ��l��Ҹ��
			String studentCourscode = coursCode.toString(); // �Nset�নstring
			String courscodeStr = studentCourscode.substring(1, studentCourscode.length() - 1); // �h����ܵ��G�ҥX�{���e��A��
			studentCoursCode.setStudentClassCode(courscodeStr); // �N�ഫ���A���[��ҵ{��^��T��

			// �N�s�¸�Ʃ�Jlist
			Set<String> oldNewOpClass = new HashSet<>(); // �����Ƥ�����List

			// �h���ҵ{�N�X���r���ΪŮ�
			String[] removeComma = studentOldstr.split(",");
			for (String removeNull : removeComma) {
				oldNewOpClass.add(removeNull.trim());
			}
			String[] removeComma2 = courscodeStr.split(",");
			for (String removeNull2 : removeComma2) {
				oldNewOpClass.add(removeNull2.trim());
			}

			// =================================================================================
			// ��Ҥ��o�W�L10�Ǥ� and�����ۦP�W�٪��ҵ{
			List<String> courseList = new ArrayList<>(); // �Ҧ��ҵ{���M��
			Set<String> courseSet = new HashSet<>(); // ���o���ƪ��ҵ{�M��

			int allUnits = 0; // �N�`�Ǥ��w�]��0
			List<School> getAllCourseCode = schoolDao.findAllByCoursecodeIn(oldNewOpClass); // oldNewOpClass�G�qPK���o�ҵ{��T
			for (School keepAllCourseCodeContent : getAllCourseCode) { // �q�ҵ{�N�X����ҵ{�W��
				courseList.add(keepAllCourseCodeContent.getCourse());
				courseSet.add(keepAllCourseCodeContent.getCourse());
				allUnits = allUnits + keepAllCourseCodeContent.getUnits(); // �q�ҵ{�N�X����Ǥ���T
//				allUnits += keepAllCourseCodeContent.getUnits();              
				keepAllCourseCodeContent.getClassDay(); // �q�ҵ{�N�X����W�ҬP��
				keepAllCourseCodeContent.getClassTime(); // �q�ҵ{�N�X����W�Үɶ�
				keepAllCourseCodeContent.getRecess(); // �q�ҵ{�N�X����U�Үɶ�
			}
			if (courseList.size() != courseSet.size()) { // �Q�βM�檺���� �w�ҵ{�W�٪��קP�_�O�_�����ۦP�W�٪��ҵ{
				return new SelectCourseRes(SelectCourseRtnCode.NOT_SAME_CLASS.getRtnmessage()); //�L�k�[��ۦP�W�٪��ҵ{
			}

			if (allUnits > 10) {
				return new SelectCourseRes(SelectCourseRtnCode.CREDIT_ERROR.getRtnmessage()); //�W�L10�Ǥ����� �L�k���
			}

			// ==============================================================
			// ���o�İ�

			List<List<String>> lookDayAndGoOutTimeNo = new ArrayList<>();
			List<School> geAllCourseCode = schoolDao.findAllByCoursecodeIn(oldNewOpClass); // oldNewOpClass�G�qPK���o�ҵ{��T
			for (School keepCourseCodeContent : geAllCourseCode) { // foreach�@�ӭӹM�� �a�X��T
				List<String> courseDayAndGoOutTime = new ArrayList<>(); // ��P��+�W�U�ɶ���i�M���
				courseDayAndGoOutTime.add(keepCourseCodeContent.getClassDay());
				DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm"); // �]�w�r��榡
				String goTimeStr = df.format(keepCourseCodeContent.getClassTime()); // �NLocal time�ন�r��
				String outTimeStr = df.format(keepCourseCodeContent.getRecess());
				courseDayAndGoOutTime.add(goTimeStr); // �[�J�W�Үɶ�
				courseDayAndGoOutTime.add(outTimeStr); // �[�J�U�Үɶ�
				lookDayAndGoOutTimeNo.add(courseDayAndGoOutTime);
			}
			for (int i = 0; i < lookDayAndGoOutTimeNo.size(); i++) {
				String oneClassDay = lookDayAndGoOutTimeNo.get(i).get(0); // ���P��
				for (int j = i + 1; j < lookDayAndGoOutTimeNo.size(); j++) {
					String twoClassDay = lookDayAndGoOutTimeNo.get(j).get(0);
					if (oneClassDay == twoClassDay) {
						// �Ĥ@��
						String oneClassGoDay = lookDayAndGoOutTimeNo.get(i).get(1);
						int oneClassGoDayInt = Integer.parseInt(oneClassGoDay);
						String oneClassoutDay = lookDayAndGoOutTimeNo.get(i).get(2);
						int oneClassOutDayInt = Integer.parseInt(oneClassoutDay);
						// �ĤG��..........
						String twoClassGoDay = lookDayAndGoOutTimeNo.get(j).get(1);
						int twoClassGoDayInt = Integer.parseInt(twoClassGoDay);
						String twoClassoutDay = lookDayAndGoOutTimeNo.get(j).get(2);
						int twoClassOutDayInt = Integer.parseInt(twoClassoutDay);
						if (betweenExclude(oneClassGoDayInt, oneClassOutDayInt, twoClassGoDayInt, twoClassOutDayInt)) {
							return new SelectCourseRes(SelectCourseRtnCode.OUTFID.getRtnmessage()); //�İ� �L�k���
						}
					}
				}
			}
			String oldNewOpClassStr = oldNewOpClass.toString();
			String removeBrackets = oldNewOpClassStr.substring(1, oldNewOpClassStr.length() - 1);
			studentCoursCode.setStudentClassCode(removeBrackets);
			studentDao.save(studentCoursCode);
			selectCourseRes.setStudent(studentCoursCode); // �s��JSON���L�i�H��ܦbRes�W
			return new SelectCourseRes(SelectCourseRtnCode.CLASS_ADD_SUCCESSFUL.getRtnmessage()); //�ҵ{�[�令�\
		}
		return null;
	}

	/* ======================================================== */
//	�h��
	@Override  //�мg�B���s�w�q
	public SelectCourseRes WithdrawClass(String studentId, List<String> coursCode) {

		// �Ǹ����o����
		if (!StringUtils.hasText(studentId)) {
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //�Ǹ����o����
		}

		Optional<Student> studentOp = studentDao.findById(studentId);
		// �T�{�O�_�����ǥ�
				if (!studentOp.isPresent()) {
					return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //�d�L���H
				}
		Student studentAll = studentOp.get();

		String studentCourseCode = studentAll.getStudentClassCode();
		// ���o�ǥͿ�ҥN�X���ഫ��List
		
		List<String> codeList = new ArrayList<>();
		// �h���ҵ{�N�X���r���ΪŮ�
					String[] removeComma = studentCourseCode.split(",");     //�]��String����foreach�A�ҥH���Q�γr���Ϊťդ��Φ��@�ӭӪ��F��
					for (String removeNull : removeComma) {                   //�A�N���@�ӭӹM���X��
						codeList.add(removeNull.trim());                           //�s�J�}�C��
					}
		
		// ���o�ϥΪ̿�J���h��ҵ{�N�X
		List<School> courscodeList = schoolDao.findAllByCoursecodeIn(coursCode);
		List<String> removeList = new ArrayList<>();
		for (School item : courscodeList) {
			if (codeList.contains(item.getCourseCode())) {
				removeList.add(item.getCourseCode());
			}
		}
		
		//�T�{�O�_���o���
		if(removeList.isEmpty()) {
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage());  //�d�L���ҵ{
		}
		
		codeList.removeAll(removeList);

		studentAll.setStudentClassCode(codeList.toString().substring(1, (codeList.toString().length() - 1)));
		studentDao.save(studentAll);
		return new SelectCourseRes(SelectCourseRtnCode.WITHDRAW_CLASS_SUCCESSFUL.getRtnmessage()); //�h�令�\
	}

	/* ======================================================== */
	// �ǥͩҿ�ҵ{�`�� class Overview (�z�L�Ǹ��d��)
	@Override   //�мg�B���s�w�q
	public SelectCourseRes classOverview(String studentId) {

		if (!StringUtils.hasText(studentId)) {
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //�Ǹ����o����
		}

		Optional<Student> student = studentDao.findById(studentId);
		if (!student.isPresent()) {
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage());      //�d�L���ǥ�
		}
		

		Student studentCalss = student.get();
		String classCode = studentCalss.getStudentClassCode();
		List<String> classCodeList = new ArrayList<>();
		// �γr���j�}�h�ťի� ��iList��
					String[] removeComma = classCode.split(",");
					for (String removeNull : removeComma) {
						classCodeList.add(removeNull.trim());
					}
//					List<School> school1 = new ArrayList<>();
//					school1.addAll(schoolDao.findAllById(classCodeList));
		List<School> school =  schoolDao.findAllById(classCodeList);
		//�^�Ǩ�L�ҵ{��T�νҵ{�N�X�A�d�ߦ��\
		return new SelectCourseRes(school, studentCalss, SelectCourseRtnCode.QUERY_SUCCESSFUL.getRtnmessage());
	}
}
