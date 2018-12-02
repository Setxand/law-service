package com.lawfirm.controller;

import com.lawfirm.dto.telegram.Update;
import com.lawfirm.service.telegram.UpdateParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
public class AdminController {

	@Autowired UpdateParserService updateParserService;

	@PostMapping
	public void getUpdate(@RequestBody Update update) {
		updateParserService.parseUpdate(update);
	}
}
