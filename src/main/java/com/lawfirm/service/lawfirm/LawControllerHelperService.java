package com.lawfirm.service.lawfirm;

import com.lawfirm.model.lawProject.EditableComponent;
import com.lawfirm.repo.EditableComponentRepository;
import com.lawfirm.repo.ServiceTitleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class LawControllerHelperService {

	@Autowired ServiceTitleRepo serviceTitleRepo;
	@Autowired EditableComponentRepository editableComponentRepo;

	public Model helpParseModelForIndex(Model model) {
		model.addAttribute("titleText", editableComponentRepo.findByComponentKey("TITLE").getValue());

		model.addAttribute("services", serviceTitleRepo.findAll());
		return model;
	}
}
