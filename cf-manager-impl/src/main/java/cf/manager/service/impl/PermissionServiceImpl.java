package cf.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cf.bean.Permission;
import cf.bean.PermissionExample;
import cf.manager.dao.PermissionMapper;
import cf.manager.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	PermissionMapper permissionMapper;

	public List<Permission> selectAll() {
		// TODO Auto-generated method stub
		List<Permission> permissions = permissionMapper.selectAll();
		return permissions;
	}

	public void addPermission(int pid, String name) {
		// TODO Auto-generated method stub
		Permission permission = new Permission();
		permission.setPid(pid);
		permission.setName(name);
		permissionMapper.insertSelective(permission);
	}

	public Permission selectPermission(int id) {
		// TODO Auto-generated method stub
		Permission permission = permissionMapper.selectByPrimaryKey(id);
		return permission;
	}

	public void updatePermission(int id, String name) {
		// TODO Auto-generated method stub
		Permission permission = new Permission();
		permission.setId(id);
		permission.setName(name);
		PermissionExample permissionExample = new PermissionExample();
		permissionExample.createCriteria().andIdEqualTo(id);
		permissionMapper.updateByExample(permission, permissionExample);
	}

	public void deletePermission(int id) {
		// TODO Auto-generated method stub
		permissionMapper.deleteByPrimaryKey(id);
	}

	public List<Permission> selectPermissionByUserid(Integer id) {
		// TODO Auto-generated method stub
		List<Permission> permissions = permissionMapper.selectPermissionByUserid(id);
		return permissions;
	}

}
