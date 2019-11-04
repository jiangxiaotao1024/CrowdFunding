package cf.manager.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cf.bean.Role;
import cf.bean.User;
import cf.manager.service.RoleService;
import cf.manager.service.UserService;
import cf.util.AjaxResult;
import cf.util.Data;
import cf.util.MD5Util;
import cf.util.Page;
import cf.util.StringUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@RequestMapping("index")
	public String user() {
		return "user/index";
	}

	@RequestMapping("showUser")
	@ResponseBody
	public Object showUser(@RequestParam(value = "pageno", required = false, defaultValue = "1") int pageno,
			@RequestParam(value = "pagesize", required = false, defaultValue = "10") int pagesize, String queryWord) {
		AjaxResult result = new AjaxResult();
		Page page;
		try {
			if (StringUtil.isEmpty(queryWord)) {
				page = userService.queryPage(pageno, pagesize);
			} else {
				page = userService.queryByWord(pageno, pagesize, queryWord);
			}
			result.setPage(page);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("查询数据失败");
		}
		return result;
	}

	@RequestMapping("add")
	public String add() {
		return "user/add";
	}

	@RequestMapping("doAdd")
	@ResponseBody
	public Object doAdd(String loginacct, String username, String email) {
		AjaxResult result = new AjaxResult();
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setLoginacct(loginacct);
		user.setUserpswd(MD5Util.digest("123"));
		user.setCreatetime(simpleDateFormat.format(date));
		try {
			userService.addUser(user);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("添加出错");
		}
		return result;
	}

	@RequestMapping("delete")
	@ResponseBody
	public Object delete(int id) {
		AjaxResult result = new AjaxResult();
		try {
			userService.deleteUser(id);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("删除失败");
		}
		return result;
	}

	@RequestMapping("edit")
	public String edit(int id, Map map) {
		User user = userService.queryUserById(id);
		map.put("user", user);
		return "user/edit";
	}

	@RequestMapping("doEdit")
	@ResponseBody
	public Object doEdit(String loginacct, String username, String email) {
		AjaxResult result = new AjaxResult();
		User user = new User();
		user.setEmail(email);
		user.setLoginacct(loginacct);
		user.setUsername(username);
		try {
			userService.updateUser(user);
			result.setSuccess(true);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("更新出错");
		}
		return result;
	}

	@RequestMapping("deleteBatch")
	@ResponseBody
	public Object deleteBatch(Data data) {
		AjaxResult result = new AjaxResult();
		try {
			userService.deleteBatch(data);
			result.setSuccess(true);
			result.setMessage("删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("删除失败");
		}
		return result;
	}

	@RequestMapping("assignRole")
	public String assignRole(int id, Map map) {
		List<Role> roles = roleService.selectAll();
		List<Role> rightlist = userService.selectRoleById(id);
		List<Role> leftList = new ArrayList<Role>();
		for (Role role : roles) {
			if (!rightlist.contains(role)) {
				leftList.add(role);
			}
		}
		map.put("leftlist", leftList);
		map.put("rightlist", rightlist);
		return "user/assignRole";
	}

	@RequestMapping("addAssign")
	@ResponseBody
	public Object addAssign(Data data, int userid) {
		AjaxResult result = new AjaxResult();
		List<Integer> ids = data.getIds();
		try {
			userService.addRole(ids, userid);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("分配角色失败");
		}
		return result;
	}

	@RequestMapping("deleteAssign")
	@ResponseBody
	public Object deleteAssign(Data data, int userid) {
		AjaxResult result = new AjaxResult();
		List<Integer> ids = data.getIds();
		try {
			userService.deleteRole(ids, userid);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("分配角色失败");
		}
		return result;
	}
}
