package com.lawfirm.service.lawfirm;

import com.lawfirm.dto.ServiceBodyDTO;
import com.lawfirm.model.lawProject.EditableComponent;
import com.lawfirm.model.lawProject.ServiceBody;
import com.lawfirm.reposiory.EditableComponentRepository;
import com.lawfirm.reposiory.ServiceBodyRepo;
import com.lawfirm.reposiory.ServiceTitleRepo;
import com.lawfirm.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class LawControllerHelperService {

	private final ServiceTitleRepo serviceTitleRepo;
	private final EditableComponentRepository editableComponentRepo;
	private final ServiceBodyRepo serviceBodyRepo;

	@Autowired
	public LawControllerHelperService(ServiceTitleRepo serviceTitleRepo, EditableComponentRepository editableComponentRepo, ServiceBodyRepo serviceBodyRepo) {
		this.serviceTitleRepo = serviceTitleRepo;
		this.editableComponentRepo = editableComponentRepo;
		this.serviceBodyRepo = serviceBodyRepo;
	}

	public void helpParseModelForIndex(Model model) {
		EditableComponent editableComponent = editableComponentRepo.findByComponentKey("TITLE")
				.orElseGet(() -> new EditableComponent("TITLE", "empty title!"));

		model.addAttribute("titleText", editableComponent.getValue());

		model.addAttribute("services", serviceTitleRepo.findAll());
	}

	public ServiceBodyDTO helpLoadServiceBody(String titleId) {
		ServiceBody serviceBody = serviceBodyRepo
				.findById(Long.parseLong(titleId))
				.orElseThrow(() -> new IllegalArgumentException("Invalid title ID"));
		ServiceBodyDTO dto = DtoUtils.serviceBody(serviceBody);
		return dto;
	}
}
