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

@Service     // 讓Spring Boot託管 這樣才有辦法在其他地方被@Autowired
public class SelectCourseServiceImpl implements SelectCourseService {         //對內部進行實作

	@Autowired           //依賴    預設會依注入對象的類別型態來選擇容器中相符的物件來注入。
	private SchoolDao schoolDao;

	@Autowired          //依賴    預設會依注入對象的類別型態來選擇容器中相符的物件來注入。
	private StudentDao studentDao;

	/* ================================================= */

	// SCHOOL
	// 新增課程 Create
	@Override      //覆寫、重新定義
	public SelectCourseRes createCours(String courseCode, String course, String classDay, String classTime,
			String recess, int units) {
		
		boolean a = classDay(classDay);    //利用布林值去判斷上課星期格式是否符合設定的格式

		// 課程代碼不得為空 || 課程名稱不得為空 || 課程星期不得為空
		if (!StringUtils.hasText(courseCode) || !StringUtils.hasText(course) || !StringUtils.hasText(classDay)) {
//			SelectCourseRes selectCourseRes2 = new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage());
//			return selectCourseRes2;
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage());   //提示訊息 課程代碼不得為空
		}

		 //課程星期格式錯誤
		if (a != true) {                        //當判斷結果不為true
			return new SelectCourseRes(SelectCourseRtnCode.FORMAT_ERROR.getRtnmessage()); //提示訊息 課程星期格式錯誤
		}
		
		LocalTime classtimeLocalTime = classTime(classTime);    // 上課時間錯誤提醒：確認是否有上課時間這東西
		LocalTime recessLocalTime = classTime(recess);            // 下課時間錯誤提醒：確認是否有下課時間這東西
		if (!StringUtils.hasLength(classTime) || !StringUtils.hasLength(recess)) {    //有上、下課時間。如果上、下課時間為空
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //提示訊息 課程時間不得為空
		} else if (classtimeLocalTime == null || recessLocalTime == null) {               //如果上、下課時間為0
			return new SelectCourseRes(SelectCourseRtnCode.FORMAT_ERROR.getRtnmessage()); //提示訊息 課程時間格式錯誤
		}

		// 學分數錯誤提醒
		if (units == 0) {    //如果學分數為0
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //提示訊息 學分數不得為空
		}
		if (units > 3) {     //如果選修的課程超過3學分的範圍限制
			return new SelectCourseRes(SelectCourseRtnCode.CLASS_CODE_EXISTED.getRtnmessage()); //提示訊息 超過學分數限制
		}

		Optional<School> coursCode = schoolDao.findById(courseCode);      //透過DB所設的PK去取得學校的所有課程資訊

		if (coursCode.isPresent()) {              //如果我們在Postman設定的課程代碼已經存在或是被使用
			return new SelectCourseRes(SelectCourseRtnCode.CLASS_CODE_EXISTED.getRtnmessage()); //提示訊息 課程代碼不得重複
		}

