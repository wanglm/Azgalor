package org.Azgalor.mongodb;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * mongodb存储对象基类
 * 
 * @author ming
 *
 */
public abstract class MongoEntity<T> extends BasicDBObject {
	private static final long serialVersionUID = 5293439786916585037L;
	protected ObjectId id;// 保存_id
	protected String updateId;// 修改者id
	protected Long updateTime;// 修改时间
	protected String createId;// 建立者id
	protected Long createTime;// 建立时间
	protected String description;// 备注

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.put("updateId", updateId);
		this.updateId = updateId;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.put("updateTime", updateTime);
		this.updateTime = updateTime;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.put("createId", createId);
		this.createId = createId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.put("createTime", createTime);
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.put("description", description);
		this.description = description;
	}

	/**
	 * 查询转换成业务对象方法
	 * 
	 * @param obj
	 *            DBObject
	 * @return 业务对象
	 */
	public abstract T convert(DBObject obj);

}
