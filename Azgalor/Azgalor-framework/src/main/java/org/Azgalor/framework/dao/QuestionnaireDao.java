package org.Azgalor.framework.dao;

import org.Azgalor.framework.entities.Questionnaire;

public interface QuestionnaireDao {
	public void insertQuestionnaire(Questionnaire questionnaire);

	public boolean updateQuestionnaire(Questionnaire questionnaire);

	public boolean deleteQuestionnaire(Questionnaire questionnaire);
}