		School school = new School(courseCode, course, classDay, classtimeLocalTime, recessLocalTime, units);
		schoolDao.save(school);
		return new SelectCourseRes(school, SelectCourseRtnCode.SUCCESSFUL.getRtnmessage());//課程創建成功
	}

	// [上課星期 防呆]
	public boolean classDay(String classDay) {
		List<String> classWeekDay = Arrays.asList("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
		return classWeekDay.contains(classDay); // 確認使用者輸入的參數是否符合List陣列的所有內容
	} // 此方法只要有一個參數不符合皆為false

	// [時間 防呆]
	public LocalTime classTime(String allTime) { // (String allTime)為了對應Postman的req寫法格式!!式自訂義名稱，與上方設置的參數無關
		try {
			// 把字串轉換成LocalTime
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // 設定我們要的格式("HH:mm")，並放入名為formatter的空間
			LocalTime classTime = LocalTime.parse(allTime, formatter); // 把輸入的時間格式(String),將
																		// (classTime)轉成我們要的formatter的"HH:mm"格式
			return classTime; // 符合就是回傳，不符合就是null(唯一)
		} catch (Exception e) {
			return null;
		}
	}

	// ==============================================================================
	// 修改課程 Update
	@Override     //覆寫、重新定義
	public SelectCourseRes updateCours(String coursCode, String cours, String classDay, String classTime, String recess,
			int units) {
		
		// 課堂名稱修改不得為空
		if (!StringUtils.hasText(cours)) { // 如果課程名稱為空
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //課程名稱不得為空
		}
		
		
		// 上課時間不能為空，上課時間也不能晚於下課時間
		// 上課星期格式是否符合 || 課程時間修改錯誤
		LocalTime classtimeLocalTime = classTime(classTime); // LocalTime = 0的時候顯示方式為null
		LocalTime recessLocalTime = classTime(recess); // 字串轉LocalTime
		if (!classDay(classDay) || classtimeLocalTime == null || recessLocalTime == null || classtimeLocalTime.isAfter(recessLocalTime)) {
			return new SelectCourseRes(SelectCourseRtnCode.FORMAT_ERROR.getRtnmessage()); //上課星期格式不符合規範
		}

		
		// 學分不得少於0及大於3
		if (units <= 0 || units > 3) {
			return new SelectCourseRes(SelectCourseRtnCode.CREDIT_ERROR.getRtnmessage()); //學分數修改錯誤
		}

		Optional<School> Update = schoolDao.findById(coursCode);
		if (!Update.isPresent()) { // 如果課程不存在
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //查無此課程
		}else {
			School updateClass = Update.get();
			updateClass.setCourse(cours);
			updateClass.setClassDay(classDay);
			updateClass.setClassTime(classtimeLocalTime);
			updateClass.setRecess(recessLocalTime);
			updateClass.setUnits(units);
			schoolDao.save(updateClass);
			//課程修改成功
			return new SelectCourseRes(updateClass,SelectCourseRtnCode.REVISE_SUCCESSFUL.getRtnmessage()); // 為了讓JSON印出
		} 
	}

	// ==============================================================================
	// 刪除課程 Delete
	@Override    //覆寫、重新定義
	public SelectCourseRes deleteCours(String coursCode) {

		SelectCourseRes selectCourseRes = new SelectCourseRes();

		if (!StringUtils.hasText(coursCode)) { // 如果課程代碼為空
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //課程代碼不得為空
		}

		Optional<School> deleteCourse = schoolDao.findById(coursCode); // 看資料庫是否有課程代碼

		if (!deleteCourse.isPresent()) { // 確認我要的課程代碼是否存在
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //查無此課程
		}

		schoolDao.deleteById(coursCode); // 刪除此資訊
		selectCourseRes.setMessage("課程已刪除");
		return new SelectCourseRes(SelectCourseRtnCode.DELETE_SUCCESSFUL.getRtnmessage());
	}

	// ==============================================================================
	// 課程查詢 class query

	// 一、透過代碼查詢
	@Override    //覆寫、重新定義
	public SelectCourseRes classQuery(String coursCode) {
		
		if (!StringUtils.hasText(coursCode)) { // 如果課程代碼為空
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //課程代碼不得為空
		}

		Optional<School> coursCodeQuery = schoolDao.findById(coursCode);
		if (!coursCodeQuery.isPresent()) {
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //查無課程代碼
		}else {
			School school = coursCodeQuery.get();
			return new SelectCourseRes(school,SelectCourseRtnCode.QUERY_SUCCESSFUL.getRtnmessage()); 	//查詢成功
		}
	}

	// 透過課程名稱查詢
	@Override    //覆寫、重新定義
	public SelectCourseRes classnameQuery(String course) { // 查詢的課程代碼是否符合DB資料庫的代碼 符合就往下跑

		if (!StringUtils.hasText(course)) { // 如果課程名稱為空
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //課程名稱不得為空
		}

		List<School> coursNameQuery = schoolDao.findAllByCourse(course);   //確認DB裡是否有課程這個東西
		if (coursNameQuery.isEmpty()) {                                             //有， 比對要查詢的課程是否為空
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage());      //查無課程資訊
		}
		return new SelectCourseRes(coursNameQuery,SelectCourseRtnCode.QUERY_SUCCESSFUL.getRtnmessage()); //查詢成功 以上為課程資訊
	}
	/*======================================*/

	// STUDENT
	// 新增學生選課資訊
	@Override       //覆寫、重新定義
	public SelectCourseRes addStudentSelectCourse(String studentId, String studentName) {

		// 學號、姓名不得為空
		if (!StringUtils.hasText(studentId) || !StringUtils.hasText(studentName)) {
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //學號不得為空
		}

		Optional<Student> Id = studentDao.findById(studentId);  //判斷DB是否有學號
		if (Id.isPresent()) {              //有， 如果學號存在
			return new SelectCourseRes(SelectCourseRtnCode.STUDENT_ID_EXISTED.getRtnmessage()); //此學號已被使用
		}

		Student student = new Student(studentId, studentName);
		studentDao.save(student);
		return new SelectCourseRes(student,SelectCourseRtnCode.SUCCESSFUL.getRtnmessage());  //學生資訊輸入成功
	}

	// ======================================================================
	// 修改學生資訊
	@Override    //覆寫、重新定義
	public SelectCourseRes updateStudentSelectCourse(String studentId, String studentName) {

		//學號、學生資訊不得為空
		if (!StringUtils.hasText(studentId) || !StringUtils.hasText(studentName) ) { // 學生資訊不得為空
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //學號不得為空
		}

		Optional<Student> update = studentDao.findById(studentId);
		if (!update.isPresent()) { // 確認此學生資訊是否存在
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //查無此人
		}else { // 當學生資訊存在時
			Student student = new Student();
			student = update.get();
//			Student student = update.get();
			student.setStudentName(studentName);
			studentDao.save(student);
			return new SelectCourseRes(student,SelectCourseRtnCode.REVISE_SUCCESSFUL.getRtnmessage()); //學生資訊修改成功
		}
	}

	// ======================================================================
	// 刪除學生資訊
	@Override   //覆寫、重新定義
	public SelectCourseRes deleteStudentSelectCourse(String studentId, String studentName) {

		if (!StringUtils.hasText(studentId)) {
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //學號不得為空
		}

		Optional<Student> deleteStudent = studentDao.findById(studentId);
		if (!deleteStudent.isPresent()) {
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //查無此人
		}
		studentDao.deleteById(studentId);
		return new SelectCourseRes(SelectCourseRtnCode.DELETE_SUCCESSFUL.getRtnmessage()); //學生資訊已刪除
	}

	/* ======================================================== */
	// 選課 course selection (不得超過10學分、不能選相同名稱的課程、不能衝堂)
	@Override   //覆寫、重新定義
	public SelectCourseRes courseSelection(String studentId, Set<String> coursCode) {
		SelectCourseRes selectCourseRes = new SelectCourseRes();

		// 選課學生資訊不得為空 || 選取的課程不得為空。  補充：單獨的.isEmpty() 不能接受空，但可以接受null
		if (!StringUtils.hasText(studentId) || CollectionUtils.isEmpty(coursCode)) {
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage());  //請先輸入個人資訊再選課
		}

		// 確認是否有此課程代碼
		List<School> confirmClass = schoolDao.findAll(); // 所有DB Shool的內容
		List<String> keepCourse = new ArrayList<>();
		for (School schoolAllCurse : confirmClass) {
			keepCourse.add(schoolAllCurse.getCourseCode());
		}
