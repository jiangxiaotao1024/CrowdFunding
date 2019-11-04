package cf.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cf.bean.Member;
import cf.bean.User;
import cf.util.Const;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 1.定义哪些路径是不需要拦截(将这些路径称为白名单)
		Set<String> uri = new HashSet<String>();
		uri.add("/reg.htm");
		uri.add("/doReg.do");
		uri.add("/login.htm");
		uri.add("/dologin.do");
		uri.add("/loginout.htm");
		uri.add("/index.htm");

		// 获取请求路径.
		String servletPath = request.getServletPath();

		if (uri.contains(servletPath)) {
			return true;
		}

		// 2.判断用户是否登录,如果登录就放行
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Const.USER_LOGIN);
		Member member = (Member) session.getAttribute(Const.MEMBER_LOGIN);

		if (user != null || member != null) {
			return true;
		} else {
			response.sendRedirect(request.getContextPath() + "/login.htm");
			return false;
		}

	}

}
