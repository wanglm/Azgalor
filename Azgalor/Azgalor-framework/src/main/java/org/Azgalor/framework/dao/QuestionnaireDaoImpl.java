package org.Azgalor.framework.dao;

import org.Azgalor.framework.entities.Questionnaire;
import org.Azgalor.mongodb.annotations.Mongoz;
import org.Azgalor.mongodb.dao.MongoDao;
import org.Azgalor.mongodb.enums.MongoType;
import org.springframework.stereotype.Repository;

@Repository
@Mongoz(MongoType.SIMPLE)
public class QuestionnaireDaoImpl extends MongoDao<Questionnaire> implements
		QuestionnaireDao {

	@Override
	public boolean insertQuestionnaire(Questionnaire questionnaire) {
		return this.insert(questionnaire);
	}

	@Override
	public boolean updateQuestionnaire(Questionnaire questionnaire) {
		return this.update(questionnaire);
	}

	@Override
	public boolean deleteQuestionnaire(Questionnaire questionnaire) {
		return this.delete(questionnaire);
	}

}
