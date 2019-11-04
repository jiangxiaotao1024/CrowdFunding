package cf.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cf.bean.Permission;
import cf.bean.Role;
import cf.bean.RoleExample;

public interface RoleMapper {
	long countByExample(RoleExample example);

	int deleteByExample(RoleExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(Role record);

	int insertSelective(Role record);

	List<Role> selectByExample(RoleExample example);

	Role selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

	int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

	int updateByPrimaryKeySelective(Role record);

	int updateByPrimaryKey(Role record);

	List<Role> queryRole(@Param("startIndex") int startIndex, @Param("pagesize") int pagesize);

	int queryCount();

	List<Role> selectAll();

	void deleteByBatch(@Param("ids") List<Integer> ids);

	List<Role> queryRoleByWord(@Param("startIndex") int startIndex, @Param("pagesize") int pagesize,
			@Param("word") String word);

	int queryCountByWord(String word);

	List<Permission> selectPermissionById(int id);

	void addAssign(@Param("ids") List<Integer> ids, @Param("roleid") int roleid);

	void deleteAssign(@Param("ids") List<Integer> ids, @Param("roleid") int roleid);
}