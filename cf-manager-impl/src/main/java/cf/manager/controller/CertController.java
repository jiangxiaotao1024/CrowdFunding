package cf.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cf.bean.AccountTypeCert;
import cf.bean.Cert;
import cf.manager.service.CertService;
import cf.util.AjaxResult;
import cf.util.Data;
import cf.util.Page;
import cf.util.StringUtil;

@Controller
@RequestMapping("cert")
public class CertController {
	@Autowired
	CertService certService;

	@RequestMapping("index")
	public String index() {
		return "cert/index";
	}

	@RequestMapping("showCert")
	@ResponseBody
	public Object showCert(@RequestParam(value = "pageno", required = false, defaultValue = "1") int pageno,
			@RequestParam(value = "pagesize", required = false, defaultValue = "10") int pagesize, String word) {
		AjaxResult result = new AjaxResult();
		Page page = new Page(pageno, pagesize);
		int startIndex = page.getStartIndex();
		List<Cert> data;
		int totalsize;
		try {
			if (StringUtil.isEmpty(word)) {
				data = certService.queryCert(startIndex, pagesize);
				totalsize = certService.queryCount();
			} else {
				data = certService.queryCertByWord(startIndex, pagesize, word);
				totalsize = certService.queryCountByWord(word);
			}
			page.setData(data);
			page.setTotalsize(totalsize);
			result.setSuccess(true);
			result.setPage(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("查询数据失败");
		}
		return result;
	}

	@RequestMapping("add")
	public String add() {
		return "cert/add";
	}

	@RequestMapping("doAdd")
	@ResponseBody
	public Object doAdd(String name) {
		AjaxResult result = new AjaxResult();
		try {
			certService.addCert(name);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("添加角色失败");
		}
		return result;
	}

	@RequestMapping("update")
	public String update(int id, Map map) {
		Cert cert = certService.selectById(id);
		map.put("cert", cert);
		return "cert/update";
	}

	@RequestMapping("doUpdate")
	@ResponseBody
	public Object doUpdate(int id, String name) {
		AjaxResult result = new AjaxResult();
		try {
			certService.updateCert(id, name);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("更新数据失败");
		}
		return result;
	}

	@RequestMapping("delete")
	@ResponseBody
	public Object delete(int id) {
		AjaxResult result = new AjaxResult();
		try {
			certService.deleteRole(id);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("删除数据失败");
		}
		return result;
	}

	@RequestMapping("deleteByBatch")
	@ResponseBody
	public Object deleteByBatch(Data data) {
		AjaxResult result = new AjaxResult();
		List<Integer> ids = data.getIds();
		try {
			certService.deleteByBatch(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("删除数据失败");
		}
		return result;
	}

	@RequestMapping("certtype")
	@ResponseBody
	public Object certtype() {
		AjaxResult result = new AjaxResult();
		try {
			List<Cert> certs = certService.selectAll();
			List<AccountTypeCert> accountTypeCerts = certService.selectAtc();
			result.setSuccess(true);
			result.setData(certs);
			result.setData1(accountTypeCerts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("查询数据失败");
		}
		return result;
	}

	@RequestMapping("updateCerttype")
	@ResponseBody
	public Object updateCerttype(Data data) {
		AjaxResult result = new AjaxResult();
		List<AccountTypeCert> accountTypeCerts = data.getAccountTypeCerts();
		try {
			certService.updateAtc(accountTypeCerts);
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage("更新数据失败");
		}
		return result;
	}
}
