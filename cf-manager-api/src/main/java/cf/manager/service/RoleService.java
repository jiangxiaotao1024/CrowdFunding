package cf.manager.service;

import java.util.List;

import cf.bean.Permission;
import cf.bean.Role;

public interface RoleService {

	List<Role> queryRole(int startIndex, int pagesize);

	int queryCount();

	void addRole(String name);

	List<Role> selectAll();

	Role selectById(int id);

	void updateRole(int id, String name);

	void deleteRole(int id);

	void deleteByBatch(List<Integer> ids);

	List<Role> queryRoleByWord(int startIndex, int pagesize, String word);

	int queryCountByWord(String word);

	List<Permission> selectPermissionById(int id);

	void addAssign(List<Integer> ids, int roleid);

	void deleteAssign(List<Integer> ids, int roleid);

}
