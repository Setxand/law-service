package com.lawfirm.service.lawfirm;

import com.lawfirm.exception.BotException;
import com.lawfirm.model.lawProject.EditableComponent;
import com.lawfirm.model.lawProject.ServiceBody;
import com.lawfirm.reposiory.EditableComponentRepository;
import com.lawfirm.reposiory.ServiceBodyRepo;
import com.lawfirm.reposiory.ServiceTitleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Service
public class LawControllerHelperService {

	@Autowired ServiceTitleRepo serviceTitleRepo;
	@Autowired EditableComponentRepository editableComponentRepo;
	@Autowired ServiceBodyRepo serviceBodyRepo;

	public Model helpParseModelForIndex(Model model) {
		EditableComponent editableComponent = editableComponentRepo.findByComponentKey("TITLE")
				.orElseGet(() -> new EditableComponent("TITLE", "empty title!"));

		model.addAttribute("titleText", editableComponent.getValue());

		model.addAttribute("services", serviceTitleRepo.findAll());
		return model;
	}

    public void helpLoadServiceBody(String titleId, ModelAndView model) {
		ServiceBody serviceBody = serviceBodyRepo
				.findById(Long.parseLong(titleId)).orElseThrow(() -> new IllegalArgumentException("Invalid title ID"));

		model.addObject("serviceBody", serviceBody);
		model.setViewName("serviceBody");
    }
}
