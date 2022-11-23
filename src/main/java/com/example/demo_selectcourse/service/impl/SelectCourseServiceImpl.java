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

@Service // 讓Spring Boot託管 這樣才有辦法在其他地方被@Autowired
public class SelectCourseServiceImpl implements SelectCourseService { // 對內部進行實作

	@Autowired // 依賴 預設會依注入對象的類別型態來選擇容器中相符的物件來注入。 對DB資料進行新增、修改、刪除
	private SchoolDao schoolDao;

	@Autowired // 依賴 預設會依注入對象的類別型態來選擇容器中相符的物件來注入。
	private StudentDao studentDao;

	/* ================================================= */

	// SCHOOL
	// 新增課程 Create
	@Override // 覆寫、重新定義
	public SelectCourseRes createCourse(String courseCode, String courseName, String courseDay, String startTime,
			String endTime, int units) {

		// 課程代碼為空 || 課程名稱為空 || 課程星期為空 -> 提示訊息 課程資訊不得為空
		if (!StringUtils.hasText(courseCode) || !StringUtils.hasText(courseName) || !StringUtils.hasText(courseDay)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// 將輸入的課堂開始、結束時間帶入 classTime 方法 判斷是否真的有這筆資料 ->
		// 將判斷結果放入名為classTimeLocalTime、recessLocalTime的空間
		LocalTime startTimeLocalTime = checkTime(startTime);
		LocalTime endTimeLocalTime = checkTime(endTime); //

		// 上課星期格式是否符合 || 課程時間修改不得為NULL (補充：LocalTime = 0 的時候顯示方式為null) ||
				// 課堂開始時間不能超過課堂結束時間
				if (!checkCourseDayFormat(courseDay) || startTimeLocalTime == null || endTimeLocalTime == null
						|| startTimeLocalTime.isAfter(endTimeLocalTime)) {

					// 回傳提示訊息 上課星期、時間格式不符合規範
					return new SelectCourseRes(SelectCourseMessageCode.FORMAT_FAIL.getMessage());
				}
		
		// 學分數錯誤提醒：學分不得少於0(為空)及大於3 -> 學分數不符合規範
		if (units <= 0 || units > 3) {
			return new SelectCourseRes(SelectCourseMessageCode.CREDIT_FAIL.getMessage());
		}

		// 確認DB裡是否有課程代碼
		Optional<School> schoolOp = schoolDao.findById(courseCode);

		// 確認課程代碼已經存在或是被使用 -> 提示訊息 課程代碼不得重複
		if (schoolOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.CLASS_CODE_EXISTED.getMessage());
		}

		// 課程代碼不存在或是沒被使用 -> 提示訊息 課程創建成功
		School school = new School(courseCode, courseName, courseDay, startTimeLocalTime, endTimeLocalTime, units);
		schoolDao.save(school);
		return new SelectCourseRes(school, SelectCourseMessageCode.SUCCESSFUL.getMessage());//
	}

