package org.Azgalor.framework.dao;

import java.util.List;

import org.Azgalor.framework.entities.Questions;
import org.Azgalor.mongodb.annotations.MongoCollection;
import org.Azgalor.mongodb.annotations.MongoDBName;
import org.Azgalor.mongodb.annotations.Mongoz;
import org.Azgalor.mongodb.dao.MongoDao;
import org.Azgalor.mongodb.enums.MongoType;
import org.springframework.stereotype.Repository;

import com.mongodb.DBObject;

@Repository
@Mongoz(MongoType.SIMPLE)
@MongoCollection("question")
@MongoDBName("azgalor")
public class QuestionDaoImpl extends MongoDao<Questions> implements QuestionDao {

	@Override
	public boolean insertQuestions(Questions questions) {
		return this.insert(questions);
	}

	@Override
	public boolean updateQuestions(Questions questions) {
		return this.update(questions);
	}

	@Override
	public boolean deleteQuestions(Questions questions) {
		return this.delete(questions);
	}

	@Override
	public List<DBObject> listQuestions(DBObject obj) {
		return this.find(dbName, collection, obj);
	}

	@Override
	public DBObject getQuestionsById(Object id) {
		return this.getById(dbName, collection, id);
	}

}