//		if(!keepCourse.containsAll(courscode)) {
//			selectCourseRes.setMessage("查無課程代碼 無法選課");
//		}
		boolean b = keepCourse.containsAll(coursCode); // 輸入的課程是否符合DB全部的課成代碼
		if (b == false) {
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //查無課程代碼
		}

		// 確認是否有此學生
		Optional<Student> confirmStudentId = studentDao.findById(studentId); // 沒有
		if (!confirmStudentId.isPresent()) {
			return new SelectCourseRes(SelectCourseRtnCode.FORMAT_ERROR.getRtnmessage()); //學號輸入錯誤
		}
		Student getAllStudent = confirmStudentId.get(); // 有 --> 透過學號(pk)取得此學生的所有個人資訊

		if (StringUtils.hasText(getAllStudent.getStudentClassCode())) {
			return new SelectCourseRes(SelectCourseRtnCode.HAVE_CLASS_GO_ADDOROUT_CLASS.getRtnmessage()); //你已選過課 請去加退選
		}
		// ==============================================================
		// 選課不得超過10學分 and不能選相同名稱的課程
		List<String> courseList = new ArrayList<>(); // 所有課程的清單
		Set<String> courseSet = new HashSet<>(); // 不得重複的課程清單

		int allUnits = 0; // 將總學分預設為0
		List<School> getAllCourseCode = schoolDao.findAllByCoursecodeIn(coursCode); // courscode：從PK取得課程資訊
		for (School keepAllCourseCodeContent : getAllCourseCode) { // 從課程代碼抓取課程名稱
			courseList.add(keepAllCourseCodeContent.getCourse());
			courseSet.add(keepAllCourseCodeContent.getCourse());
			allUnits = allUnits + keepAllCourseCodeContent.getUnits(); // 從課程代碼抓取學分資訊
//			allUnits += keepAllCourseCodeContent.getUnits();              
			keepAllCourseCodeContent.getClassDay(); // 從課程代碼抓取上課星期
			keepAllCourseCodeContent.getClassTime(); // 從課程代碼抓取上課時間
			keepAllCourseCodeContent.getRecess(); // 從課程代碼抓取下課時間
		}
		if (courseList.size() != courseSet.size()) { // 利用清單的限制 已課程名稱長度判斷是否有選到相同名稱的課程
			return new SelectCourseRes(SelectCourseRtnCode.NOT_SAME_CLASS.getRtnmessage()); //無法加選相同名稱的課程
		}

		if (allUnits > 10) {
			return new SelectCourseRes(SelectCourseRtnCode.CREDIT_ERROR.getRtnmessage()); //超過10學分限制
		}

		// ==============================================================
		// 不得衝堂

		List<List<String>> lookDayAndGoOutTimeNo = new ArrayList<>();
		List<School> geAllCourseCode = schoolDao.findAllByCoursecodeIn(coursCode); // courscode：從PK取得課程資訊
		for (School keepCourseCodeContent : geAllCourseCode) { // foreach一個個遍歷 帶出資訊
			List<String> courseDayAndGoOutTime = new ArrayList<>(); // 把星期 + 上下時間放進清單裡
			courseDayAndGoOutTime.add(keepCourseCodeContent.getClassDay());
			DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm"); // 設定字串格式
			String goTimeStr = df.format(keepCourseCodeContent.getClassTime()); // 將Local time轉成字串
			String outTimeStr = df.format(keepCourseCodeContent.getRecess());
			courseDayAndGoOutTime.add(goTimeStr); // 加入上課時間
			courseDayAndGoOutTime.add(outTimeStr); // 加入下課時間
			lookDayAndGoOutTimeNo.add(courseDayAndGoOutTime);
		}
		for (int i = 0; i < lookDayAndGoOutTimeNo.size(); i++) {
			String oneClassDay = lookDayAndGoOutTimeNo.get(i).get(0); // 比對星期
			for (int j = i + 1; j < lookDayAndGoOutTimeNo.size(); j++) {
				String twoClassDay = lookDayAndGoOutTimeNo.get(j).get(0);
				if (oneClassDay == twoClassDay) {
					// 第一堂
					String oneClassGoDay = lookDayAndGoOutTimeNo.get(i).get(1);
					int oneClassGoDayInt = Integer.parseInt(oneClassGoDay);
					String oneClassoutDay = lookDayAndGoOutTimeNo.get(i).get(2);
					int oneClassOutDayInt = Integer.parseInt(oneClassoutDay);
					// 第二堂..........
					String twoClassGoDay = lookDayAndGoOutTimeNo.get(j).get(1);
					int twoClassGoDayInt = Integer.parseInt(twoClassGoDay);
					String twoClassoutDay = lookDayAndGoOutTimeNo.get(j).get(2);
					int twoClassOutDayInt = Integer.parseInt(twoClassoutDay);
					if (betweenExclude(oneClassGoDayInt, oneClassOutDayInt, twoClassGoDayInt, twoClassOutDayInt)) {
						return new SelectCourseRes(SelectCourseRtnCode.OUTFID.getRtnmessage()); //衝堂 無法選課
					}
				}
			}
		}
		String courscodeToString = coursCode.toString(); // 將Set<String>轉成字串，因此前面要賦予字串型態
		String courscodeStr = courscodeToString.substring(1, courscodeToString.length() - 1); // 去頭尾(括號)的方法：.substring(以index來算/有包含,陣列是用size
																								// 字串是用.length
																								// -1/不包含那個位子的內容); Ex
																								// (1,5) = 包含1，不包含5
																								// -->取到的是 1- 4

		getAllStudent.setStudentClassCode(courscodeStr);
		studentDao.save(getAllStudent);
		return new SelectCourseRes(getAllStudent,SelectCourseRtnCode.SELECT_COURSE_SUCCESSFUL.getRtnmessage()); //選課成功
	}

	// ============內部時間方法============
	private boolean betweenExclude(int start1, int end1, int start2, int end2) {
//		return start1 >= end2 || end1 >= start2 ; //true= 不衝堂
		return !(start1 >= end2) && !(end1 <= start2); // true= 衝堂
	}
	// ============內部時間方法============

	/* ======================================================== */
	// 加選
	@Override   //覆寫、重新定義
	public SelectCourseRes addClass(String studentId, Set<String> coursCode) {
		// 判斷學號、課程代碼是否為空
		SelectCourseRes selectCourseRes = new SelectCourseRes();
		String coursecodeStr = coursCode.toString();
		String coursecodeDelet = coursecodeStr.substring(1, coursecodeStr.length() - 1);
		if (!StringUtils.hasText(studentId) || CollectionUtils.isEmpty(coursCode) || !StringUtils.hasText(coursecodeDelet)) {
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage());
		}
		
		// 確認是否有此課程代碼
				List<School> confirmClass = schoolDao.findAll(); // 所有DB Shool的內容
				List<String> keepCourse = new ArrayList<>();
				for (School schoolAllCurse : confirmClass) {
					keepCourse.add(schoolAllCurse.getCourseCode());
				}
