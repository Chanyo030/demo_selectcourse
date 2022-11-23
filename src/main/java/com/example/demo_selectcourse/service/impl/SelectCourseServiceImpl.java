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

import com.example.demo_selectcourse.constants.SelectCourseMessageCode;
import com.example.demo_selectcourse.entity.School;
import com.example.demo_selectcourse.entity.Student;
import com.example.demo_selectcourse.repository.SchoolDao;
import com.example.demo_selectcourse.repository.StudentDao;
import com.example.demo_selectcourse.service.ifs.SelectCourseService;
import com.example.demo_selectcourse.vo.SelectCourseRes;

@Service // ��Spring Boot�U�� �o�ˤ~����k�b��L�a��Q@Autowired
public class SelectCourseServiceImpl implements SelectCourseService { // �鷺���i���@

	@Autowired // �̿� �w�]�|�̪`�J��H�����O���A�ӿ�ܮe�����۲Ū�����Ӫ`�J�C ��DB��ƶi��s�W�B�ק�B�R��
	private SchoolDao schoolDao;

	@Autowired // �̿� �w�]�|�̪`�J��H�����O���A�ӿ�ܮe�����۲Ū�����Ӫ`�J�C
	private StudentDao studentDao;

	/* ================================================= */

	// SCHOOL
	// �s�W�ҵ{ Create
	@Override // �мg�B���s�w�q
	public SelectCourseRes createCourse(String courseCode, String courseName, String courseDay, String startTime,
			String endTime, int units) {

		// �ҵ{�N�X���� || �ҵ{�W�٬��� || �ҵ{�P������ -> ���ܰT�� �ҵ{��T���o����
		if (!StringUtils.hasText(courseCode) || !StringUtils.hasText(courseName) || !StringUtils.hasText(courseDay)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// �N��J���Ұ�}�l�B�����ɶ��a�J classTime ��k �P�_�O�_�u�����o����� ->
		// �N�P�_���G��J�W��classTimeLocalTime�BrecessLocalTime���Ŷ�
		LocalTime startTimeLocalTime = checkTime(startTime);
		LocalTime endTimeLocalTime = checkTime(endTime); //

		// �W�ҬP���榡�O�_�ŦX || �ҵ{�ɶ��ק藍�o��NULL (�ɥR�GLocalTime = 0 ���ɭ���ܤ覡��null) ||
				// �Ұ�}�l�ɶ�����W�L�Ұ󵲧��ɶ�
				if (!checkCourseDayFormat(courseDay) || startTimeLocalTime == null || endTimeLocalTime == null
						|| startTimeLocalTime.isAfter(endTimeLocalTime)) {

					// �^�Ǵ��ܰT�� �W�ҬP���B�ɶ��榡���ŦX�W�d
					return new SelectCourseRes(SelectCourseMessageCode.FORMAT_FAIL.getMessage());
				}
		
		// �Ǥ��ƿ��~�����G�Ǥ����o�֩�0(����)�Τj��3 -> �Ǥ��Ƥ��ŦX�W�d
		if (units <= 0 || units > 3) {
			return new SelectCourseRes(SelectCourseMessageCode.CREDIT_FAIL.getMessage());
		}

		// �T�{DB�̬O�_���ҵ{�N�X
		Optional<School> schoolOp = schoolDao.findById(courseCode);

		// �T�{�ҵ{�N�X�w�g�s�b�άO�Q�ϥ� -> ���ܰT�� �ҵ{�N�X���o����
		if (schoolOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.CLASS_CODE_EXISTED.getMessage());
		}

		// �ҵ{�N�X���s�b�άO�S�Q�ϥ� -> ���ܰT�� �ҵ{�Ыئ��\
		School school = new School(courseCode, courseName, courseDay, startTimeLocalTime, endTimeLocalTime, units);
		schoolDao.save(school);
		return new SelectCourseRes(school, SelectCourseMessageCode.SUCCESSFUL.getMessage());//
	}

