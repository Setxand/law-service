package com.lawfirm.reposiory;

import com.lawfirm.model.lawProject.ServiceBody;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ServiceBodyRepo extends JpaRepository<ServiceBody, Long> {

    Optional<ServiceBody> findTopByOrderByIdDesc();

}
