package org.Azgalor.framework.service;

import java.util.List;

import org.Azgalor.framework.entities.Messages;
import org.Azgalor.framework.entities.Users;
import org.bson.Document;



public interface UserService {
	/**
	 * 保存用户
	 * 
	 * @param user
	 * @return Messages
	 */
	public Messages saveUser(Users user);

	/**
	 * 删除用户
	 * 
	 * @param user
	 * @return Messages
	 */
	public Messages deleteUser(Users user);

	/**
	 * 查询用户集合
	 * 
	 * @param obj
	 * @return List
	 */
	public List<Users> listUsers(Document obj);

	/**
	 * 查询单个用户
	 * 
	 * @param obj
	 * @return Users
	 */
	public Users findOneUsers(Document obj);
	
	
	/**用户总数
	 * @param obj
	 * @return
	 */
	public long countUsers(Document obj);

}
