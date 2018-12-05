package com.lawfirm.model.lawProject;

import com.lawfirm.reposiory.EditableComponents;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class EditableComponent {

	@Id
	@GeneratedValue
	private Long id;
	@Enumerated(EnumType.STRING)
	private EditableComponents componentKey;
	private String value;

	public EditableComponent(EditableComponents componentKey, String value) {
		this.componentKey = componentKey;
		this.value = value;
	}
}
