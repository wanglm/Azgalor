package org.Azgalor.framework.dao;

import java.util.List;

import org.Azgalor.framework.entities.Questions;
import org.Azgalor.mongodb.annotations.MongoCollection;
import org.Azgalor.mongodb.annotations.MongoDBName;
import org.Azgalor.mongodb.annotations.Mongoz;
import org.Azgalor.mongodb.dao.MongoDao;
import org.Azgalor.mongodb.enums.MongoType;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
@Mongoz(MongoType.SIMPLE)
@MongoCollection("question")
@MongoDBName("azgalor")
public class QuestionDaoImpl extends MongoDao<Questions> implements QuestionDao {

	@Override
	public void insertQuestions(Questions questions) {
		this.insert(questions);
	}

	@Override
	public boolean updateQuestions(Questions questions) {
		return this.updateById(questions);
	}

	@Override
	public boolean deleteQuestions(Questions questions) {
		return this.deleteById(questions);
	}

	@Override
	public List<Document> listQuestions(Document obj) {
		return this.find(dbName, collection, obj);
	}

	@Override
	public Document getQuestionsById(Object id) {
		return this.getById(dbName, collection, id);
	}

}
