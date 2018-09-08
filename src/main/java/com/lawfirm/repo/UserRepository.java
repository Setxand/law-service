package com.lawfirm.repo;

import com.lawfirm.model.telegram.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByChatId(Integer chatId);


}
