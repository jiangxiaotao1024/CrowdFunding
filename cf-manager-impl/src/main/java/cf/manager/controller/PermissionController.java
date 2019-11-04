package cf.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cf.bean.Permission;
import cf.manager.service.PermissionService;
import cf.util.AjaxResult;

@Controller
@RequestMapping("permission")
public class PermissionController {
	@Autowired
	PermissionService permissionServer;

	@RequestMapping("index")
	public String index() {
		return "permission/index";
	}

	@RequestMapping("loadData")
	@ResponseBody
	public Object loadData() {
		AjaxResult result = new AjaxResult();
		List<Permission> root = new ArrayList<Permission>();
		try {
			List<Permission> permissions = permissionServer.selectAll();
			Map<Integer, Permission> map = new HashMap<Integer, Permission>();
			for (Permission permission : permissions) {
				map.put(permission.getId(), permission);
			}
			for (Permission permission : permissions) {
				if (permission.getPid() == null) {
					root.add(permission);
				} else {
					Permission parent = map.get(permission.getPid());
					parent.getChildren().add(permission);
				}
			}
			result.setSuccess(true);
			result.setData(root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("查询数据出错");
		}
		return result;
	}

	@RequestMapping("add")
	public String add(int id) {
		return "permission/add";
	}

	@RequestMapping("doAdd")
	@ResponseBody
	public Object doAdd(int pid, String name) {
		AjaxResult result = new AjaxResult();
		try {
			permissionServer.addPermission(pid, name);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("添加权限失败");
		}
		return result;
	}

	@RequestMapping("update")
	public String update(int id, Map map) {
		Permission permission = permissionServer.selectPermission(id);
		map.put("permission", permission);
		return "permission/update";
	}

	@RequestMapping("doUpdate")
	@ResponseBody
	public Object doUpdate(int id, String name) {
		AjaxResult result = new AjaxResult();
		try {
			permissionServer.updatePermission(id, name);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("修改权限失败");
		}
		return result;
	}

	@RequestMapping("delete")
	@ResponseBody
	public Object delete(int id) {
		AjaxResult result = new AjaxResult();
		try {
			permissionServer.deletePermission(id);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("删除权限失败");
		}
		return result;
	}
}
