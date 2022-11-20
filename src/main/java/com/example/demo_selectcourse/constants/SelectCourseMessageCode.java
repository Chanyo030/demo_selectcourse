package com.example.demo_selectcourse.constants;

public enum SelectCourseMessageCode { // enumerated (enum) �C�|

	// �ҵ{�ЫءB��J���\
	SUCCESSFUL("200", "Successful"),

	// ��Ҧ��\
	SELECT_COURSE_SUCCESSFUL("200", "Successful"),

	// �ҵ{�ק令�\
	REVISE_SUCCESSFUL("200", "Revise Successful"),

	// �ҵ{�R�����\
	DELETE_SUCCESSFUL("200", "Delete Successful"),

	// �ҵ{�d�ߦ��\
	QUERY_SUCCESSFUL("200", "Query Successful"),

	// �ҵ{�[�令�\
	CLASS_ADD_SUCCESSFUL("200", "Class add Successful"),

	// �ҵ{�h�令�\
	WITHDRAW_CLASS_SUCCESSFUL("200", "Withdraw class Successful"),

	// ��줣�o����
	NOT_NULL("400", "Value can not null!!!"),

	// ��g�榡���~
	FORMAT_FAIL("400", "format fail"),

	// �Ǥ��d�򤣲�
	CREDIT_FAIL("400", "credit fail"),

	// �ҵ{�N�X�w�s�b
	CLASS_CODE_EXISTED("400", "class code existed"),

	// �Ǹ��w�s�b
	STUDENT_ID_EXISTED("400", "student id existed"), 
	
	// �d�L��T
	NO_DATA("400", "no data"), 
	
	// �A�w��L�� �Хh�[�h��
	HAVE_CLASS_GO_ADDOROUT_CLASS("400", "Selected Courses,Please go and withdraw."), 
	
	// �L�k�[��ۦP�W�٪��ҵ{
	NOT_SAME_CLASS("400", "had to repeat the course."), 
	
	// �İ�
	OUTFID("400", "outfid"), 
	
	// �A�S��L�� �Цb���[��
	NOT_CLASS_GO_ADD_CLASS("400", "No classes have been selected yet. Please add them."); 

	// �W�٦ۨ� (code , message)
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
