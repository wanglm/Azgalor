package org.Azgalor.framework.dao;

import java.util.List;

import org.Azgalor.framework.entities.Questions;

import com.mongodb.DBObject;

public interface QuestionDao {
	public boolean insertQuestions(Questions questions);

	public boolean updateQuestions(Questions questions);

	public boolean deleteQuestions(Questions questions);
	
	public List<DBObject> listQuestions(DBObject obj);
	
	public DBObject getQuestionsById(Object id);

}
