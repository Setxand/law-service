package com.lawfirm.repo;

import com.lawfirm.model.lawProject.EditableComponent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditableComponentRepository extends JpaRepository<EditableComponent, Long> {

	EditableComponent findByComponentKey(String key);

}
