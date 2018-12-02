package com.lawfirm.reposiory;

import com.lawfirm.model.telegram.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {


}
