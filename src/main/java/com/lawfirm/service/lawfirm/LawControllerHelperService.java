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
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

import static com.lawfirm.reposiory.EditableComponents.BACKGROUND_IMAGE;
import static com.lawfirm.reposiory.EditableComponents.TITLE;

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


	//init must be not in this place
	@PostConstruct
	public void init() {
		EditableComponent component = editableComponentRepo.findByComponentKey(BACKGROUND_IMAGE)
				.orElseGet(() -> new EditableComponent(BACKGROUND_IMAGE, ""));

		if (!StringUtils.hasText(component.getValue())) {
			editableComponentRepo.saveAndFlush(component);
		}
	}



	public void helpParseModelForIndex(Model model) {
		// Set to init()
		EditableComponent editableComponent = editableComponentRepo.findByComponentKey(TITLE)
				.orElseGet(() -> new EditableComponent(TITLE, "empty title!"));

		EditableComponent background = getBackGroundImage();

		model.addAttribute("titleText", editableComponent.getValue());
		model.addAttribute("services", serviceTitleRepo.findAll());
		model.addAttribute(BACKGROUND_IMAGE.name(), background.getValue());
	}

	public EditableComponent getBackGroundImage() {
		return editableComponentRepo.findByComponentKey(BACKGROUND_IMAGE).get();
	}

	public ServiceBodyDTO helpLoadServiceBody(String titleId) {
		ServiceBody serviceBody = serviceBodyRepo
				.findById(Long.parseLong(titleId))
				.orElseThrow(() -> new IllegalArgumentException("Invalid title ID"));
		ServiceBodyDTO dto = DtoUtils.serviceBody(serviceBody);
		return dto;
	}
}