//				if(!keepCourse.containsAll(courscode)) {
//					selectCourseRes.setMessage("查無課程代碼 無法選課");
//				}
				boolean b = keepCourse.containsAll(coursCode); // 輸入的課程是否符合DB全部的課成代碼
				if (b == false) {
					return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //查無課程代碼
				}

		// 確認此學生是否存在
		Optional<Student> studentOp = studentDao.findById(studentId);
		if (!studentOp.isPresent()) {
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //查無此人
		}

		if (studentOp.isPresent()) {
			Student studentCoursCode = studentOp.get(); // 取得單一學生的所有個人資訊 (新選課資料)
			if (!StringUtils.hasText(studentCoursCode.getStudentClassCode())) {
				return new SelectCourseRes(SelectCourseRtnCode.NOT_CLASS_GO_ADD_CLASS.getRtnmessage()); //你沒選過課 請在此加選
			}

			String studentOldstr = studentOp.get().getStudentClassCode(); // 原始選課資料
			String studentCourscode = coursCode.toString(); // 將set轉成string
			String courscodeStr = studentCourscode.substring(1, studentCourscode.length() - 1); // 去除顯示結果所出現的前後括號
			studentCoursCode.setStudentClassCode(courscodeStr); // 將轉換型態的加選課程放回資訊欄

			// 將新舊資料放入list
			Set<String> oldNewOpClass = new HashSet<>(); // 不重複元素的List

			// 去除課程代碼的逗號及空格
			String[] removeComma = studentOldstr.split(",");
			for (String removeNull : removeComma) {
				oldNewOpClass.add(removeNull.trim());
			}
			String[] removeComma2 = courscodeStr.split(",");
			for (String removeNull2 : removeComma2) {
				oldNewOpClass.add(removeNull2.trim());
			}

			// =================================================================================
			// 選課不得超過10學分 and不能選相同名稱的課程
			List<String> courseList = new ArrayList<>(); // 所有課程的清單
			Set<String> courseSet = new HashSet<>(); // 不得重複的課程清單

			int allUnits = 0; // 將總學分預設為0
			List<School> getAllCourseCode = schoolDao.findAllByCoursecodeIn(oldNewOpClass); // oldNewOpClass：從PK取得課程資訊
			for (School keepAllCourseCodeContent : getAllCourseCode) { // 從課程代碼抓取課程名稱
				courseList.add(keepAllCourseCodeContent.getCourse());
				courseSet.add(keepAllCourseCodeContent.getCourse());
				allUnits = allUnits + keepAllCourseCodeContent.getUnits(); // 從課程代碼抓取學分資訊
//				allUnits += keepAllCourseCodeContent.getUnits();              
				keepAllCourseCodeContent.getClassDay(); // 從課程代碼抓取上課星期
				keepAllCourseCodeContent.getClassTime(); // 從課程代碼抓取上課時間
				keepAllCourseCodeContent.getRecess(); // 從課程代碼抓取下課時間
			}
			if (courseList.size() != courseSet.size()) { // 利用清單的限制 已課程名稱長度判斷是否有選到相同名稱的課程
				return new SelectCourseRes(SelectCourseRtnCode.NOT_SAME_CLASS.getRtnmessage()); //無法加選相同名稱的課程
			}

			if (allUnits > 10) {
				return new SelectCourseRes(SelectCourseRtnCode.CREDIT_ERROR.getRtnmessage()); //超過10學分限制 無法選課
			}

			// ==============================================================
			// 不得衝堂

			List<List<String>> lookDayAndGoOutTimeNo = new ArrayList<>();
			List<School> geAllCourseCode = schoolDao.findAllByCoursecodeIn(oldNewOpClass); // oldNewOpClass：從PK取得課程資訊
			for (School keepCourseCodeContent : geAllCourseCode) { // foreach一個個遍歷 帶出資訊
				List<String> courseDayAndGoOutTime = new ArrayList<>(); // 把星期+上下時間放進清單裡
				courseDayAndGoOutTime.add(keepCourseCodeContent.getClassDay());
				DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm"); // 設定字串格式
				String goTimeStr = df.format(keepCourseCodeContent.getClassTime()); // 將Local time轉成字串
				String outTimeStr = df.format(keepCourseCodeContent.getRecess());
				courseDayAndGoOutTime.add(goTimeStr); // 加入上課時間
				courseDayAndGoOutTime.add(outTimeStr); // 加入下課時間
				lookDayAndGoOutTimeNo.add(courseDayAndGoOutTime);
			}
			for (int i = 0; i < lookDayAndGoOutTimeNo.size(); i++) {
				String oneClassDay = lookDayAndGoOutTimeNo.get(i).get(0); // 比對星期
				for (int j = i + 1; j < lookDayAndGoOutTimeNo.size(); j++) {
					String twoClassDay = lookDayAndGoOutTimeNo.get(j).get(0);
					if (oneClassDay == twoClassDay) {
						// 第一堂
						String oneClassGoDay = lookDayAndGoOutTimeNo.get(i).get(1);
						int oneClassGoDayInt = Integer.parseInt(oneClassGoDay);
						String oneClassoutDay = lookDayAndGoOutTimeNo.get(i).get(2);
						int oneClassOutDayInt = Integer.parseInt(oneClassoutDay);
						// 第二堂..........
						String twoClassGoDay = lookDayAndGoOutTimeNo.get(j).get(1);
						int twoClassGoDayInt = Integer.parseInt(twoClassGoDay);
						String twoClassoutDay = lookDayAndGoOutTimeNo.get(j).get(2);
						int twoClassOutDayInt = Integer.parseInt(twoClassoutDay);
						if (betweenExclude(oneClassGoDayInt, oneClassOutDayInt, twoClassGoDayInt, twoClassOutDayInt)) {
							return new SelectCourseRes(SelectCourseRtnCode.OUTFID.getRtnmessage()); //衝堂 無法選課
						}
					}
				}
			}
			String oldNewOpClassStr = oldNewOpClass.toString();
			String removeBrackets = oldNewOpClassStr.substring(1, oldNewOpClassStr.length() - 1);
			studentCoursCode.setStudentClassCode(removeBrackets);
			studentDao.save(studentCoursCode);
			selectCourseRes.setStudent(studentCoursCode); // 存到JSON讓他可以顯示在Res上
			return new SelectCourseRes(SelectCourseRtnCode.CLASS_ADD_SUCCESSFUL.getRtnmessage()); //課程加選成功
		}
		return null;
	}

	/* ======================================================== */
