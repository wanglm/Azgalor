package org.Azgalor.framework.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.Azgalor.framework.dao.QuestionDao;
import org.Azgalor.framework.dao.QuestionDaoImpl;
import org.Azgalor.framework.entities.Messages;
import org.Azgalor.framework.entities.Questions;
import org.Azgalor.framework.service.QuestionService;
import org.Azgalor.mongodb.MongoFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;



@Service
public class QuestionServiceImpl implements QuestionService {
	private Logger log = LogManager.getLogger(QuestionServiceImpl.class);
	@Autowired
	@Qualifier("mongoFactory")
	private MongoFactory mf;

	@Override
	public Messages saveQuestions(Questions questions) {
		Messages msg = new Messages();
		try {
			QuestionDao dao = mf.get(QuestionDaoImpl.class);
			if (questions.get("_id") == null) {
				dao.insertQuestions(questions);
				msg.setSuccess(true);
				msg.setMessage("问题添加成功！");
			} else {
				msg.setSuccess(dao.updateQuestions(questions));
				msg.setMessage("问题更新成功！");
			}
		} catch (Exception e) {
			log.error("saveQuestions(Questions questions)错误:", e);
		}
		return msg;
	}

	@Override
	public Messages deleteQuestions(Questions questions) {
		Messages msg = new Messages();
		try {
			QuestionDao dao = mf.get(QuestionDaoImpl.class);
			msg.setSuccess(dao.deleteQuestions(questions));
			msg.setMessage("问题删除成功！");
		} catch (Exception e) {
			log.error("deleteQuestions(Questions questions)错误:", e);
		}
		return msg;
	}

	@Override
	public List<Questions> listQuestions(Document obj) {
		List<Questions> list = null;
		try {
			QuestionDao dao = mf.get(QuestionDaoImpl.class);
			List<Document> _list = dao.listQuestions(obj);
			list = new ArrayList<Questions>(_list.size());
			for (Document o : _list) {
				Questions questions = new Questions();
				questions = questions.convert(o);
				list.add(questions);
			}
		} catch (Exception e) {
			log.error("listQuestions(Document obj)错误:", e);
		}
		return list;
	}

	@Override
	public Questions findOneQuestions(String id) {
		Questions questions = new Questions();
		try {
			QuestionDao dao = mf.get(QuestionDaoImpl.class);
			Document obj = dao.getQuestionsById(id);
			questions = questions.convert(obj);
		} catch (Exception e) {
			log.error("findOneQuestions(String id)错误:", e);
		}
		return questions;
	}

}
