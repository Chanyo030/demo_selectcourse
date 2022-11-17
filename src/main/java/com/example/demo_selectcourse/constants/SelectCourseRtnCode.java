package com.example.demo_selectcourse.constants;

public enum SelectCourseRtnCode {           //enumerated (enum) �C�|

	
	SUCCESSFUL ("200","Successful"),    //�ЫءB��J���\
	SELECT_COURSE_SUCCESSFUL("200","Successful"), //��Ҧ��\
	REVISE_SUCCESSFUL ("200","Revise Successful"), //�ק�
	DELETE_SUCCESSFUL ("200","Delete Successful"),  //�R��
	QUERY_SUCCESSFUL ("200","Query Successful"),   //�d��
	CLASS_ADD_SUCCESSFUL("200","Class add Successful"),  //�ҵ{�[�令�\
	WITHDRAW_CLASS_SUCCESSFUL("200","Withdraw class Successful"),
	NOT_NULL ("400","Value can not null!!!"),  //��줣�o����
	FORMAT_ERROR ("400","format error"),  //��g�榡���~
	CREDIT_ERROR ("400","credit error"), //�Ǥ��d�򤣲�
    CLASS_CODE_EXISTED("400","class code existed"),  //�ҵ{�N�X�w�s�b
    STUDENT_ID_EXISTED("400","student id existed"), //�Ǹ��w�s�b
    NO_DATA("400","no data"), //�d�L��T
    HAVE_CLASS_GO_ADDOROUT_CLASS ("400","Selected Courses,Please go and withdraw."),//�A�w��L�� �Хh�[�h��
	NOT_SAME_CLASS("400","had to repeat the course."),//�L�k�[��ۦP�W�٪��ҵ{
	OUTFID("400","outfid"),//�İ�
	NOT_CLASS_GO_ADD_CLASS("400","No classes have been selected yet. Please add them.");	 //�A�S��L�� �Цb���[��
	
	
	//�W�٦ۨ�  (code , message)
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
