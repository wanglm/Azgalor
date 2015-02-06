package org.Azgalor.framework.controller;

import java.util.List;

import org.Azgalor.framework.entities.Messages;
import org.Azgalor.framework.entities.Users;
import org.Azgalor.framework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Controller
@RequestMapping("User")
public class UserController {
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@RequestMapping(params = "method=save")
	@ResponseBody
	public Messages save(Users user) {
		Messages msg = userService.saveUser(user);
		return msg;
	}

	@RequestMapping(params = "method=delete")
	@ResponseBody
	public Messages delete(Users user) {
		Messages msg = userService.deleteUser(user);
		return msg;
	}

	@RequestMapping(params = "method=find")
	@ResponseBody
	public List<Users> find() {
		DBObject obj = new BasicDBObject();
		List<Users> list = userService.listUsers(obj);
		return list;
	}

	@RequestMapping(params = "method=list")
	public String list(Model model) {
		DBObject obj = new BasicDBObject();
		List<Users> list = userService.listUsers(obj);
		model.addAttribute("list", list);
		model.addAttribute("totals", userService.countUsers(obj));
		return "users/UserShow";
	}

}
