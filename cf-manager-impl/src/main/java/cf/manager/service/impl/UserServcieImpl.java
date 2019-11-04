package cf.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cf.bean.Role;
import cf.bean.User;
import cf.bean.UserExample;
import cf.manager.dao.UserMapper;
import cf.manager.service.UserService;
import cf.util.Data;
import cf.util.Page;

@Service
public class UserServcieImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	public void save(User user) {
		// TODO Auto-generated method stub
		userMapper.insertSelective(user);
	}

	public User queryUser(String loginacct) {
		// TODO Auto-generated method stub
		UserExample userExample = new UserExample();
		userExample.createCriteria().andLoginacctEqualTo(loginacct);
		List<User> userList = userMapper.selectByExample(userExample);
		return userList.get(0);
	}

	public Page queryPage(int pageno, int pagesize) {
		// TODO Auto-generated method stub
		Page page = new Page(pageno, pagesize);
		int startIndex = page.getStartIndex();
		List<User> data = userMapper.queryList(startIndex, pagesize);
		page.setData(data);
		int totalsize = userMapper.queryCount();
		page.setTotalsize(totalsize);
		return page;
	}

	public void addUser(User user) {
		// TODO Auto-generated method stub
		userMapper.insertSelective(user);
	}

	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		userMapper.deleteByPrimaryKey(id);
	}

	public User queryUserById(int id) {
		// TODO Auto-generated method stub
		User user = userMapper.selectByPrimaryKey(id);
		return user;
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		UserExample userExample = new UserExample();
		userExample.createCriteria().andLoginacctEqualTo(user.getLoginacct());
		userMapper.updateByExampleSelective(user, userExample);
	}

	public void deleteBatch(Data data) {
		// TODO Auto-generated method stub
		List<User> userList = data.getUserList();
		userMapper.deleteBatch(userList);
	}

	public Page queryByWord(int pageno, int pagesize, String queryWord) {
		// TODO Auto-generated method stub
		Page page = new Page(pageno, pagesize);
		int startIndex = page.getStartIndex();
		List<User> data = userMapper.queryByWord(startIndex, pagesize, queryWord);
		page.setData(data);
		int cout = userMapper.queryByWordCount(queryWord);
		page.setTotalsize(cout);
		return page;
	}

	public List<Role> selectRoleById(int id) {
		// TODO Auto-generated method stub
		List<Role> roleList = userMapper.selectRoleById(id);
		return roleList;
	}

	public void addRole(List<Integer> ids, int userid) {
		// TODO Auto-generated method stub
		userMapper.addRole(ids, userid);
	}

	public void deleteRole(List<Integer> ids, int userid) {
		// TODO Auto-generated method stub
		userMapper.deleteRole(ids, userid);
	}

}
