package org.Azgalor.framework.dao;

import java.util.List;

import org.Azgalor.framework.entities.Users;
import org.bson.Document;

public interface UserDao {
	public void insertUser(Users user);

	public boolean updateUser(Users user);

	public boolean deleteUser(Users user);

	public List<Document> listUser(Document doc);

	public Document getUserById(Object id);

	public long countUsers(Document doc);
}
