package com.example.demo_selectcourse.constants;

public enum SelectCourseMessageCode { // enumerated (enum) 列舉

	// 課程創建、輸入成功
	SUCCESSFUL("200", "Successful"),

	// 選課成功
	SELECT_COURSE_SUCCESSFUL("200", "Successful"),

	// 課程修改成功
	REVISE_SUCCESSFUL("200", "Revise Successful"),

	// 課程刪除成功
	DELETE_SUCCESSFUL("200", "Delete Successful"),

	// 課程查詢成功
	QUERY_SUCCESSFUL("200", "Query Successful"),

	// 課程加選成功
	CLASS_ADD_SUCCESSFUL("200", "Class add Successful"),

	// 課程退選成功
	WITHDRAW_CLASS_SUCCESSFUL("200", "Withdraw class Successful"),

	// 欄位不得為空
	NOT_NULL("400", "Value can not null!!!"),

	// 輸寫格式錯誤
	FORMAT_FAIL("400", "format fail"),

	// 學分範圍不符
	CREDIT_FAIL("400", "credit fail"),

	// 課程代碼已存在
	CLASS_CODE_EXISTED("400", "class code existed"),

	// 學號已存在
	STUDENT_ID_EXISTED("400", "student id existed"), 
	
	// 查無資訊
	NO_DATA("400", "no data"), 
	
	// 你已選過課 請去加退選
	HAVE_CLASS_GO_ADDOROUT_CLASS("400", "Selected Courses,Please go and withdraw."), 
	
	// 無法加選相同名稱的課程
	NOT_SAME_CLASS("400", "had to repeat the course."), 
	
	// 衝堂
	OUTFID("400", "outfid"), 
	
	// 你沒選過課 請在此加選
	NOT_CLASS_GO_ADD_CLASS("400", "No classes have been selected yet. Please add them."); 

	// 名稱自取 (code , message)
	private String code;
	private String message;

	private SelectCourseMessageCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
