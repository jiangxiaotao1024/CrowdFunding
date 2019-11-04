package cf.manager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cf.bean.Permission;
import cf.bean.Role;
import cf.manager.service.PermissionService;
import cf.manager.service.RoleService;
import cf.util.AjaxResult;
import cf.util.Data;
import cf.util.Page;
import cf.util.StringUtil;

@Controller
@RequestMapping("role")
public class RoleController {
	@Autowired
	RoleService roleService;
	@Autowired
	PermissionService permissionService;

	@RequestMapping("index")
	public String index() {
		return "role/index";
	}

	@RequestMapping("showrole")
	@ResponseBody
	public Object showrole(@RequestParam(value = "pageno", required = false, defaultValue = "1") int pageno,
			@RequestParam(value = "pagesize", required = false, defaultValue = "10") int pagesize, String word) {
		AjaxResult result = new AjaxResult();
		Page page = new Page(pageno, pagesize);
		int startIndex = page.getStartIndex();
		List<Role> data;
		int totalsize;
		try {
			if (StringUtil.isEmpty(word)) {
				data = roleService.queryRole(startIndex, pagesize);
				totalsize = roleService.queryCount();
			} else {
				data = roleService.queryRoleByWord(startIndex, pagesize, word);
				totalsize = roleService.queryCountByWord(word);
			}
			page.setData(data);
			page.setTotalsize(totalsize);
			result.setSuccess(true);
			result.setPage(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("查询数据失败");
		}
		return result;
	}

	@RequestMapping("add")
	public String add() {
		return "role/add";
	}

	@RequestMapping("doAdd")
	@ResponseBody
	public Object doAdd(String name) {
		AjaxResult result = new AjaxResult();
		try {
			roleService.addRole(name);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("添加角色失败");
		}
		return result;
	}

	@RequestMapping("update")
	public String update(int id, Map map) {
		Role role = roleService.selectById(id);
		map.put("role", role);
		return "role/update";
	}

	@RequestMapping("doUpdate")
	@ResponseBody
	public Object doUpdate(int id, String name) {
		AjaxResult result = new AjaxResult();
		try {
			roleService.updateRole(id, name);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("更新数据失败");
		}
		return result;
	}

	@RequestMapping("delete")
	@ResponseBody
	public Object delete(int id) {
		AjaxResult result = new AjaxResult();
		try {
			roleService.deleteRole(id);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("删除数据失败");
		}
		return result;
	}

	@RequestMapping("deleteByBatch")
	@ResponseBody
	public Object deleteByBatch(Data data) {
		AjaxResult result = new AjaxResult();
		List<Integer> ids = data.getIds();
		try {
			roleService.deleteByBatch(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("删除数据失败");
		}
		return result;
	}

	@RequestMapping("assignPermission")
	public String assignPermission(int id, Map map) {
		List<Permission> permissions = permissionService.selectAll();
		List<Permission> rightlist = roleService.selectPermissionById(id);
		List<Permission> leftlist = new ArrayList<Permission>();
		for (Permission permission : permissions) {
			if (!rightlist.contains(permission)) {
				leftlist.add(permission);
			}
		}
		map.put("rightlist", rightlist);
		map.put("leftlist", leftlist);
		return "role/assignPermission";
	}

	@RequestMapping("addAssign")
	@ResponseBody
	public Object addAssign(Data data, int roleid) {
		AjaxResult result = new AjaxResult();
		List<Integer> ids = data.getIds();
		try {
			roleService.addAssign(ids, roleid);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("分配权限失败");
		}
		return result;
	}

	@RequestMapping("deleteAssign")
	@ResponseBody
	public Object deleteAssign(Data data, int roleid) {
		AjaxResult result = new AjaxResult();
		List<Integer> ids = data.getIds();
		try {
			roleService.deleteAssign(ids, roleid);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("删除权限失败");
		}
		return result;
	}
}
