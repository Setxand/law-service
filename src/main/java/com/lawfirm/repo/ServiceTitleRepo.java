package com.lawfirm.repo;

import com.lawfirm.models.ServiceTitle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTitleRepo extends JpaRepository<ServiceTitle, Long> {
}