	// ==============================================================================
	// 修改課程 Update
	@Override // 覆寫、重新定義
	public SelectCourseRes updateCourse(String courseCode, String courseName, String courseDay, String startTime,
			String endTime, int units) {

		// 課程代碼為空 || 課程名稱為空 || 課程星期為空 -> 提示訊息 課程資訊不得為空
		if (!StringUtils.hasText(courseCode) || !StringUtils.hasText(courseName) || !StringUtils.hasText(courseDay)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// 將輸入的課堂開始、結束時間帶入 classTime 方法 判斷是否真的有這筆資料 ->
		// 將判斷結果放入名為classTimeLocalTime、recessLocalTime的空間
		LocalTime startTimeLocalTime = checkTime(startTime);
		LocalTime endTimeLocalTime = checkTime(endTime);

		// 上課星期格式是否符合 || 課程時間修改不得為NULL (補充：LocalTime = 0 的時候顯示方式為null) ||
		// 課堂開始時間不能超過課堂結束時間
		if (!checkCourseDayFormat(courseDay) || startTimeLocalTime == null || endTimeLocalTime == null
				|| startTimeLocalTime.isAfter(endTimeLocalTime)) {

			// 回傳提示訊息 上課星期、時間格式不符合規範
			return new SelectCourseRes(SelectCourseMessageCode.FORMAT_FAIL.getMessage());
		}

		// 學分不得少於0及大於3 -> 提示訊息 學分數修改錯誤
		if (units <= 0 || units > 3) {
			return new SelectCourseRes(SelectCourseMessageCode.CREDIT_FAIL.getMessage());
		}

		// 確認DB是否有課程代碼
		Optional<School> schoolOp = schoolDao.findById(courseCode);

		// 課程不存在 -> 提示訊息 查無此課程
		if (!schoolOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		} else {
			School school = new School(courseName, courseDay, startTimeLocalTime, endTimeLocalTime, units);
			schoolDao.save(school);

			// 回傳提示訊息 課程修改成功
			return new SelectCourseRes(school, SelectCourseMessageCode.REVISE_SUCCESSFUL.getMessage());
		}
	}

	// ==============================================================================
	// 刪除課程 Delete
	@Override // 覆寫、重新定義
	public SelectCourseRes deleteCourse(String courseCode) {

		// 課程代碼為空 -> 提示訊息 課程代碼不得為空
		if (!StringUtils.hasText(courseCode)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// 確認DB是否有課程代碼
		Optional<School> schoolOp = schoolDao.findById(courseCode);

		// 課程代碼不存在 -> 訊息提示 查無此課程
		if (!schoolOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// 刪除此課程資訊 -> 提示訊息 課程已刪除
		schoolDao.deleteById(courseCode);
		return new SelectCourseRes(SelectCourseMessageCode.DELETE_SUCCESSFUL.getMessage());
	}

	// ==============================================================================
	// 課程查詢 class query

	// 透過代碼查詢
	@Override // 覆寫、重新定義
	public SelectCourseRes courseQuery(String courseCode) {

		// 課程代碼為空 -> 提示訊息 課程代碼不得為空
		if (!StringUtils.hasText(courseCode)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// 確認DB是否有課程代碼
		Optional<School> schoolOp = schoolDao.findById(courseCode);

		// 課程代碼不存在 -> 提示訊息 查無此課程代碼
		if (!schoolOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		} else {

			// 透過查詢課程代碼取得課程資訊
			School school = schoolOp.get();

			// 回傳查詢的課程資訊及提示訊息 查詢成功
			return new SelectCourseRes(school, SelectCourseMessageCode.QUERY_SUCCESSFUL.getMessage());
		}
	}

	// 透過課程名稱查詢
	@Override // 覆寫、重新定義
	public SelectCourseRes courseNameQuery(String courseName) {

		// 課程名稱為空 -> 提示訊息 課程名稱不得為空
		if (!StringUtils.hasText(courseName)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// 確認DB是否有課程名稱
		List<School> schoolList = schoolDao.findAllByCourseName(courseName);

		// 查詢的課程為空 -> 提示訊息 查無課程資訊
		if (schoolList.isEmpty()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// 回傳查詢的課程資訊及提示訊息 查詢成功
		return new SelectCourseRes(schoolList, SelectCourseMessageCode.QUERY_SUCCESSFUL.getMessage());
	}
	/* ====================================== */

	// STUDENT
	// 新增學生資訊
	@Override // 覆寫、重新定義
	public SelectCourseRes addStudentInfo(String studentId, String studentName) {

		// 學號、姓名為空 -> 提示訊息 學號、姓名不得為空
		if (!StringUtils.hasText(studentId) || !StringUtils.hasText(studentName)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// 確認DB是否有學號
		Optional<Student> studentOp = studentDao.findById(studentId);

		// 學號存在 -> 提示訊息 此學號已被使用
		if (studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.STUDENT_ID_EXISTED.getMessage());
		}

		// 將使用者輸入的學號及學生姓名放入名為 student 的空間
		Student student = new Student(studentId, studentName);

		// 將值存入DB
		studentDao.save(student);

		// 回傳提示訊息 學生資訊新增成功
		return new SelectCourseRes(student, SelectCourseMessageCode.SUCCESSFUL.getMessage());
	}

	// ======================================================================
	// 修改學生資訊
	@Override // 覆寫、重新定義
	public SelectCourseRes updateStudentInfo(String studentId, String studentName) {

		// 學號、學生姓名為空 -> 提示訊息 學號、學生姓名不得為空
		if (!StringUtils.hasText(studentId) || !StringUtils.hasText(studentName)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// 確認DB是否有學號
		Optional<Student> studentOp = studentDao.findById(studentId);

		// 學號不存在 -> 提示訊息 查無此人
		if (!studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());

			// 否則 (學號存在)
		} else {

			// 透過學號取得該學生的所有資訊
			Student student = studentOp.get();

			// 設置使用者輸入的學生姓名
			student.setStudentName(studentName);

			// 存入DB
			studentDao.save(student);

			// 回傳提示訊息 學生資訊修改成功
			return new SelectCourseRes(student, SelectCourseMessageCode.REVISE_SUCCESSFUL.getMessage());
		}
	}

	// ======================================================================
	// 刪除學生資訊
	@Override // 覆寫、重新定義
	public SelectCourseRes deleteStudentInfo(String studentId, String studentName) {

		// 學號、姓名為空 -> 提示訊息 學號、姓名不得為空
		if (!StringUtils.hasText(studentId) || !StringUtils.hasText(studentName)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// 確認DB是否有學號
		Optional<Student> studentOp = studentDao.findById(studentId);

		// 學號不存在 -> 提示訊息 查無此人
		if (!studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// 學號存在 -> 刪除DB裡的單一學生資訊
		studentDao.deleteById(studentId);

		// 回傳提示訊息 學生資訊已刪除
		return new SelectCourseRes(SelectCourseMessageCode.DELETE_SUCCESSFUL.getMessage());
	}

	/* ======================================================== */
	// 選課 course selection (不得超過10學分、不能選相同名稱的課程、不能衝堂)
	@Override // 覆寫、重新定義
	public SelectCourseRes courseSelection(String studentId, Set<String> courseCodeSet) {

		// 帶入內部方法：將輸入的課程代碼帶入checkCourseCode 方法 去判斷是否真的有這兩筆資料 ->
		// 將判斷結果放入名為studentIdAndcourseCodeRes的空間
		SelectCourseRes studentIdAndCourseCodeRes = checkCourseCode(studentId, courseCodeSet);

		// 當判斷結果不等於null，代表有課程代碼已存在 -> 返回提示訊息 查無課程代碼
		if (studentIdAndCourseCodeRes != null) {
			return studentIdAndCourseCodeRes;
		}

		// 確認DB是否有學號
		Optional<Student> studentOp = studentDao.findById(studentId);

		// 學號輸入錯誤 -> 提示訊息 學號不存在
		if (!studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.FORMAT_FAIL.getMessage());
		}

		// 透過學號(PK)取得此學生的所有個人資訊
		Student student = studentOp.get();

		// 如果學生當前已選過課 -> 提示訊息 你已選過課，請去加退選
		if (StringUtils.hasText(student.getCourseCode())) {
			return new SelectCourseRes(SelectCourseMessageCode.HAVE_CLASS_GO_ADDOROUT_CLASS.getMessage());
		}

		// ==============================================================
		// 不能選相同名稱的課程 and 選課不得超過10學分

		// 總註解：創建一個List、Set清單分別存放學生輸入的課程名稱
		// 創建一個存有所有課程名稱的清單 (比對課程名稱用)
		List<String> courseNameList = new ArrayList<>();

		// 創建一個存放不得重複的課程名稱清單 (比對課程名稱用)
		Set<String> courseNameSet = new HashSet<>();

		// 將總學分預設為0
		int allUnits = 0;

		// 將已符合的選課代碼放入資料庫，取出多筆課程資訊
		List<School> allCourseCodeList = schoolDao.findAllByCourseCodeIn(courseCodeSet);

		// 由於上面清單取得的課程資料是包含'所有'資訊，因此使用foreach將它一個個遍歷出來 分別儲存
		for (School schoolItem : allCourseCodeList) {

			// 取出課程名稱 並放入list清單
			courseNameList.add(schoolItem.getCourseName());

			// 取出課程名稱 放入set清單
			courseNameSet.add(schoolItem.getCourseName());

			// 取出課程學分 -> 學分加總
			allUnits = allUnits + schoolItem.getUnits();
		}

		// 利用清單長度判斷是否有選到相同名稱的課程 (當Set清單與List清單的長度不相等 代表有選到相同名稱的課程)
		if (courseNameList.size() != courseNameSet.size()) {

			// 回傳提示訊息 無法加選相同名稱的課程
			return new SelectCourseRes(SelectCourseMessageCode.NOT_SAME_CLASS.getMessage());
		}

		// 當選課學分總計超過10 -> 提示訊息 超過10學分限制
		if (allUnits > 10) {
			return new SelectCourseRes(SelectCourseMessageCode.CREDIT_FAIL.getMessage());
		}

		// ==============================================================
		// 不得衝堂

		// 將輸入的課程代碼帶入課程衝突的方法去判斷 -> 將判斷結果放入名為res的空間
		SelectCourseRes courseCodeRes = checkCourseConflict(courseCodeSet);

		// 當判斷結果不等於null，代表有衝堂 -> 回傳提示訊息
		if (courseCodeRes != null) {
			return courseCodeRes;
		}

		// 將使用者輸入的課程代碼之資料型態 Set<String> 轉成字串(為了符合DB格式)
		String courseCodeStr = courseCodeSet.toString();

		// 集合轉換成字串會有括號，因此去頭尾括號 方法：.substring(以index來算/有包含,陣列是用size、字串是用.length() -1
		// 不包含那個位子的內容)
		// Ex.(1,5) = 包含1，不包含5 --> 取到的會是 1 - 4
		String removeBracketsCourseCode = courseCodeStr.substring(1, courseCodeStr.length() - 1);

		// 將課程代碼存入該學生student
		student.setCourseCode(removeBracketsCourseCode);

		// 再存進DB裡
		studentDao.save(student);

		// 回傳提示訊息 選課成功
		return new SelectCourseRes(student, SelectCourseMessageCode.SELECT_COURSE_SUCCESSFUL.getMessage());
	}

	/* ======================================================== */
	// 加選
	@Override // 覆寫、重新定義
	public SelectCourseRes addCourse(String studentId, Set<String> courseCodeSet) {

		// 判斷學生加選的課程是否與資料庫的資訊相符
		SelectCourseRes studentIdAndCourseCodeRes = checkCourseCode(studentId, courseCodeSet);

		// 當result不等於null -> 回傳提示訊息 學號、課程代碼不得空及查無此課程
		if (studentIdAndCourseCodeRes != null) {
			return studentIdAndCourseCodeRes;
		}

		// 確認DB是否有學號
		Optional<Student> studentOp = studentDao.findById(studentId);

		// 學號不存在 -> 提示訊息 查無此人
		if (!studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// 透過DB的課程代碼取得單一學生的所有個人資訊 (加選的課程資料)
		Student student = studentOp.get();

		// 學生的課程代碼為空 -> 提示訊息 你沒選過課，請去選課
		if (!StringUtils.hasText(student.getCourseCode())) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_CLASS_GO_ADD_CLASS.getMessage());
		}

		// 從DB取得學生'原始'的選課資訊(第一次選的課)
		String studentOldStr = studentOp.get().getCourseCode();

		// 將使用者輸入的'加選'課程代碼之資料型態 Set<String> 轉成字串
		String studentAddCoursCodeStr = courseCodeSet.toString();

		// 去除前後括號
		String bracketsRemoveStudentAddCoursCode = studentAddCoursCodeStr.substring(1,
				studentAddCoursCodeStr.length() - 1);

		// 創建一個存放新舊課程的set清單
		Set<String> newCourseCodeSet = new HashSet<>();

		// 將原始的選課資訊用逗號隔開去空白 -> 放進清單裡
		String[] removeCommaOld = studentOldStr.split(",");
		for (String removeNullOld : removeCommaOld) {
			newCourseCodeSet.add(removeNullOld.trim());
		}

		// 將加選的選課資訊用逗號隔開去空白 -> 放進SetList清單裡
		String[] removeCommaNew = bracketsRemoveStudentAddCoursCode.split(",");
		for (String removeNullNew : removeCommaNew) {
			newCourseCodeSet.add(removeNullNew.trim());
		}

		// =================================================================================
		// 選課不得超過10學分 and 不能選相同名稱的課程

		// 判斷新舊課程是否有相同課程、學分數是否超過10學分 -> 將判斷結果放入名為courseNameConflictAndUnitsRes的空間
		SelectCourseRes courseNameConflictAndUnitsRes = checkCourseNameConflictAndUnits(newCourseCodeSet);

		// 當判斷結果不等於null，代表格式不符 -> 回傳提示訊息 無法加選相同名稱的課程、總學分超過10
		if (courseNameConflictAndUnitsRes != null) {
			return courseNameConflictAndUnitsRes;
		}
		// ==============================================================
		// 不得衝堂

		// 將輸入的課程代碼帶入課程衝突的方法去判斷 -> 將判斷結果放入名為courseConflictRes(課程衝突)的空間
		SelectCourseRes courseConflictRes = checkCourseConflict(courseCodeSet);

		// 當判斷結果不等於null，代表有衝堂 -> 回傳提示訊息 無法選課
		if (courseConflictRes != null) {
			return courseConflictRes;
		}

		// 將放有原有課程、加選課程資訊的set清單轉換成字串型態
		String courseCodeStr = newCourseCodeSet.toString();

		// 並去除因string而產生的前後括號
		String removeBracketsStr = courseCodeStr.substring(1, courseCodeStr.length() - 1);

		// 將新的課程代碼覆蓋原有課程
		student.setCourseCode(removeBracketsStr);

		// 存回DB裡
		studentDao.save(student);

		// 儲存成功 -> 回傳所有加選課程及提示訊息 課程加選成功
		return new SelectCourseRes(student, SelectCourseMessageCode.CLASS_ADD_SUCCESSFUL.getMessage());
	}

	/* ======================================================== */
//	退選
	@Override // 覆寫、重新定義
	public SelectCourseRes WithdrawCourse(String studentId, List<String> courseCodeList) {

		// 學號為空、課程代碼為空 -> 提示訊息 學號、課程代碼不得為空
		if (!StringUtils.hasText(studentId) || CollectionUtils.isEmpty(courseCodeList)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// 確認DB是否有學號
		Optional<Student> studentOp = studentDao.findById(studentId);

		// 此學生不存在 -> 提示訊息 查無此人
		if (!studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// 透過DB學號(PK)取得單一學生所有資訊
		Student studentAllInfo = studentOp.get();

		// 透過單一學生資訊取得選課代碼並存放入studentCourseCode
		String studentCourseCode = studentAllInfo.getCourseCode();

		// 創建一個存放學生課程代碼的清單 (對比用)
		List<String> studentCourseCodeList = new ArrayList<>();

		// 將取得學生課程代碼用逗號隔開去空白 -> 放進清單裡
		String[] removeCommaCourseCode = studentCourseCode.split(",");
		for (String removeNullCourseCode : removeCommaCourseCode) {
			studentCourseCodeList.add(removeNullCourseCode.trim());
		}

		// 將學生課程代碼清單放入DB裡 -> 把符合的課程代碼放入名為allCourseCodeList的空間(擁有school的所有課程資訊)
		List<School> allCourseCodeList = schoolDao.findAllByCourseCodeIn(courseCodeList);

		// 創建一個存放退選課程代碼的清單 (對比用)
		List<String> withdrawCourseCodeList = new ArrayList<>();

		// 將學生退選的課程代碼用逗號隔開去空白 -> 放進清單裡
		for (School schoolItem : allCourseCodeList) {
			if (studentCourseCodeList.contains(schoolItem.getCourseCode())) {
				withdrawCourseCodeList.add(schoolItem.getCourseCode());
			}
		}

		// 當輸入的退選課程代碼為空、DB裡無此課程代碼 -> 提示訊息 查無此課程
		if (withdrawCourseCodeList.isEmpty()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// 當原有的課程代碼與退選課程代碼相符 -> 從studentCourseCodeList裡刪除退選的課程
		studentCourseCodeList.removeAll(withdrawCourseCodeList);

		// 將studentCodeList清單所有課程資訊轉字串、去前後括號 -> 將課程代碼返回給該學生
		studentAllInfo.setCourseCode(
				studentCourseCodeList.toString().substring(1, (studentCourseCodeList.toString().length() - 1)));

		// 存回DB
		studentDao.save(studentAllInfo);

		// 儲存成功 -> 回傳學生課程資訊及提示訊息 退選成功
		return new SelectCourseRes(studentAllInfo, SelectCourseMessageCode.WITHDRAW_CLASS_SUCCESSFUL.getMessage());
	}

	/* ======================================================== */
	// 學生所選課程總覽 class Overview (透過學號查詢)
	@Override // 覆寫、重新定義
	public SelectCourseRes courseOverview(String studentId) {

		// 學號為空 -> 提示訊息 學號不得為空
		if (!StringUtils.hasText(studentId)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}

		// 確認DB是否有學號
		Optional<Student> studentOp = studentDao.findById(studentId);

		// 學號不存在 -> 提示訊息 查無此學生
		if (!studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// 透過DB學號(PK)取得單一學生所有資訊
		Student studentAllInfo = studentOp.get();

		// 透過單一學生資訊取得選課代碼並存放入studentCourseCode
		String studentCourseCode = studentAllInfo.getCourseCode();

		// 創建一個存放學生課程代碼的清單
		List<String> courCodeList = new ArrayList<>();

		// 將學生課程代碼用逗號隔開去空白 -> 放進清單裡
		String[] removeCommaCourseCode = studentCourseCode.split(",");
		for (String removeNullCourseCode : removeCommaCourseCode) {
			courCodeList.add(removeNullCourseCode.trim());
		}

		// 將學生課程代碼清單放入DB裡 -> 把符合的課程放入名為studentAllCourseCode的空間(擁有school的所有課程資訊)
		List<School> studentAllCourseCode = schoolDao.findAllById(courCodeList);

		// 回傳學生擁有的所有課程代碼及課程資訊 提示訊息 查詢成功
		return new SelectCourseRes(studentAllCourseCode, studentAllInfo,
				SelectCourseMessageCode.QUERY_SUCCESSFUL.getMessage());
	}

	// =============================內部方法======================================
	// [上課星期 防呆]
	private boolean checkCourseDayFormat(String courseDay) {

		// 把想要的星期格式設定到陣列裡
		List<String> classWeekDaysList = Arrays.asList("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");

		// 確認使用者輸入的參數是否符合List陣列的所有內容 (此方法只要有一個參數不符合皆為false)
		return classWeekDaysList.contains(courseDay);
	}

	// [時間 防呆]
	// (String allTime)為了對應Postman的req寫法格式 (自訂義名稱，與上方設置的參數無關)
	private LocalTime checkTime(String allTime) {
		try {
			// 把字串轉換成LocalTime -> 設定我們要的格式("HH:mm")，並放入名為formatter的空間
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

			// 把輸入的時間格式(String),將classTime轉成我們要的formatter的"HH:mm"格式
			LocalTime classTime = LocalTime.parse(allTime, formatter);

			// 符合就是回傳，不符合就是null(唯一)
			return classTime;
		} catch (Exception e) {
			return null;
		}
	}

	// 判斷選取的課程是否符合DB資訊 (判斷課程是否存在)
	private SelectCourseRes checkCourseCode(String studentId, Set<String> courseCodeSet) {

		// 學號為空 || 選取的課程代碼為空 -> 提示訊息 學號、課程代碼不得空 (補充：單獨的.isEmpty() 不能接受空，但可以接受null)
		if (!StringUtils.hasText(studentId) || CollectionUtils.isEmpty(courseCodeSet)) {
			return new SelectCourseRes(SelectCourseMessageCode.NOT_NULL.getMessage());
		}
		
		// 將DB所有課程資訊放入confirmClassList清單 (擁有school的所有課程資訊)
		List<School> confirmClassList = schoolDao.findAll();

		// 創建一個存放課程代碼的陣列清單 (為了比對資料庫及使用者輸入的選課代碼，因此轉換成相同List去做比對)
		List<String> tempCourseCodeList = new ArrayList<>();

		// 將所有課程資訊一個個遍歷並把課程代碼存放到keepCourseCodeList清單
		for (School schoolItem : confirmClassList) {
			tempCourseCodeList.add(schoolItem.getCourseCode());
		}

		// 確認輸入的課程代碼是否符合DB裡有的課程代碼
		boolean courseCodeBoolean = tempCourseCodeList.containsAll(courseCodeSet);

		// 當課程代碼不存在 -> 提示訊息 查無課程代碼
		if (courseCodeBoolean == false) {
			return new SelectCourseRes(SelectCourseMessageCode.NO_DATA.getMessage());
		}

		// 當課程代碼存在 -> 回傳null
		return null;
	}

	// checkCourseConflict 課程衝突
	private SelectCourseRes checkCourseConflict(Set<String> courseCodeSet) {

		// 創建一個雙層List清單 (用於之後比對課程是否衝堂：單堂課程星期、時間資訊及多堂課程)
		// List = allDayAndTimeList -> {星期,開始時間,結束時間}, List<String> = courseDayAndTime
		// -> {星期} {開始時間} {結束時間}
		List<List<String>> allDayAndTimeList = new ArrayList<>();

		// 將學生課程代碼清單放入DB裡 -> 把符合的課程代碼放入名為allCourseCourseList的空間(擁有school的所有課程資訊)
		List<School> allCourseCodeList = schoolDao.findAllByCourseCodeIn(courseCodeSet);

		// 用foreach將課程所有資訊一個個遍歷
		for (School schoolItem : allCourseCodeList) {

			// 創建一個List陣列清單(星期、開始時間、結束時間)
			List<String> courseDayAndTime = new ArrayList<>();

			// 將遍歷出來的上課星期加進 courseDayAndTime 清單裡
			courseDayAndTime.add(schoolItem.getCourseDay());

			// 設定使用者在輸入時間時須遵守的格式規範 (時:分:秒 -> 時:分)
			DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");

			// 將DB裡取得的Local time轉成字串("HH:mm") -> 分別放進 startTimeStr、endTimeStr的空間
			String startTimeStr = df.format(schoolItem.getStartTime());
			String endTimeStr = df.format(schoolItem.getEndTime());

			// 將課堂開始時間加進courseDayAndTime清單裡
			courseDayAndTime.add(startTimeStr);

			// 將課堂結束時間加進courseDayTime清單裡
			courseDayAndTime.add(endTimeStr);

			// 將個別的上課星期及課堂開始、結束時間放入雙層的List清單
			allDayAndTimeList.add(courseDayAndTime);
		}

		// 比對星期 + 開始時間 + 結束時間：利用兩個迴圈一個個比對使用者輸入的上課星期是否相同
		// i {{星期 + 開始時間 + 結束時間}, j {星期 + 開始時間 + 結束時間}}
		for (int i = 0; i < allDayAndTimeList.size(); i++) {
			String oneClassDay = allDayAndTimeList.get(i).get(0);

			// 第一筆上課資訊比對第二筆上課資訊(排除自己本身的那筆資料，一個個接續比對)
			for (int j = i + 1; j < allDayAndTimeList.size(); j++) {
				String twoClassDay = allDayAndTimeList.get(j).get(0);

				// 當星期相符(Ex. 星期三 = 星期三)再接續判斷時間
				if (oneClassDay == twoClassDay) {

					// 第一堂課的開始時間 index比對 => {星期 + 開始時間 + 結束時間}
					String oneClassStartTime = allDayAndTimeList.get(i).get(1);
					int oneClassStartTimeInt = Integer.parseInt(oneClassStartTime);

					// 第一堂課的結束時間 index比對 => {星期 + 開始時間 + 結束時間}
					String oneClassEndTime = allDayAndTimeList.get(i).get(2);
					int oneClassEndTimeInt = Integer.parseInt(oneClassEndTime);

					// 第二堂課的開始時間 index比對 => {星期 + 開始時間 + 結束時間}
					String twoClassStartTime = allDayAndTimeList.get(j).get(1);
					int twoClassStartTimeInt = Integer.parseInt(twoClassStartTime);

					// 第二堂課的結束時間 index比對 => {星期 + 開始時間 + 結束時間}
					String twoClassEndTime = allDayAndTimeList.get(j).get(2);
					int twoClassEndTimeInt = Integer.parseInt(twoClassEndTime);

					// 將開始、結束時間帶入 內部時間方法 確認是否衝堂
					if (betweenExclude(oneClassStartTimeInt, oneClassEndTimeInt, twoClassStartTimeInt,
							twoClassEndTimeInt)) {

						// 衝堂 -> 提示訊息 無法選課
						return new SelectCourseRes(SelectCourseMessageCode.OUTFID.getMessage());
					}
				}
			}
		}

		// 沒衝堂 -> 回傳null
		return null;
	}

	// 用布林來判斷課程是否衝堂
	private boolean betweenExclude(int start1, int end1, int start2, int end2) {

		// 當課程開始時間不大於等於課堂結束時間 且 結束時間不小於等於開始時間，就會回傳true = 衝堂 (去掉! true = 不衝堂)
		return !(start1 >= end2) && !(end1 <= start2);
	}

	// 判斷是否有相同課程、學分數是否不超過10學分
	private SelectCourseRes checkCourseNameConflictAndUnits(Set<String> courseCodeSet) {

		// 創建一個存有所有課程名稱的清單 (比對課程名稱用)
		List<String> courseNameList = new ArrayList<>();

		// 創建一個存有所有課程名稱的set清單 (比對課程名稱用)
		Set<String> courseNameSet = new HashSet<>();

		// 將總學分預設為0
		int allUnits = 0;

		// 將學生課程代碼清單放入DB裡 -> 把符合的課程代碼放入名為allCourseCodeList的空間(擁有school的所有課程資訊)
		List<School> allCourseCodeList = schoolDao.findAllByCourseCodeIn(courseCodeSet);

		// 將所有課程資訊一個個遍歷
		for (School schoolItem : allCourseCodeList) {

			// 將課程名稱分別放入清單裡 (對比名稱用)
			courseNameList.add(schoolItem.getCourseName());
			courseNameSet.add(schoolItem.getCourseName());

			// 計算所選課程的總學分
			allUnits = allUnits + schoolItem.getUnits();

		}

		// 利用清單長度判斷是否有選到相同名稱的課程
		if (courseNameList.size() != courseNameSet.size()) {

			// 清單長度不同 -> 回傳提示訊息 無法加選相同名稱的課程
			return new SelectCourseRes(SelectCourseMessageCode.NOT_SAME_CLASS.getMessage());
		}

		// 總學分超過10
		if (allUnits > 10) {

			// 回傳提示訊息 超過10學分限制 無法選課
			return new SelectCourseRes(SelectCourseMessageCode.CREDIT_FAIL.getMessage());
		}

		// 清單長度相同、學分數符合 -> 回傳null
		return null;
	}

}
