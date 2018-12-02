package com.lawfirm.controller;

import com.lawfirm.dto.ServiceBodyDTO;
import com.lawfirm.service.lawfirm.LawControllerHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LawController {

	@Autowired LawControllerHelperService lawControllerHelperService;

	@GetMapping("/lawfirm")
	public String index(Model model) {
		lawControllerHelperService.helpParseModelForIndex(model);
		return "index";
	}

	@GetMapping("/lawfirm/users/{titleId}/service-bodies")
	@ResponseBody
	public ServiceBodyDTO getServiceBody(@PathVariable String titleId) {
		return lawControllerHelperService.helpLoadServiceBody(titleId);
	}
}
