package com.lawfirm.service;

import com.lawfirm.model.telegram.User;
import com.lawfirm.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepo;

	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public User getUser(Integer chatId) {
		return userRepo.findByChatId(chatId).orElseThrow(() -> new RuntimeException("Invalid chat ID"));
	}

	public User getOrNew(Integer chatId) {
		return userRepo.findByChatId(chatId).orElseGet(() -> new User());
	}

	public void save(User user) {
		userRepo.saveAndFlush(user);
	}
}
