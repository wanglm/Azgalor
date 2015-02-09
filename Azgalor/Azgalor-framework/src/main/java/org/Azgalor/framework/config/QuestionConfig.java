package org.Azgalor.framework.util;

public class QuestionStatic {

	/**
	 * 普通
	 */
	public static final int QUESTIONNAIRE_NOMAL = 0;
	/**
	 * 锁定，超级管理员可以解锁，或者问卷用户预设权限以上权限可以解锁
	 */
	public static final int QUESTIONNAIRE_LOCK = 1;

	/**
	 * 输入框
	 */
	public static final int QUESTION_TEXT = 0;
	/**
	 * 单选
	 */
	public static final int QUESTION_REDIO = 1;
	/**
	 * 下拉选择
	 */
	public static final int QUESTION_SELECT = 2;
	/**
	 * 多选
	 */
	public static final int QUESTION_CHECK = 4;
	/**
	 * 文本输入
	 */
	public static final int QUESTION_TEXT_ERA = 5;
	/**
	 * 拖动条
	 */
	public static final int QUESTION_PROCESS = 6;

	/**
	 * 未选择
	 */
	public static final String ANWSER_UNSELECT = "no";
	/**
	 * 已选择
	 */
	public static final String ANWSER_SELECT = "yes";

}
