package com.lawfirm.utils;

import com.lawfirm.dto.ServiceBodyDTO;
import com.lawfirm.model.lawProject.ServiceBody;

public class DtoUtils {

	public static ServiceBodyDTO serviceBody(ServiceBody entity) {
		ServiceBodyDTO dto = new ServiceBodyDTO();
		dto.body = entity.getBody();
		dto.title = entity.getTitle();
		dto.image = entity.getImage();

		return dto;
	}

}
