package com.lawfirm.controller;

import com.lawfirm.service.lawfirm.LawControllerHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import static com.lawfirm.reposiory.EditableComponents.BACKGROUND_IMAGE;

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
		modelAndView.addObject("serviceBody", lawControllerHelperService.helpLoadServiceBody(titleId));
		modelAndView.addObject(BACKGROUND_IMAGE.name(), lawControllerHelperService.getBackGroundImage().getValue());
		modelAndView.setViewName("serviceBody");
		return modelAndView;
	}
}
