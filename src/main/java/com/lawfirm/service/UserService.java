package com.lawfirm.service;

import com.lawfirm.exception.BotException;
import com.lawfirm.model.telegram.User;
import com.lawfirm.reposiory.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepo;

	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public User getUser(Integer chatId) {
		return userRepo.findById(chatId).orElseThrow(() -> new BotException(chatId, "Invalid chat ID"));
	}

	public User getOrNew(Integer chatId) {
		return userRepo.findById(chatId).orElseGet(User::new);
	}

	public void save(User user) {
		userRepo.saveAndFlush(user);
	}
}
