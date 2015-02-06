package org.Azgalor.framework.entities;

import java.util.List;

import org.Azgalor.mongodb.MongoEntity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Anwsers extends MongoEntity<Anwsers> {
	private static final long serialVersionUID = -54780414053238859L;
	private String userId;// 答题人id
	private String questionnaireId;// 问卷id
	private List<Anwser> list;// 答题结果

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.put("userId", userId);
		this.userId = userId;
	}

	public String getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.put("questionnaireId", questionnaireId);
		this.questionnaireId = questionnaireId;
	}

	public List<Anwser> getList() {
		return list;
	}

	public void setList(List<Anwser> list) {
		this.put("list", list);
		this.list = list;
	}

	public class Anwser extends BasicDBObject {
		private static final long serialVersionUID = 7013643117222611222L;
		private int no;// 序号
		private String questionId;// 题名id
		private String result;// 答题结果

		public int getNo() {
			return no;
		}

		public void setNo(int no) {
			this.put("no", no);
			this.no = no;
		}

		public String getQuestionId() {
			return questionId;
		}

		public void setQuestionId(String questionId) {
			this.put("questionId", questionId);
			this.questionId = questionId;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.put("result", result);
			this.result = result;
		}

	}

	@Override
	public Anwsers convert(DBObject obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
