package cf.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cf.bean.Member;
import cf.manager.service.CertService;
import cf.potal.service.MemberService;
import cf.potal.service.TicketService;
import cf.util.AjaxResult;
import cf.util.Page;

@Controller
@RequestMapping("authcert")
public class AuthcertController {
	@Autowired
	TaskService taskService;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	TicketService ticketService;
	@Autowired
	MemberService memberService;
	@Autowired
	CertService certService;

	@RequestMapping("index")
	public String index() {
		return "authcert/index";
	}

	@RequestMapping("showPage")
	@ResponseBody
	public Object showPage(@RequestParam(value = "pageno", defaultValue = "1") int pageno,
			@RequestParam(value = "pagesize", defaultValue = "10") int pagesize) {
		AjaxResult result = new AjaxResult();
		try {
			Page page = new Page(pageno, pagesize);
			TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey("auth")
					.taskCandidateGroup("backuser");
			List<Task> tasks = taskQuery.listPage(page.getStartIndex(), pagesize);
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			for (Task task : tasks) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("taskId", task.getId());
				map.put("taskName", task.getName());
				ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
						.processDefinitionId(task.getProcessDefinitionId()).singleResult();
				map.put("procDefName", processDefinition.getName());
				map.put("procDefVersion", processDefinition.getVersion());
				Member member = ticketService.queryMemberByPiid(task.getProcessInstanceId());
				map.put("member", member);
				data.add(map);
			}
			page.setData(data);
			long count = taskQuery.count();
			page.setTotalsize((int) count);
			result.setPage(page);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage("查询数据失败");
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("verify")
	public String verify(int memberid, int taskid, Map map) {
		Member member = memberService.getMemberById(memberid);
		List<Map<String, Object>> certs = certService.getCertsByMember(memberid);
		map.put("member", member);
		map.put("certs", certs);
		return "authcert/verify";
	}

	@RequestMapping("pass")
	@ResponseBody
	public Object pass(String taskid, int memberid) {
		AjaxResult result = new AjaxResult();
		try {
			taskService.setVariable(taskid, "flag", true);
			taskService.setVariable(taskid, "memberid", memberid);
			taskService.complete(taskid);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage("审核失败");
			e.printStackTrace();
		}
		return result;
	}
}
