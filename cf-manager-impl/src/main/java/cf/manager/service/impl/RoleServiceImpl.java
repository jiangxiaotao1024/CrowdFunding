package cf.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cf.bean.Permission;
import cf.bean.Role;
import cf.bean.RoleExample;
import cf.manager.dao.RoleMapper;
import cf.manager.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleMapper roleMapper;

	public List<Role> queryRole(int startIndex, int pagesize) {
		// TODO Auto-generated method stub
		List<Role> data = roleMapper.queryRole(startIndex, pagesize);
		return data;
	}

	public int queryCount() {
		// TODO Auto-generated method stub
		int count = roleMapper.queryCount();
		return count;
	}

	public void addRole(String name) {
		// TODO Auto-generated method stub
		Role role = new Role();
		role.setName(name);
		roleMapper.insertSelective(role);
	}

	public List<Role> selectAll() {
		// TODO Auto-generated method stub
		List<Role> roles = roleMapper.selectAll();
		return roles;
	}

	public Role selectById(int id) {
		// TODO Auto-generated method stub
		Role role = roleMapper.selectByPrimaryKey(id);
		return role;
	}

	public void updateRole(int id, String name) {
		// TODO Auto-generated method stub
		Role role = new Role();
		role.setId(id);
		role.setName(name);
		RoleExample roleExample = new RoleExample();
		roleExample.createCriteria().andIdEqualTo(id);
		roleMapper.updateByExample(role, roleExample);
	}

	public void deleteRole(int id) {
		// TODO Auto-generated method stub
		roleMapper.deleteByPrimaryKey(id);
	}

	public void deleteByBatch(List<Integer> ids) {
		// TODO Auto-generated method stub
		roleMapper.deleteByBatch(ids);
	}

	public List<Role> queryRoleByWord(int startIndex, int pagesize, String word) {
		// TODO Auto-generated method stub
		List<Role> roleList = roleMapper.queryRoleByWord(startIndex, pagesize, word);
		return roleList;
	}

	public int queryCountByWord(String word) {
		// TODO Auto-generated method stub
		int count = roleMapper.queryCountByWord(word);
		return count;
	}

	public List<Permission> selectPermissionById(int id) {
		// TODO Auto-generated method stub
		List<Permission> permissions = roleMapper.selectPermissionById(id);
		return permissions;
	}

	public void addAssign(List<Integer> ids, int roleid) {
		// TODO Auto-generated method stub
		roleMapper.addAssign(ids, roleid);
	}

	public void deleteAssign(List<Integer> ids, int roleid) {
		// TODO Auto-generated method stub
		roleMapper.deleteAssign(ids, roleid);
	}

}
