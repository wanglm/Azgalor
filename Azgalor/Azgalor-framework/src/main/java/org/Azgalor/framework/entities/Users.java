package org.Azgalor.framework.entities;

import org.Azgalor.framework.util.UsersStatic;
import org.Azgalor.mongodb.MongoEntity;
import org.bson.types.ObjectId;

import com.mongodb.DBObject;

public class Users extends MongoEntity<Users> {
	private static final long serialVersionUID = -4611124040065321210L;
	private String name;// 名字
	private String passWord;// 密码
	private String idCard;// 身份证id
	private String birthday;// 生日
	private String email;// 邮箱
	/**
	 * @see UsersStatic
	 */
	private int type;// 权限类型,0超级管理员，1普通管理员，2会员，3普通用户

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.put("name", name);
		this.name = name;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.put("passWord", passWord);
		this.passWord = passWord;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.put("idCard", idCard);
		this.idCard = idCard;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.put("birthday", birthday);
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.put("email", email);
		this.email = email;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.put("type", type);
		this.type = type;
	}

	@Override
	public Users convert(DBObject obj) {
		this.setId((ObjectId) obj.get("_id"));
		this.setName(obj.get("name").toString());
		this.setPassWord(obj.get("passWord").toString());
		this.setIdCard(obj.get("idCard").toString());
		this.setBirthday(obj.get("birthday").toString());
		this.setEmail(obj.get("email").toString());
		return this;
	}


}
