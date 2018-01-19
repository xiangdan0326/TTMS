package cn.tedu.ttms.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/type/")
@Controller
public class typeController {
	
	@RequestMapping("listUI")
	public String listUI() {
		return "product/type_list";
	}
	@RequestMapping("editUI")
	public String editUI() {
		return "product/type_edit";
	}
}
