package cf.manager.service;

import java.util.List;

import cf.bean.Role;
import cf.bean.User;
import cf.util.Data;
import cf.util.Page;

public interface UserService {

	void save(User user);

	User queryUser(String loginacct);

	Page queryPage(int pageno, int pagesize);

	void addUser(User user);

	void deleteUser(int id);

	User queryUserById(int id);

	void updateUser(User user);

	void deleteBatch(Data data);

	Page queryByWord(int pageno, int pagesize, String queryWord);

	List<Role> selectRoleById(int id);

	void addRole(List<Integer> ids, int userid);

	void deleteRole(List<Integer> ids, int userid);

}
