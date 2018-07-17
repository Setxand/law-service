package com.lawfirm.service.lawfirm;

import com.lawfirm.repo.ServiceTitleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class LawControllerHelperService {

	@Autowired ServiceTitleRepo serviceTitleRepo;

	public Model helpParseModelForIndex(Model model) {
		model.addAttribute("services",serviceTitleRepo.findAll());
		return model;
	}
}
