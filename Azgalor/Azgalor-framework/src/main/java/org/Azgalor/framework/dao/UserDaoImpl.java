package org.Azgalor.framework.dao;

import java.util.List;

import org.Azgalor.framework.entities.Users;
import org.Azgalor.mongodb.annotations.MongoCollections;
import org.Azgalor.mongodb.annotations.MongoDBName;
import org.Azgalor.mongodb.annotations.Mongoz;
import org.Azgalor.mongodb.dao.MongoDao;
import org.Azgalor.mongodb.enums.MongoType;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
@Mongoz(MongoType.SIMPLE)
@MongoDBName("azgalor")
@MongoCollections("users")
public class UserDaoImpl extends MongoDao<Users> implements UserDao {

	@Override
	public void insertUser(Users user) {
		this.insert(user);
	}

	@Override
	public boolean updateUser(Users user) {
		return this.updateById(user);
	}

	@Override
	public boolean deleteUser(Users user) {
		return this.deleteById(user);
	}

	@Override
	public List<Document> listUser(Document obj) {
		return this.find(obj);
	}

	@Override
	public Document getUserById(Object id) {
		return this.getById(id);
	}

	@Override
	public long countUsers(Document obj) {
		return this.count(obj);
	}

}
