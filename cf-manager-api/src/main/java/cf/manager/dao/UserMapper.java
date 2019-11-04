package cf.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cf.bean.Role;
import cf.bean.User;
import cf.bean.UserExample;

public interface UserMapper {
	long countByExample(UserExample example);

	int deleteByExample(UserExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	List<User> selectByExample(UserExample example);

	User selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

	int updateByExample(@Param("record") User record, @Param("example") UserExample example);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	List<User> queryList(@Param("index") int startIndex, @Param("pagesize") int pagesize);

	int queryCount();

	void deleteBatch(@Param("userList") List<User> userList);

	List<User> queryByWord(@Param("startIndex") int startIndex, @Param("pagesize") int pagesize,
			@Param("queryWord") String queryWord);

	int queryByWordCount(String queryWord);

	List<Role> selectRoleById(int id);

	void addRole(@Param("ids") List<Integer> ids, @Param("userid") int userid);

	void deleteRole(@Param("ids") List<Integer> ids, @Param("userid") int userid);

}