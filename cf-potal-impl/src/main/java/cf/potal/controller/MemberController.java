package cf.potal.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cf.bean.Cert;
import cf.bean.Member;
import cf.bean.MemberCert;
import cf.bean.Ticket;
import cf.potal.listener.PassListener;
import cf.potal.listener.RefuseListener;
import cf.potal.service.MemberService;
import cf.potal.service.TicketService;
import cf.util.AjaxResult;
import cf.util.Const;
import cf.util.Data;

@Controller
@RequestMapping("member")
public class MemberController {
	@Autowired
	MemberService memberService;
	@Autowired
	TicketService ticketService;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;

	@RequestMapping("index")
	public String index() {
		return "member/index";
	}

	@RequestMapping("apply")
	public String acctype(HttpSession session) {
		Member member = (Member) session.getAttribute(Const.MEMBER_LOGIN);
		Ticket ticket = ticketService.queryTicketByMemberId(member.getId());
		if (ticket == null) {
			Ticket ticket2 = new Ticket();
			ticket2.setMemberid(member.getId());
			ticket2.setPstep("accttype");
			ticket2.setStatus("0");
			ticketService.saveTicket(ticket2);
			return "member/accttype";
		} else {
			if ("accttype".equals(ticket.getPstep())) {
				return "member/accttype";
			} else if ("info".equals(ticket.getPstep())) {
				return "member/info";
			} else if ("cert".equals(ticket.getPstep())) {
				return "redirect:/member/uploadCert.htm";
			} else if ("email".equals(ticket.getPstep())) {
				return "member/email";
			} else if ("verify".equals(ticket.getPstep())) {
				return "member/verify";
			} else {
				return null;
			}
		}
	}

	@RequestMapping("info")
	public String info() {
		return "member/info";
	}

	@RequestMapping("submitAccttype")
	@ResponseBody
	public Object doAccttype(int id, String accttype) {
		AjaxResult result = new AjaxResult();
		try {
			Member member = new Member();
			member.setId(id);
			member.setAccttype(accttype);
			memberService.updateAccttype(member);
			result.setSuccess(true);

			Ticket ticket = ticketService.queryTicketByMemberId(id);
			ticket.setPstep("info");
			ticketService.updateTicket(ticket);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("类型选择失败");
		}
		return result;
	}

	@RequestMapping("submitMemberInfo")
	@ResponseBody
	public Object submitMemberInfo(String realname, String cardnum, int id) {
		AjaxResult result = new AjaxResult();
		try {
			Member member = new Member();
			member.setId(id);
			member.setRealname(realname);
			member.setCardnum(cardnum);
			memberService.updateAccttype(member);
			result.setSuccess(true);

			Ticket ticket = ticketService.queryTicketByMemberId(id);
			ticket.setPstep("cert");
			ticketService.updateTicket(ticket);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("信息提交失败");
		}
		return result;
	}

	@RequestMapping("uploadCert")
	public String uploadCert(HttpSession session, Map map) {

		Member member = (Member) session.getAttribute(Const.MEMBER_LOGIN);
		String accttype = member.getAccttype();
		List<Cert> certs = memberService.queryCert(accttype);
		map.put("certs", certs);

		return "member/uploadCert";
	}

	@RequestMapping("doUploadCert")
	@ResponseBody
	public Object doUploadCert(Data data, HttpSession session) {
		AjaxResult result = new AjaxResult();
		try {
			Member loginMember = (Member) session.getAttribute(Const.MEMBER_LOGIN);

			String realPath = session.getServletContext().getRealPath("/picture");

			List<MemberCert> memberCerts = data.getMemberCerts();
			for (MemberCert memberCert : memberCerts) {

				MultipartFile fileImg = memberCert.getFileImg();
				String extName = fileImg.getOriginalFilename()
						.substring(fileImg.getOriginalFilename().lastIndexOf("."));
				String tmpName = UUID.randomUUID().toString() + extName;
				String filename = realPath + "/cert" + "/" + tmpName;

				fileImg.transferTo(new File(filename)); // 资质文件上传.

				// 准备数据
				memberCert.setIconpath(tmpName); // 封装数据,保存数据库
				memberCert.setMemberid(loginMember.getId());
			}

			// 保存会员与资质关系数据.
			memberService.saveMemberCert(memberCerts);

			result.setSuccess(true);

			Ticket ticket = ticketService.queryTicketByMemberId(loginMember.getId());
			ticket.setPstep("email");
			ticketService.updateTicket(ticket);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("信息提交失败");
		}
		return result;
	}

	@RequestMapping("email")
	public String email() {

		return "member/email";
	}

	@ResponseBody
	@RequestMapping("startProcess")
	public Object startProcess(HttpSession session, String email) {
		AjaxResult result = new AjaxResult();
		try {
			Member member = (Member) session.getAttribute(Const.MEMBER_LOGIN);
			if (!member.getEmail().equals(email)) {
				member.setEmail(email);
				memberService.updateMember(member);
			}
			// 启动实名认证流程 - 系统自动发送邮件,生成验证码.验证邮箱地址是否合法(模拟:银行卡是否邮箱).
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
					.processDefinitionKey("auth").latestVersion().singleResult();
			StringBuilder authcode = new StringBuilder();
			for (int i = 0; i < 4; i++) {
				authcode.append(new Random().nextInt(10));
			}
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("toEmail", email);
			variables.put("authcode", authcode);
			variables.put("loginacct", member.getLoginacct());
			variables.put("passListener", new PassListener());
			variables.put("refuseListener", new RefuseListener());

			ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(),
					variables);

			Ticket ticket = ticketService.queryTicketByMemberId(member.getId());
			ticket.setPstep("verify");
			ticket.setAuthcode(authcode.toString());
			ticket.setPiid(processInstance.getId());
			ticketService.updateTicket(ticket);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage("失败");
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping("verify")
	public String auth() {
		return "member/verify";
	}

	@RequestMapping("doverify")
	@ResponseBody
	public Object doverify(String code, HttpSession session) {
		AjaxResult result = new AjaxResult();
		Member member = (Member) session.getAttribute(Const.MEMBER_LOGIN);
		Ticket ticket = ticketService.queryTicketByMemberId(member.getId());
		if (ticket.getAuthcode().equals(code)) {
			Task task = taskService.createTaskQuery().processInstanceId(ticket.getPiid())
					.taskAssignee(member.getLoginacct()).singleResult();
			taskService.complete(task.getId());

			member.setAuthstatus("1");
			memberService.updateMember(member);

			ticket.setPstep("finish");
			ticketService.updateTicket(ticket);
			result.setSuccess(true);
		} else {
			result.setMessage("验证码错误");
		}
		return result;
	}

}
