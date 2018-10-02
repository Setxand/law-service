package com.lawfirm.reposiory;

import com.lawfirm.model.lawProject.ServiceTitle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTitleRepo extends JpaRepository<ServiceTitle, Long> {

    ServiceTitle findTopByOrderByIdDesc();

}
