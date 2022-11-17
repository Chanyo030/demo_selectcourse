package com.example.demo_selectcourse.constants;

public enum SelectCourseRtnCode {           //enumerated (enum) 列舉

	
	SUCCESSFUL ("200","Successful"),    //創建、輸入成功
	SELECT_COURSE_SUCCESSFUL("200","Successful"), //選課成功
	REVISE_SUCCESSFUL ("200","Revise Successful"), //修改
	DELETE_SUCCESSFUL ("200","Delete Successful"),  //刪除
	QUERY_SUCCESSFUL ("200","Query Successful"),   //查詢
	CLASS_ADD_SUCCESSFUL("200","Class add Successful"),  //課程加選成功
	WITHDRAW_CLASS_SUCCESSFUL("200","Withdraw class Successful"),
	NOT_NULL ("400","Value can not null!!!"),  //欄位不得為空
	FORMAT_ERROR ("400","format error"),  //輸寫格式錯誤
	CREDIT_ERROR ("400","credit error"), //學分範圍不符
    CLASS_CODE_EXISTED("400","class code existed"),  //課程代碼已存在
    STUDENT_ID_EXISTED("400","student id existed"), //學號已存在
    NO_DATA("400","no data"), //查無資訊
    HAVE_CLASS_GO_ADDOROUT_CLASS ("400","Selected Courses,Please go and withdraw."),//你已選過課 請去加退選
	NOT_SAME_CLASS("400","had to repeat the course."),//無法加選相同名稱的課程
	OUTFID("400","outfid"),//衝堂
	NOT_CLASS_GO_ADD_CLASS("400","No classes have been selected yet. Please add them.");	 //你沒選過課 請在此加選
	
	
	//名稱自取  (code , message)
	private String code;
	private String rtnmessage;
	
	private SelectCourseRtnCode(String code, String rtnmessage) {
		this.code = code;
		this.rtnmessage = rtnmessage;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getRtnmessage() {
		return rtnmessage;
	}
}
