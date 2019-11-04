package cf.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cf.bean.Member;
import cf.bean.Permission;
import cf.bean.User;
import cf.manager.service.PermissionService;
import cf.manager.service.UserService;
import cf.potal.service.MemberService;
import cf.util.AjaxResult;
import cf.util.Const;
import cf.util.MD5Util;

@Controller
public class DispatcherController {
	@Autowired
	UserService userService;
	@Autowired
	MemberService memberService;
	@Autowired
	PermissionService permissionService;

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping("/reg")
	public String reg() {
		return "reg";
	}

	@RequestMapping("/doReg")
	@ResponseBody
	public Object doReg(String loginacct, String userpswd, String email, String usertype) {
		AjaxResult result = new AjaxResult();
		try {
			Member member = new Member();
			member.setLoginacct(loginacct);
			member.setUsername(loginacct);
			member.setUserpswd(MD5Util.digest(userpswd));
			member.setEmail(email);
			member.setUsertype(usertype);
			member.setAuthstatus("0");
			memberService.save(member);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("注册失败");
		}
		return result;
	}

	@RequestMapping("login")
	public String login() {
		return "login";
	}

	@RequestMapping("dologin")
	@ResponseBody
	public Object dologin(String loginacct, String userpswd, String type, HttpSession session) {
		AjaxResult result = new AjaxResult();
		String pswd = MD5Util.digest(userpswd);
		if (type.equals("user")) {
			User user = userService.queryUser(loginacct);
			if (user == null) {
				result.setMessage("用户不存在");
			} else {
				if (loginacct.equals(user.getLoginacct()) && pswd.equals(user.getUserpswd())) {
					session.setAttribute(Const.USER_LOGIN, user);
					result.setSuccess(true);
					// 权限
					Set<String> myUris = new HashSet<String>();
					List<Permission> permissions = permissionService.selectPermissionByUserid(user.getId());
					for (Permission permission : permissions) {
						myUris.add("/" + permission.getUrl());
					}
					session.setAttribute(Const.MY_URIS, myUris);
				} else {
					result.setSuccess(false);
					result.setMessage("账号或密码错误");
				}

			}
		} else {
			Member member = memberService.queryMember(loginacct);
			if (member == null) {
				result.setMessage("管理员不存在");
			} else {
				if (loginacct.equals(member.getLoginacct()) && pswd.equals(member.getUserpswd())) {
					session.setAttribute(Const.MEMBER_LOGIN, member);
					result.setSuccess(true);
					result.setLoginType("member");

				} else {
					result.setMessage("账号或密码错误");
				}
			}
		}
		return result;
	}

	@RequestMapping("loginout")
	public String loginout(HttpSession session) {
		session.invalidate();
		return "redirect:index.jsp";
	}

	@RequestMapping("main")
	public String main() {
		return "main";
	}
}
