package org.Azgalor.framework.dao;

import java.util.List;

import org.Azgalor.framework.entities.Questions;
import org.bson.Document;

public interface QuestionDao {
	public void insertQuestions(Questions questions);

	public boolean updateQuestions(Questions questions);

	public boolean deleteQuestions(Questions questions);

	public List<Document> listQuestions(Document doc);

	public Document getQuestionsById(Object id);

}
