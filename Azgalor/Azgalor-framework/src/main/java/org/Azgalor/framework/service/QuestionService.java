package org.Azgalor.framework.service;

import java.util.List;

import org.Azgalor.framework.entities.Messages;
import org.Azgalor.framework.entities.Questions;
import org.bson.Document;



/**
 * 问卷和问题service
 * 
 * @author ming
 *
 */
public interface QuestionService {

	/**
	 * 保存问题
	 * 
	 * @param questions
	 * @return Messages
	 */
	public Messages saveQuestions(Questions questions);

	/**
	 * 删除问题
	 * 
	 * @param questions
	 * @return Messages
	 */
	public Messages deleteQuestions(Questions questions);

	/**
	 * 查询问题集合
	 * 
	 * @param obj
	 * @return List<Questions>
	 */
	public List<Questions> listQuestions(Document obj);

	/**
	 * 查询指定id的问题
	 * 
	 * @param id
	 * @return Questions
	 */
	public Questions findOneQuestions(String id);

}
