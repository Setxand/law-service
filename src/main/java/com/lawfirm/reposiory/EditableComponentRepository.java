package com.lawfirm.reposiory;

import com.lawfirm.model.lawProject.EditableComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EditableComponentRepository extends JpaRepository<EditableComponent, Long> {

	Optional<EditableComponent> findByComponentKey(EditableComponents key);

}
