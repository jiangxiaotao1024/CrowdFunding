package cf.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("certtype")
public class CerttypeController {
	@RequestMapping("index")
	public String index() {
		return "certtype/index";
	}
}
