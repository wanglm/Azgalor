package org.Azgalor.mongodb;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * mongodb存储对象基类
 * 
 * @author ming
 *
 */
public abstract class MongoEntity<T> extends Document {
	private static final long serialVersionUID = 5293439786916585037L;
	protected String id;// 保存_id
	protected String updateId;// 修改者id
	protected Long updateTime;// 修改时间
	protected String createId;// 建立者id
	protected Long createTime;// 建立时间
	protected String description;// 备注

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.put("_id", new ObjectId(id));
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
	 * 默认属性设置
	 * 
	 * @param doc
	 */
	public void doSetDefault(Document doc) {
		ObjectId id = doc.getObjectId("_id");
		if (id != null) {
			this.setId(id.toString());
		}
		String cId = (String) doc.get("createId");
		if (StringUtils.isNotBlank(cId)) {
			this.setCreateId(cId);
		}
		Long ct = (Long) doc.get("createTime");
		if (ct != null) {
			this.setCreateTime(ct);
		}
		String dsc = (String) doc.get("description");
		if (StringUtils.isNotBlank(dsc)) {
			this.setDescription(dsc);
		}
		String uId = (String) doc.get("updateId");
		if (StringUtils.isNotBlank(uId)) {
			this.setUpdateId(uId);
		}
		Long ut = (Long) doc.get("updateTime");
		if (ut != null) {
			this.setUpdateTime(ut);
		}
	}

	/**
	 * 蛋疼的mongodb 3，有bug，只能保存Document，子类是会报错的
	 * 
	 * @return
	 */
	public Document toDocument() {
		Document doc = Document.parse(this.toJson());
		return doc;
	}

	/**
	 * 查询转换成业务对象方法
	 * 
	 * @param obj
	 *            Document
	 * @return 业务对象
	 */
	public abstract T convert(Document doc);

}
