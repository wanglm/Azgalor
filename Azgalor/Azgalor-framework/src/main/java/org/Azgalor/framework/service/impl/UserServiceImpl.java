package org.Azgalor.framework.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.Azgalor.framework.dao.UserDao;
import org.Azgalor.framework.dao.UserDaoImpl;
import org.Azgalor.framework.entities.Messages;
import org.Azgalor.framework.entities.Users;
import org.Azgalor.framework.service.UserService;
import org.Azgalor.mongodb.MongoFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mongodb.DBObject;

public class UserServiceImpl implements UserService {
	private Logger log = LogManager.getLogger(UserServiceImpl.class);
	@Autowired
	@Qualifier("mongoFactory")
	private MongoFactory mf;

	@Override
	public Messages saveUser(Users user) {
		Messages msg = new Messages();
		try {
			UserDao dao = mf.get(UserDaoImpl.class);
			if (user.getObjectId("_id") == null) {
				msg.setSuccess(dao.insertUser(user));
				msg.setMessage("添加用户成功!");
			} else {
				msg.setSuccess(dao.updateUser(user));
				msg.setMessage("更新用户成功!");
			}
		} catch (Exception e) {
			log.error("saveUser(Users user)的错误", e);
		}
		return msg;
	}

	@Override
	public Messages deleteUser(Users user) {
		Messages msg = new Messages();
		try {
		} catch (Exception e) {
			log.error("deleteUser(Users user)的错误", e);
		}
		return msg;
	}

	@Override
	public List<Users> listUsers(DBObject obj) {
		List<Users> list = null;
		try {
			UserDao dao = mf.get(UserDaoImpl.class);
			List<DBObject> _list = dao.listUser(obj);
			list = new ArrayList<Users>(_list.size());
			for (DBObject o : _list) {
				Users user = new Users();
				user = user.convert(o);
				list.add(user);
			}
		} catch (Exception e) {
			log.error("listUsers(DBObject obj)的错误", e);
		}
		return list;
	}

	@Override
	public Users findOneUsers(DBObject obj) {
		Users user = new Users();
		try {
			UserDao dao = mf.get(UserDaoImpl.class);
			user = user.convert(dao.getUserById(obj.get("_id")));
		} catch (Exception e) {
			log.error("findOneUsers(DBObject obj)的错误", e);
		}
		return user;
	}

	@Override
	public long countUsers(DBObject obj) {
		long count = 0;
		try {
			UserDao dao = mf.get(UserDaoImpl.class);
			count = dao.countUsers(obj);
		} catch (Exception e) {
			log.error("countUsers(DBObject obj)的错误", e);
		}
		return count;
	}

}