//	退選
	@Override  //覆寫、重新定義
	public SelectCourseRes WithdrawClass(String studentId, List<String> coursCode) {

		// 學號不得為空
		if (!StringUtils.hasText(studentId)) {
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //學號不得為空
		}

		Optional<Student> studentOp = studentDao.findById(studentId);
		// 確認是否有此學生
				if (!studentOp.isPresent()) {
					return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage()); //查無此人
				}
		Student studentAll = studentOp.get();

		String studentCourseCode = studentAll.getStudentClassCode();
		// 取得學生選課代碼並轉換成List
		
		List<String> codeList = new ArrayList<>();
		// 去除課程代碼的逗號及空格
					String[] removeComma = studentCourseCode.split(",");     //因為String不能foreach，所以先利用逗號及空白切割成一個個的東西
					for (String removeNull : removeComma) {                   //再將它一個個遍歷出來
						codeList.add(removeNull.trim());                           //存入陣列裡
					}
		
		// 取得使用者輸入的退選課程代碼
		List<School> courscodeList = schoolDao.findAllByCoursecodeIn(coursCode);
		List<String> removeList = new ArrayList<>();
		for (School item : courscodeList) {
			if (codeList.contains(item.getCourseCode())) {
				removeList.add(item.getCourseCode());
			}
		}
		
		//確認是否有這堂課
		if(removeList.isEmpty()) {
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage());  //查無此課程
		}
		
		codeList.removeAll(removeList);

		studentAll.setStudentClassCode(codeList.toString().substring(1, (codeList.toString().length() - 1)));
		studentDao.save(studentAll);
		return new SelectCourseRes(SelectCourseRtnCode.WITHDRAW_CLASS_SUCCESSFUL.getRtnmessage()); //退選成功
	}

	/* ======================================================== */
	// 學生所選課程總覽 class Overview (透過學號查詢)
	@Override   //覆寫、重新定義
	public SelectCourseRes classOverview(String studentId) {

		if (!StringUtils.hasText(studentId)) {
			return new SelectCourseRes(SelectCourseRtnCode.NOT_NULL.getRtnmessage()); //學號不得為空
		}

		Optional<Student> student = studentDao.findById(studentId);
		if (!student.isPresent()) {
			return new SelectCourseRes(SelectCourseRtnCode.NO_DATA.getRtnmessage());      //查無此學生
		}
		

		Student studentCalss = student.get();
		String classCode = studentCalss.getStudentClassCode();
		List<String> classCodeList = new ArrayList<>();
		// 用逗號隔開去空白後 放進List裡
					String[] removeComma = classCode.split(",");
					for (String removeNull : removeComma) {
						classCodeList.add(removeNull.trim());
					}
//					List<School> school1 = new ArrayList<>();
//					school1.addAll(schoolDao.findAllById(classCodeList));
		List<School> school =  schoolDao.findAllById(classCodeList);
		//回傳其他課程資訊及課程代碼，查詢成功
		return new SelectCourseRes(school, studentCalss, SelectCourseRtnCode.QUERY_SUCCESSFUL.getRtnmessage());
	}
}
