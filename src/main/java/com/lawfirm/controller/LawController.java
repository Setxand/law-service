package com.lawfirm.controller;

import com.lawfirm.dto.ServiceBodyDTO;
import com.lawfirm.service.lawfirm.LawControllerHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LawController {

	@Autowired LawControllerHelperService lawControllerHelperService;

	@GetMapping("/lawfirm")
	public String index(Model model) {
		lawControllerHelperService.helpParseModelForIndex(model);
		return "index";
	}

	@GetMapping("/lawfirm/users/{titleId}/service-bodies")
	public ModelAndView getServiceBody(@PathVariable String titleId, ModelAndView modelAndView) {
		ServiceBodyDTO dto = lawControllerHelperService.helpLoadServiceBody(titleId);
		modelAndView.addObject("serviceBody", dto);
		modelAndView.setViewName("serviceBody");
		return modelAndView;
	}
}
