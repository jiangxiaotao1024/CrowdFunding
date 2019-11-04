package cf.manager.service;

import java.util.List;

import cf.bean.Permission;

public interface PermissionService {

	List<Permission> selectAll();

	void addPermission(int pid, String name);

	Permission selectPermission(int id);

	void updatePermission(int id, String name);

	void deletePermission(int id);

	List<Permission> selectPermissionByUserid(Integer id);

}
