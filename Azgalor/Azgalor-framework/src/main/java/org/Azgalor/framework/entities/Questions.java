package org.Azgalor.framework.entities;

import java.util.List;
import java.util.stream.Collectors;

import org.Azgalor.framework.util.QuestionStatic;
import org.Azgalor.mongodb.MongoEntity;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

public class Questions extends MongoEntity<Questions> {
	private static final long serialVersionUID = 8449888892369293844L;
	private String title;
	/**
	 * 问卷题目类型
	 * 
	 * @see QuestionStatic
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
	public Questions convert(DBObject obj) {
		this.setId((ObjectId) obj.get("_id"));
		this.setTitle(obj.get("title").toString());// 获取标题
		this.setType(Integer.valueOf(obj.get("type").toString()));// 获取类型
		BasicDBList options = (BasicDBList) obj.get("options");// 获取选项值
		List<String> oList = null;
		if (options.size() > 0) {
			oList = options.stream().map(Object::toString)
					.collect(Collectors.toList());// 用流的方式转换
		}
		this.setOptions(oList);
		return this;
	}

}
