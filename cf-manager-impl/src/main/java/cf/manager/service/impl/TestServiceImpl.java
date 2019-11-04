package cf.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cf.bean.User;
import cf.manager.dao.UserMapper;
import cf.manager.service.TestService;
import cf.util.MD5Util;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	UserMapper userMapper;

	public void addUser() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 103; i++) {
			User user = new User();
			user.setLoginacct("test" + i);
			user.setEmail("email" + i);
			user.setUsername("name" + i);
			user.setUserpswd(MD5Util.digest("123"));
			userMapper.insertSelective(user);
		}
	}

}
