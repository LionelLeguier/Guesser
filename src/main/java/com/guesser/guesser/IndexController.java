package com.guesser.guesser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/toto")
public class IndexController {
    @Value("${spring.application.name}")
    private String appName;

    @GetMapping
    public String home(final Model model) {
        model.addAttribute("appName", appName);
        return "index";
    }
}
