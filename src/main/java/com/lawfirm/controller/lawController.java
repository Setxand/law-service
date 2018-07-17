package com.lawfirm.controller;

import com.lawfirm.service.lawfirm.LawControllerHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class lawController {
    @Autowired LawControllerHelperService lawControllerHelperService;

    @GetMapping
    public String index(Model model){
        model = lawControllerHelperService.helpParseModelForIndex(model);
        return "index";
    }
}
