package org.Azgalor.framework.dao;

import java.util.List;

import org.Azgalor.framework.entities.Users;

import com.mongodb.DBObject;

public interface UserDao {
	public boolean insertUser(Users user);

	public boolean updateUser(Users user);

	public boolean deleteUser(Users user);

	public List<DBObject> listUser(DBObject obj);

	public DBObject getUserById(Object id);
	
	public long countUsers(DBObject obj);
}
