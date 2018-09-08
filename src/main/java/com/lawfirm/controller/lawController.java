package com.lawfirm.controller;

import com.lawfirm.service.lawfirm.LawControllerHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lawfirm")
public class LawController {

	@Autowired LawControllerHelperService lawControllerHelperService;

	@GetMapping
	public String index(Model model) {
		lawControllerHelperService.helpParseModelForIndex(model);
		return "index";
	}
}
