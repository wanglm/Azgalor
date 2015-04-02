package org.Azgalor.framework.entities;

import java.util.List;
import java.util.Map;

import org.Azgalor.framework.config.QuestionConfig;
import org.Azgalor.framework.config.UsersConfig;
import org.Azgalor.mongodb.MongoEntity;
import org.bson.Document;

import com.mongodb.BasicDBObject;

public class Questionnaire extends MongoEntity<Questionnaire> {
	private static final long serialVersionUID = -4098032903589684671L;
	private String title;
	/**
	 * 问卷类型
	 * 
	 * @see QuestionConfig
	 */
	private int type;
	/**
	 * 用户权限类型
	 * 
	 * @see UsersConfig
	 */
	private int userType;
	private List<Question> list;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.put("title", title);
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.put("type", type);
		this.type = type;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.put("userType", userType);
		this.userType = userType;
	}

	public List<Question> getList() {
		return list;
	}

	public void setList(List<Question> list) {
		this.put("list", list);
		this.list = list;
	}

	public class Question extends BasicDBObject {
		private static final long serialVersionUID = -2298269537746239250L;
		private int no;// 序号
		private Questions questions;

		public int getNo() {
			return no;
		}

		public void setNo(int no) {
			this.put("no", no);
			this.no = no;
		}

		public Questions getQuestions() {
			return questions;
		}

		public void setQuestions(Questions questions) {
			this.put("questions", questions);
			this.questions = questions;
		}

	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Questionnaire convert(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}
}
