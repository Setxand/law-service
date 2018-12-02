package com.lawfirm.model.lawProject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ServiceTitle {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	@Column(length = 1024)
	private String content;
	private String image;

}
