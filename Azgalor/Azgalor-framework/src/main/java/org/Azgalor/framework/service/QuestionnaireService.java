package org.Azgalor.framework.service;

import java.util.List;

import org.Azgalor.framework.entities.Messages;
import org.Azgalor.framework.entities.Questionnaire;

import com.mongodb.DBObject;

public interface QuestionnaireService {
	/**
	 * 保存问卷
	 * 
	 * @param questionnaire
	 * @return Messages
	 */
	public Messages saveQuestionnaire(Questionnaire questionnaire);

	/**
	 * 删除问卷
	 * 
	 * @param questionnaire
	 * @return Messages
	 */
	public Messages deleteQuestionnaire(Questionnaire questionnaire);

	/**
	 * 查询问卷集合
	 * 
	 * @param obj
	 * @return List<Questionnaire>
	 */
	public List<Questionnaire> listQuestionnaire(DBObject obj);

	/**
	 * 查询指定Id的问卷
	 * 
	 * @param id
	 * @return Questionnaire
	 */
	public Questionnaire findOneQuestionnaire(String id);
}
