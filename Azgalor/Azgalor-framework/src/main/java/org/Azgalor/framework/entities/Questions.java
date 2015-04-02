package org.Azgalor.framework.entities;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.Azgalor.framework.config.QuestionConfig;
import org.Azgalor.mongodb.MongoEntity;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;

public class Questions extends MongoEntity<Questions> {
	private static final long serialVersionUID = 8449888892369293844L;
	private String title;
	/**
	 * 问卷题目类型
	 * 
	 * @see QuestionConfig
	 */
	private int type;
	private List<String> options;

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

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.put("options", options);
		this.options = options;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Questions convert(Document doc) {
		// 该方法3.0以下版本，mongodb 3.0以上估计有错
		this.setId((ObjectId) doc.get("_id"));
		this.setTitle(doc.get("title").toString());// 获取标题
		this.setType(Integer.valueOf(doc.get("type").toString()));// 获取类型
		BasicDBList options = (BasicDBList) doc.get("options");// 获取选项值
		List<String> oList = null;
		if (options.size() > 0) {
			oList = options.stream().map(Object::toString)
					.collect(Collectors.toList());// 用流的方式转换
		}
		this.setOptions(oList);
		return this;
	}

}
