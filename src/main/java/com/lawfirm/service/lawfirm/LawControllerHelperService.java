package com.lawfirm.service.lawfirm;

import com.lawfirm.dto.ServiceBodyDTO;
import com.lawfirm.exception.BotException;
import com.lawfirm.model.lawProject.EditableComponent;
import com.lawfirm.model.lawProject.ServiceBody;
import com.lawfirm.reposiory.EditableComponentRepository;
import com.lawfirm.reposiory.ServiceBodyRepo;
import com.lawfirm.reposiory.ServiceTitleRepo;
import com.lawfirm.utils.DtoUtils;
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

    public ServiceBodyDTO helpLoadServiceBody(String titleId) {
		ServiceBody serviceBody =  serviceBodyRepo
				.findById(Long.parseLong(titleId))
													.orElseThrow(() -> new IllegalArgumentException("Invalid title ID"));
		ServiceBodyDTO dto =  DtoUtils.serviceBody(serviceBody);
		return dto;
    }
}
