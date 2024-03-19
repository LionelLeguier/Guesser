package com.guesser.guesser;

import Flag.modele.Iquizz;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    public Iquizz Quizz;

    @GetMapping
    public String home(final Model model) {
        Quizz.Question();
        model.addAttribute("appName", appName);
        return "index";
    }
}
