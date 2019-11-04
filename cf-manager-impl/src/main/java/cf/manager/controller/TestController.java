package cf.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cf.manager.service.TestService;

@Controller
public class TestController {
	@Autowired
	TestService testServcie;

	@RequestMapping("/addUserTest")
	public void addUserTest() {
		testServcie.addUser();
	}
}
