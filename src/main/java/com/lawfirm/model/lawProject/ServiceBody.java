package com.lawfirm.model.lawProject;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class ServiceBody {
	@Id
	private Long id;
	private String title;
	@Column(length = 10000)
	private String body;
	private String image;
}
