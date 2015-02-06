package org.Azgalor.framework.dao;

import java.util.List;

import org.Azgalor.framework.entities.Users;
import org.Azgalor.mongodb.annotations.MongoCollection;
import org.Azgalor.mongodb.annotations.MongoDBName;
import org.Azgalor.mongodb.annotations.Mongoz;
import org.Azgalor.mongodb.dao.MongoDao;
import org.Azgalor.mongodb.enums.MongoType;
import org.springframework.stereotype.Repository;

import com.mongodb.DBObject;

@Repository
@Mongoz(MongoType.SIMPLE)
@MongoDBName("azgalor")
@MongoCollection("users")
public class UserDaoImpl extends MongoDao<Users> implements UserDao {

	@Override
	public boolean insertUser(Users user) {
		return this.insert(user);
	}

	@Override
	public boolean updateUser(Users user) {
		return this.update(user);
	}

	@Override
	public boolean deleteUser(Users user) {
		return this.delete(user);
	}

	@Override
	public List<DBObject> listUser(DBObject obj) {
		return this.find(dbName, collection, obj);
	}

	@Override
	public DBObject getUserById(Object id) {
		return this.getById(dbName, collection, id);
	}

	@Override
	public long countUsers(DBObject obj) {
		return this.count(dbName, collection, obj);
	}

}
