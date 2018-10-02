package com.lawfirm.model.lawProject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EditableComponent {

	@Id
	@GeneratedValue
	private Long id;
	private String componentKey;
	private String value;

	public EditableComponent(String componentKey, String value) {
		this.componentKey = componentKey;
		this.value = value;
	}
}
