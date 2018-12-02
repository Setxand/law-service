package com.lawfirm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServiceBodyDTO {

	public String title;
	public String body;
	public String image;

}