	// ==============================================================================
	// �ק�ҵ{ Update
	@Override // �мg�B���s�w�q
	public SelectCourseRes updateCourse(String courseCode, String courseName, String courseDay, String startTime,
			String endTime, int units) {

		// �ҵ{�N�X���� || �ҵ{�W�٬��� || �ҵ{�P������ -> ���ܰT�� �ҵ{��T���o����
		if (!StringUtils.hasText(courseCode) || !StringUtils.hasText(courseName) || !StringUtils.hasText(courseDay)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// �N��J���Ұ�}�l�B�����ɶ��a�J classTime ��k �P�_�O�_�u�����o����� ->
		// �N�P�_���G��J�W��classTimeLocalTime�BrecessLocalTime���Ŷ�
		LocalTime startTimeLocalTime = checkTime(startTime);
		LocalTime endTimeLocalTime = checkTime(endTime);

		// �W�ҬP���榡�O�_�ŦX || �ҵ{�ɶ��ק藍�o��NULL (�ɥR�GLocalTime = 0 ���ɭ���ܤ覡��null) ||
		// �Ұ�}�l�ɶ�����W�L�Ұ󵲧��ɶ�
		if (!checkCourseDayFormat(courseDay) || startTimeLocalTime == null || endTimeLocalTime == null
				|| startTimeLocalTime.isAfter(endTimeLocalTime)) {

			// �^�Ǵ��ܰT�� �W�ҬP���B�ɶ��榡���ŦX�W�d
			return new SelectCourseRes(SelectCourseMessageCode.FORMAT_FAIL.getMessage());
		}

		// �Ǥ����o�֩�0�Τj��3 -> ���ܰT�� �Ǥ��ƭק���~
		if (units <= 0 || units > 3) {
			return new SelectCourseRes(SelectCourseMessageCode.CREDIT_FAIL.getMessage());
		}

		// �T�{DB�O�_���ҵ{�N�X
		Optional<School> schoolOp = schoolDao.findById(courseCode);

		// �ҵ{���s�b -> ���ܰT�� �d�L���ҵ{
		if (!schoolOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		} else {
			School school = new School(courseName, courseDay, startTimeLocalTime, endTimeLocalTime, units);
			schoolDao.save(school);

			// �^�Ǵ��ܰT�� �ҵ{�ק令�\
			return new SelectCourseRes(school, SelectCourseMessageCode.REVISE_SUCCESSFUL.getMessage());
		}
	}

	// ==============================================================================
	// �R���ҵ{ Delete
	@Override // �мg�B���s�w�q
	public SelectCourseRes deleteCourse(String courseCode) {

		// �ҵ{�N�X���� -> ���ܰT�� �ҵ{�N�X���o����
		if (!StringUtils.hasText(courseCode)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// �T�{DB�O�_���ҵ{�N�X
		Optional<School> schoolOp = schoolDao.findById(courseCode);

		// �ҵ{�N�X���s�b -> �T������ �d�L���ҵ{
		if (!schoolOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// �R�����ҵ{��T -> ���ܰT�� �ҵ{�w�R��
		schoolDao.deleteById(courseCode);
		return new SelectCourseRes(SelectCourseMessageCode.DELETE_SUCCESSFUL.getMessage());
	}

	// ==============================================================================
	// �ҵ{�d�� class query

	// �z�L�N�X�d��
	@Override // �мg�B���s�w�q
	public SelectCourseRes courseQuery(String courseCode) {

		// �ҵ{�N�X���� -> ���ܰT�� �ҵ{�N�X���o����
		if (!StringUtils.hasText(courseCode)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// �T�{DB�O�_���ҵ{�N�X
		Optional<School> schoolOp = schoolDao.findById(courseCode);

		// �ҵ{�N�X���s�b -> ���ܰT�� �d�L���ҵ{�N�X
		if (!schoolOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		} else {

			// �z�L�d�߽ҵ{�N�X���o�ҵ{��T
			School school = schoolOp.get();

			// �^�Ǭd�ߪ��ҵ{��T�δ��ܰT�� �d�ߦ��\
			return new SelectCourseRes(school, SelectCourseMessageCode.QUERY_SUCCESSFUL.getMessage());
		}
	}

	// �z�L�ҵ{�W�٬d��
	@Override // �мg�B���s�w�q
	public SelectCourseRes courseNameQuery(String courseName) {

		// �ҵ{�W�٬��� -> ���ܰT�� �ҵ{�W�٤��o����
		if (!StringUtils.hasText(courseName)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// �T�{DB�O�_���ҵ{�W��
		List<School> schoolList = schoolDao.findAllByCourseName(courseName);

		// �d�ߪ��ҵ{���� -> ���ܰT�� �d�L�ҵ{��T
		if (schoolList.isEmpty()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// �^�Ǭd�ߪ��ҵ{��T�δ��ܰT�� �d�ߦ��\
		return new SelectCourseRes(schoolList, SelectCourseMessageCode.QUERY_SUCCESSFUL.getMessage());
	}
	/* ====================================== */

	// STUDENT
	// �s�W�ǥ͸�T
	@Override // �мg�B���s�w�q
	public SelectCourseRes addStudentInfo(String studentId, String studentName) {

		// �Ǹ��B�m�W���� -> ���ܰT�� �Ǹ��B�m�W���o����
		if (!StringUtils.hasText(studentId) || !StringUtils.hasText(studentName)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// �T�{DB�O�_���Ǹ�
		Optional<Student> studentOp = studentDao.findById(studentId);

		// �Ǹ��s�b -> ���ܰT�� ���Ǹ��w�Q�ϥ�
		if (studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.STUDENT_ID_EXISTED.getMessage());
		}

		// �N�ϥΪ̿�J���Ǹ��ξǥͩm�W��J�W�� student ���Ŷ�
		Student student = new Student(studentId, studentName);

		// �N�Ȧs�JDB
		studentDao.save(student);

		// �^�Ǵ��ܰT�� �ǥ͸�T�s�W���\
		return new SelectCourseRes(student, SelectCourseMessageCode.SUCCESSFUL.getMessage());
	}

	// ======================================================================
	// �ק�ǥ͸�T
	@Override // �мg�B���s�w�q
	public SelectCourseRes updateStudentInfo(String studentId, String studentName) {

		// �Ǹ��B�ǥͩm�W���� -> ���ܰT�� �Ǹ��B�ǥͩm�W���o����
		if (!StringUtils.hasText(studentId) || !StringUtils.hasText(studentName)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// �T�{DB�O�_���Ǹ�
		Optional<Student> studentOp = studentDao.findById(studentId);

		// �Ǹ����s�b -> ���ܰT�� �d�L���H
		if (!studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());

			// �_�h (�Ǹ��s�b)
		} else {

			// �z�L�Ǹ����o�Ӿǥͪ��Ҧ���T
			Student student = studentOp.get();

			// �]�m�ϥΪ̿�J���ǥͩm�W
			student.setStudentName(studentName);

			// �s�JDB
			studentDao.save(student);

			// �^�Ǵ��ܰT�� �ǥ͸�T�ק令�\
			return new SelectCourseRes(student, SelectCourseMessageCode.REVISE_SUCCESSFUL.getMessage());
		}
	}

	// ======================================================================
	// �R���ǥ͸�T
	@Override // �мg�B���s�w�q
	public SelectCourseRes deleteStudentInfo(String studentId, String studentName) {

		// �Ǹ��B�m�W���� -> ���ܰT�� �Ǹ��B�m�W���o����
		if (!StringUtils.hasText(studentId) || !StringUtils.hasText(studentName)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// �T�{DB�O�_���Ǹ�
		Optional<Student> studentOp = studentDao.findById(studentId);

		// �Ǹ����s�b -> ���ܰT�� �d�L���H
		if (!studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// �Ǹ��s�b -> �R��DB�̪���@�ǥ͸�T
		studentDao.deleteById(studentId);

		// �^�Ǵ��ܰT�� �ǥ͸�T�w�R��
		return new SelectCourseRes(SelectCourseMessageCode.DELETE_SUCCESSFUL.getMessage());
	}

	/* ======================================================== */
	// ��� course selection (���o�W�L10�Ǥ��B�����ۦP�W�٪��ҵ{�B����İ�)
	@Override // �мg�B���s�w�q
	public SelectCourseRes courseSelection(String studentId, Set<String> courseCodeSet) {

		// �a�J������k�G�N��J���ҵ{�N�X�a�JcheckCourseCode ��k �h�P�_�O�_�u�����o�ⵧ��� ->
		// �N�P�_���G��J�W��studentIdAndcourseCodeRes���Ŷ�
		SelectCourseRes studentIdAndCourseCodeRes = checkCourseCode(studentId, courseCodeSet);

		// ��P�_���G������null�A�N���ҵ{�N�X�w�s�b -> ��^���ܰT�� �d�L�ҵ{�N�X
		if (studentIdAndCourseCodeRes != null) {
			return studentIdAndCourseCodeRes;
		}

		// �T�{DB�O�_���Ǹ�
		Optional<Student> studentOp = studentDao.findById(studentId);

		// �Ǹ���J���~ -> ���ܰT�� �Ǹ����s�b
		if (!studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.FORMAT_FAIL.getMessage());
		}

		// �z�L�Ǹ�(PK)���o���ǥͪ��Ҧ��ӤH��T
		Student student = studentOp.get();

		// �p�G�ǥͷ�e�w��L�� -> ���ܰT�� �A�w��L�ҡA�Хh�[�h��
		if (StringUtils.hasText(student.getCourseCode())) {
			return new SelectCourseRes(SelectCourseMessageCode.HAVE_CLASS_GO_ADDOROUT_CLASS.getMessage());
		}

		// ==============================================================
		// �����ۦP�W�٪��ҵ{ and ��Ҥ��o�W�L10�Ǥ�

		// �`���ѡG�Ыؤ@��List�BSet�M����O�s��ǥͿ�J���ҵ{�W��
		// �Ыؤ@�Ӧs���Ҧ��ҵ{�W�٪��M�� (���ҵ{�W�٥�)
		List<String> courseNameList = new ArrayList<>();

		// �Ыؤ@�Ӧs�񤣱o���ƪ��ҵ{�W�ٲM�� (���ҵ{�W�٥�)
		Set<String> courseNameSet = new HashSet<>();

		// �N�`�Ǥ��w�]��0
		int allUnits = 0;

		// �N�w�ŦX����ҥN�X��J��Ʈw�A���X�h���ҵ{��T
		List<School> allCourseCodeList = schoolDao.findAllByCourseCodeIn(courseCodeSet);

		// �ѩ�W���M����o���ҵ{��ƬO�]�t'�Ҧ�'��T�A�]���ϥ�foreach�N���@�ӭӹM���X�� ���O�x�s
		for (School schoolItem : allCourseCodeList) {

			// ���X�ҵ{�W�� �é�Jlist�M��
			courseNameList.add(schoolItem.getCourseName());

			// ���X�ҵ{�W�� ��Jset�M��
			courseNameSet.add(schoolItem.getCourseName());

			// ���X�ҵ{�Ǥ� -> �Ǥ��[�`
			allUnits = allUnits + schoolItem.getUnits();
		}

		// �Q�βM����קP�_�O�_�����ۦP�W�٪��ҵ{ (��Set�M��PList�M�檺���פ��۵� �N�����ۦP�W�٪��ҵ{)
		if (courseNameList.size() != courseNameSet.size()) {

			// �^�Ǵ��ܰT�� �L�k�[��ۦP�W�٪��ҵ{
			return new SelectCourseRes(SelectCourseMessageCode.NOT_SAME_CLASS.getMessage());
		}

		// ���ҾǤ��`�p�W�L10 -> ���ܰT�� �W�L10�Ǥ�����
		if (allUnits > 10) {
			return new SelectCourseRes(SelectCourseMessageCode.CREDIT_FAIL.getMessage());
		}

		// ==============================================================
		// ���o�İ�

		// �N��J���ҵ{�N�X�a�J�ҵ{�Ĭ𪺤�k�h�P�_ -> �N�P�_���G��J�W��res���Ŷ�
		SelectCourseRes courseCodeRes = checkCourseConflict(courseCodeSet);

		// ��P�_���G������null�A�N���İ� -> �^�Ǵ��ܰT��
		if (courseCodeRes != null) {
			return courseCodeRes;
		}

		// �N�ϥΪ̿�J���ҵ{�N�X����ƫ��A Set<String> �ন�r��(���F�ŦXDB�榡)
		String courseCodeStr = courseCodeSet.toString();

		// ���X�ഫ���r��|���A���A�]���h�Y���A�� ��k�G.substring(�Hindex�Ӻ�/���]�t,�}�C�O��size�B�r��O��.length() -1
		// ���]�t���Ӧ�l�����e)
		// Ex.(1,5) = �]�t1�A���]�t5 --> ���쪺�|�O 1 - 4
		String removeBracketsCourseCode = courseCodeStr.substring(1, courseCodeStr.length() - 1);

		// �N�ҵ{�N�X�s�J�Ӿǥ�student
		student.setCourseCode(removeBracketsCourseCode);

		// �A�s�iDB��
		studentDao.save(student);

		// �^�Ǵ��ܰT�� ��Ҧ��\
		return new SelectCourseRes(student, SelectCourseMessageCode.SELECT_COURSE_SUCCESSFUL.getMessage());
	}

	/* ======================================================== */
	// �[��
	@Override // �мg�B���s�w�q
	public SelectCourseRes addCourse(String studentId, Set<String> courseCodeSet) {

		// �P�_�ǥͥ[�諸�ҵ{�O�_�P��Ʈw����T�۲�
		SelectCourseRes studentIdAndCourseCodeRes = checkCourseCode(studentId, courseCodeSet);

		// ��result������null -> �^�Ǵ��ܰT�� �Ǹ��B�ҵ{�N�X���o�Ťάd�L���ҵ{
		if (studentIdAndCourseCodeRes != null) {
			return studentIdAndCourseCodeRes;
		}

		// �T�{DB�O�_���Ǹ�
		Optional<Student> studentOp = studentDao.findById(studentId);

		// �Ǹ����s�b -> ���ܰT�� �d�L���H
		if (!studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// �z�LDB���ҵ{�N�X���o��@�ǥͪ��Ҧ��ӤH��T (�[�諸�ҵ{���)
		Student student = studentOp.get();

		// �ǥͪ��ҵ{�N�X���� -> ���ܰT�� �A�S��L�ҡA�Хh���
		if (!StringUtils.hasText(student.getCourseCode())) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_CLASS_GO_ADD_CLASS.getMessage());
		}

		// �qDB���o�ǥ�'��l'����Ҹ�T(�Ĥ@���諸��)
		String studentOldStr = studentOp.get().getCourseCode();

		// �N�ϥΪ̿�J��'�[��'�ҵ{�N�X����ƫ��A Set<String> �ন�r��
		String studentAddCoursCodeStr = courseCodeSet.toString();

		// �h���e��A��
		String bracketsRemoveStudentAddCoursCode = studentAddCoursCodeStr.substring(1,
				studentAddCoursCodeStr.length() - 1);

		// �Ыؤ@�Ӧs��s�½ҵ{��set�M��
		Set<String> newCourseCodeSet = new HashSet<>();

		// �N��l����Ҹ�T�γr���j�}�h�ť� -> ��i�M���
		String[] removeCommaOld = studentOldStr.split(",");
		for (String removeNullOld : removeCommaOld) {
			newCourseCodeSet.add(removeNullOld.trim());
		}

		// �N�[�諸��Ҹ�T�γr���j�}�h�ť� -> ��iSetList�M���
		String[] removeCommaNew = bracketsRemoveStudentAddCoursCode.split(",");
		for (String removeNullNew : removeCommaNew) {
			newCourseCodeSet.add(removeNullNew.trim());
		}

		// =================================================================================
		// ��Ҥ��o�W�L10�Ǥ� and �����ۦP�W�٪��ҵ{

		// �P�_�s�½ҵ{�O�_���ۦP�ҵ{�B�Ǥ��ƬO�_�W�L10�Ǥ� -> �N�P�_���G��J�W��courseNameConflictAndUnitsRes���Ŷ�
		SelectCourseRes courseNameConflictAndUnitsRes = checkCourseNameConflictAndUnits(newCourseCodeSet);

		// ��P�_���G������null�A�N��榡���� -> �^�Ǵ��ܰT�� �L�k�[��ۦP�W�٪��ҵ{�B�`�Ǥ��W�L10
		if (courseNameConflictAndUnitsRes != null) {
			return courseNameConflictAndUnitsRes;
		}
		// ==============================================================
		// ���o�İ�

		// �N��J���ҵ{�N�X�a�J�ҵ{�Ĭ𪺤�k�h�P�_ -> �N�P�_���G��J�W��courseConflictRes(�ҵ{�Ĭ�)���Ŷ�
		SelectCourseRes courseConflictRes = checkCourseConflict(courseCodeSet);

		// ��P�_���G������null�A�N���İ� -> �^�Ǵ��ܰT�� �L�k���
		if (courseConflictRes != null) {
			return courseConflictRes;
		}

		// �N�񦳭즳�ҵ{�B�[��ҵ{��T��set�M���ഫ���r�ꫬ�A
		String courseCodeStr = newCourseCodeSet.toString();

		// �åh���]string�Ӳ��ͪ��e��A��
		String removeBracketsStr = courseCodeStr.substring(1, courseCodeStr.length() - 1);

		// �N�s���ҵ{�N�X�л\�즳�ҵ{
		student.setCourseCode(removeBracketsStr);

		// �s�^DB��
		studentDao.save(student);

		// �x�s���\ -> �^�ǩҦ��[��ҵ{�δ��ܰT�� �ҵ{�[�令�\
		return new SelectCourseRes(student, SelectCourseMessageCode.CLASS_ADD_SUCCESSFUL.getMessage());
	}

	/* ======================================================== */
//	�h��
	@Override // �мg�B���s�w�q
	public SelectCourseRes WithdrawCourse(String studentId, List<String> courseCodeList) {

		// �Ǹ����šB�ҵ{�N�X���� -> ���ܰT�� �Ǹ��B�ҵ{�N�X���o����
		if (!StringUtils.hasText(studentId) || CollectionUtils.isEmpty(courseCodeList)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// �T�{DB�O�_���Ǹ�
		Optional<Student> studentOp = studentDao.findById(studentId);

		// ���ǥͤ��s�b -> ���ܰT�� �d�L���H
		if (!studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// �z�LDB�Ǹ�(PK)���o��@�ǥͩҦ���T
		Student studentAllInfo = studentOp.get();

		// �z�L��@�ǥ͸�T���o��ҥN�X�æs��JstudentCourseCode
		String studentCourseCode = studentAllInfo.getCourseCode();

		// �Ыؤ@�Ӧs��ǥͽҵ{�N�X���M�� (����)
		List<String> studentCourseCodeList = new ArrayList<>();

		// �N���o�ǥͽҵ{�N�X�γr���j�}�h�ť� -> ��i�M���
		String[] removeCommaCourseCode = studentCourseCode.split(",");
		for (String removeNullCourseCode : removeCommaCourseCode) {
			studentCourseCodeList.add(removeNullCourseCode.trim());
		}

		// �N�ǥͽҵ{�N�X�M���JDB�� -> ��ŦX���ҵ{�N�X��J�W��allCourseCodeList���Ŷ�(�֦�school���Ҧ��ҵ{��T)
		List<School> allCourseCodeList = schoolDao.findAllByCourseCodeIn(courseCodeList);

		// �Ыؤ@�Ӧs��h��ҵ{�N�X���M�� (����)
		List<String> withdrawCourseCodeList = new ArrayList<>();

		// �N�ǥͰh�諸�ҵ{�N�X�γr���j�}�h�ť� -> ��i�M���
		for (School schoolItem : allCourseCodeList) {
			if (studentCourseCodeList.contains(schoolItem.getCourseCode())) {
				withdrawCourseCodeList.add(schoolItem.getCourseCode());
			}
		}

		// ���J���h��ҵ{�N�X���šBDB�̵L���ҵ{�N�X -> ���ܰT�� �d�L���ҵ{
		if (withdrawCourseCodeList.isEmpty()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// ��즳���ҵ{�N�X�P�h��ҵ{�N�X�۲� -> �qstudentCourseCodeList�̧R���h�諸�ҵ{
		studentCourseCodeList.removeAll(withdrawCourseCodeList);

		// �NstudentCodeList�M��Ҧ��ҵ{��T��r��B�h�e��A�� -> �N�ҵ{�N�X��^���Ӿǥ�
		studentAllInfo.setCourseCode(
				studentCourseCodeList.toString().substring(1, (studentCourseCodeList.toString().length() - 1)));

		// �s�^DB
		studentDao.save(studentAllInfo);

		// �x�s���\ -> �^�Ǿǥͽҵ{��T�δ��ܰT�� �h�令�\
		return new SelectCourseRes(studentAllInfo, SelectCourseMessageCode.WITHDRAW_CLASS_SUCCESSFUL.getMessage());
	}

	/* ======================================================== */
	// �ǥͩҿ�ҵ{�`�� class Overview (�z�L�Ǹ��d��)
	@Override // �мg�B���s�w�q
	public SelectCourseRes courseOverview(String studentId) {

		// �Ǹ����� -> ���ܰT�� �Ǹ����o����
		if (!StringUtils.hasText(studentId)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// �T�{DB�O�_���Ǹ�
		Optional<Student> studentOp = studentDao.findById(studentId);

		// �Ǹ����s�b -> ���ܰT�� �d�L���ǥ�
		if (!studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// �z�LDB�Ǹ�(PK)���o��@�ǥͩҦ���T
		Student studentAllInfo = studentOp.get();

		// �z�L��@�ǥ͸�T���o��ҥN�X�æs��JstudentCourseCode
		String studentCourseCode = studentAllInfo.getCourseCode();

		// �Ыؤ@�Ӧs��ǥͽҵ{�N�X���M��
		List<String> courCodeList = new ArrayList<>();

		// �N�ǥͽҵ{�N�X�γr���j�}�h�ť� -> ��i�M���
		String[] removeCommaCourseCode = studentCourseCode.split(",");
		for (String removeNullCourseCode : removeCommaCourseCode) {
			courCodeList.add(removeNullCourseCode.trim());
		}

		// �N�ǥͽҵ{�N�X�M���JDB�� -> ��ŦX���ҵ{��J�W��studentAllCourseCode���Ŷ�(�֦�school���Ҧ��ҵ{��T)
		List<School> studentAllCourseCode = schoolDao.findAllById(courCodeList);

		// �^�Ǿǥ;֦����Ҧ��ҵ{�N�X�νҵ{��T ���ܰT�� �d�ߦ��\
		return new SelectCourseRes(studentAllCourseCode, studentAllInfo,
				SelectCourseMessageCode.QUERY_SUCCESSFUL.getMessage());
	}

	// =============================������k======================================
	// [�W�ҬP�� ���b]
	private boolean checkCourseDayFormat(String courseDay) {

		// ��Q�n���P���榡�]�w��}�C��
		List<String> classWeekDaysList = Arrays.asList("�P����", "�P���@", "�P���G", "�P���T", "�P���|", "�P����", "�P����");

		// �T�{�ϥΪ̿�J���ѼƬO�_�ŦXList�}�C���Ҧ����e (����k�u�n���@�ӰѼƤ��ŦX�Ҭ�false)
		return classWeekDaysList.contains(courseDay);
	}

	// [�ɶ� ���b]
	// (String allTime)���F����Postman��req�g�k�榡 (�ۭq�q�W�١A�P�W��]�m���ѼƵL��)
	private LocalTime checkTime(String allTime) {
		try {
			// ��r���ഫ��LocalTime -> �]�w�ڭ̭n���榡("HH:mm")�A�é�J�W��formatter���Ŷ�
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

			// ���J���ɶ��榡(String),�NclassTime�ন�ڭ̭n��formatter��"HH:mm"�榡
			LocalTime classTime = LocalTime.parse(allTime, formatter);

			// �ŦX�N�O�^�ǡA���ŦX�N�Onull(�ߤ@)
			return classTime;
		} catch (Exception e) {
			return null;
		}
	}

	// �P�_������ҵ{�O�_�ŦXDB��T (�P�_�ҵ{�O�_�s�b)
	private SelectCourseRes checkCourseCode(String studentId, Set<String> courseCodeSet) {

		// �Ǹ����� || ������ҵ{�N�X���� -> ���ܰT�� �Ǹ��B�ҵ{�N�X���o�� (�ɥR�G��W��.isEmpty() ���౵���šA���i�H����null)
		if (!StringUtils.hasText(studentId) || CollectionUtils.isEmpty(courseCodeSet)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}
		
		// �NDB�Ҧ��ҵ{��T��JconfirmClassList�M�� (�֦�school���Ҧ��ҵ{��T)
		List<School> confirmClassList = schoolDao.findAll();

		// �Ыؤ@�Ӧs��ҵ{�N�X���}�C�M�� (���F����Ʈw�ΨϥΪ̿�J����ҥN�X�A�]���ഫ���ۦPList�h�����)
		List<String> tempCourseCodeList = new ArrayList<>();

		// �N�Ҧ��ҵ{��T�@�ӭӹM���ç�ҵ{�N�X�s���keepCourseCodeList�M��
		for (School schoolItem : confirmClassList) {
			tempCourseCodeList.add(schoolItem.getCourseCode());
		}

		// �T�{��J���ҵ{�N�X�O�_�ŦXDB�̦����ҵ{�N�X
		boolean courseCodeBoolean = tempCourseCodeList.containsAll(courseCodeSet);

		// ��ҵ{�N�X���s�b -> ���ܰT�� �d�L�ҵ{�N�X
		if (courseCodeBoolean == false) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// ��ҵ{�N�X�s�b -> �^��null
		return null;
	}

	// checkCourseConflict �ҵ{�Ĭ�
	private SelectCourseRes checkCourseConflict(Set<String> courseCodeSet) {

		// �Ыؤ@�����hList�M�� (�Ω󤧫���ҵ{�O�_�İ�G���ҵ{�P���B�ɶ���T�Φh��ҵ{)
		// List = allDayAndTimeList -> {�P��,�}�l�ɶ�,�����ɶ�}, List<String> = courseDayAndTime
		// -> {�P��} {�}�l�ɶ�} {�����ɶ�}
		List<List<String>> allDayAndTimeList = new ArrayList<>();

		// �N�ǥͽҵ{�N�X�M���JDB�� -> ��ŦX���ҵ{�N�X��J�W��allCourseCourseList���Ŷ�(�֦�school���Ҧ��ҵ{��T)
		List<School> allCourseCodeList = schoolDao.findAllByCourseCodeIn(courseCodeSet);

		// ��foreach�N�ҵ{�Ҧ���T�@�ӭӹM��
		for (School schoolItem : allCourseCodeList) {

			// �Ыؤ@��List�}�C�M��(�P���B�}�l�ɶ��B�����ɶ�)
			List<String> courseDayAndTime = new ArrayList<>();

			// �N�M���X�Ӫ��W�ҬP���[�i courseDayAndTime �M���
			courseDayAndTime.add(schoolItem.getCourseDay());

			// �]�w�ϥΪ̦b��J�ɶ��ɶ���u���榡�W�d (��:��:�� -> ��:��)
			DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");

			// �NDB�̨��o��Local time�ন�r��("HH:mm") -> ���O��i startTimeStr�BendTimeStr���Ŷ�
			String startTimeStr = df.format(schoolItem.getStartTime());
			String endTimeStr = df.format(schoolItem.getEndTime());

			// �N�Ұ�}�l�ɶ��[�icourseDayAndTime�M���
			courseDayAndTime.add(startTimeStr);

			// �N�Ұ󵲧��ɶ��[�icourseDayTime�M���
			courseDayAndTime.add(endTimeStr);

			// �N�ӧO���W�ҬP���νҰ�}�l�B�����ɶ���J���h��List�M��
			allDayAndTimeList.add(courseDayAndTime);
		}

		// ���P�� + �}�l�ɶ� + �����ɶ��G�Q�Ψ�Ӱj��@�ӭӤ��ϥΪ̿�J���W�ҬP���O�_�ۦP
		// i {{�P�� + �}�l�ɶ� + �����ɶ�}, j {�P�� + �}�l�ɶ� + �����ɶ�}}
		for (int i = 0; i < allDayAndTimeList.size(); i++) {
			String oneClassDay = allDayAndTimeList.get(i).get(0);

			// �Ĥ@���W�Ҹ�T���ĤG���W�Ҹ�T(�ư��ۤv������������ơA�@�ӭӱ�����)
			for (int j = i + 1; j < allDayAndTimeList.size(); j++) {
				String twoClassDay = allDayAndTimeList.get(j).get(0);

				// ��P���۲�(Ex. �P���T = �P���T)�A����P�_�ɶ�
				if (oneClassDay == twoClassDay) {

					// �Ĥ@��Ҫ��}�l�ɶ� index��� => {�P�� + �}�l�ɶ� + �����ɶ�}
					String oneClassStartTime = allDayAndTimeList.get(i).get(1);
					int oneClassStartTimeInt = Integer.parseInt(oneClassStartTime);

					// �Ĥ@��Ҫ������ɶ� index��� => {�P�� + �}�l�ɶ� + �����ɶ�}
					String oneClassEndTime = allDayAndTimeList.get(i).get(2);
					int oneClassEndTimeInt = Integer.parseInt(oneClassEndTime);

					// �ĤG��Ҫ��}�l�ɶ� index��� => {�P�� + �}�l�ɶ� + �����ɶ�}
					String twoClassStartTime = allDayAndTimeList.get(j).get(1);
					int twoClassStartTimeInt = Integer.parseInt(twoClassStartTime);

					// �ĤG��Ҫ������ɶ� index��� => {�P�� + �}�l�ɶ� + �����ɶ�}
					String twoClassEndTime = allDayAndTimeList.get(j).get(2);
					int twoClassEndTimeInt = Integer.parseInt(twoClassEndTime);

					// �N�}�l�B�����ɶ��a�J �����ɶ���k �T�{�O�_�İ�
					if (betweenExclude(oneClassStartTimeInt, oneClassEndTimeInt, twoClassStartTimeInt,
							twoClassEndTimeInt)) {

						// �İ� -> ���ܰT�� �L�k���
						return new SelectCourseRes(SelectCourseMessageCode.OUTFID.getMessage());
					}
				}
			}
		}

		// �S�İ� -> �^��null
		return null;
	}

	// �Υ��L�ӧP�_�ҵ{�O�_�İ�
	private boolean betweenExclude(int start1, int end1, int start2, int end2) {

		// ��ҵ{�}�l�ɶ����j�󵥩�Ұ󵲧��ɶ� �B �����ɶ����p�󵥩�}�l�ɶ��A�N�|�^��true = �İ� (�h��! true = ���İ�)
		return !(start1 >= end2) && !(end1 <= start2);
	}

	// �P�_�O�_���ۦP�ҵ{�B�Ǥ��ƬO�_���W�L10�Ǥ�
	private SelectCourseRes checkCourseNameConflictAndUnits(Set<String> courseCodeSet) {

		// �Ыؤ@�Ӧs���Ҧ��ҵ{�W�٪��M�� (���ҵ{�W�٥�)
		List<String> courseNameList = new ArrayList<>();

		// �Ыؤ@�Ӧs���Ҧ��ҵ{�W�٪�set�M�� (���ҵ{�W�٥�)
		Set<String> courseNameSet = new HashSet<>();

		// �N�`�Ǥ��w�]��0
		int allUnits = 0;

		// �N�ǥͽҵ{�N�X�M���JDB�� -> ��ŦX���ҵ{�N�X��J�W��allCourseCodeList���Ŷ�(�֦�school���Ҧ��ҵ{��T)
		List<School> allCourseCodeList = schoolDao.findAllByCourseCodeIn(courseCodeSet);

		// �N�Ҧ��ҵ{��T�@�ӭӹM��
		for (School schoolItem : allCourseCodeList) {

			// �N�ҵ{�W�٤��O��J�M��� (���W�٥�)
			courseNameList.add(schoolItem.getCourseName());
			courseNameSet.add(schoolItem.getCourseName());

			// �p��ҿ�ҵ{���`�Ǥ�
			allUnits = allUnits + schoolItem.getUnits();

		}

		// �Q�βM����קP�_�O�_�����ۦP�W�٪��ҵ{
		if (courseNameList.size() != courseNameSet.size()) {

			// �M����פ��P -> �^�Ǵ��ܰT�� �L�k�[��ۦP�W�٪��ҵ{
			return new SelectCourseRes(SelectCourseMessageCode.NOT_SAME_CLASS.getMessage());
		}

		// �`�Ǥ��W�L10
		if (allUnits > 10) {

			// �^�Ǵ��ܰT�� �W�L10�Ǥ����� �L�k���
			return new SelectCourseRes(SelectCourseMessageCode.CREDIT_FAIL.getMessage());
		}

		// �M����׬ۦP�B�Ǥ��ƲŦX -> �^��null
		return null;
	}

}
