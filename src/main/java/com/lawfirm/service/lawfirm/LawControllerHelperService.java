package com.lawfirm.service.lawfirm;

import com.lawfirm.model.lawProject.EditableComponent;
import com.lawfirm.reposiory.EditableComponentRepository;
import com.lawfirm.reposiory.ServiceTitleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class LawControllerHelperService {

	@Autowired ServiceTitleRepo serviceTitleRepo;
	@Autowired EditableComponentRepository editableComponentRepo;

	public Model helpParseModelForIndex(Model model) {
		EditableComponent editableComponent = editableComponentRepo.findByComponentKey("TITLE")
				.orElseGet(() -> new EditableComponent("TITLE", "empty title!"));

		model.addAttribute("titleText", editableComponent.getValue());

		model.addAttribute("services", serviceTitleRepo.findAll());
		return model;
	}
}
