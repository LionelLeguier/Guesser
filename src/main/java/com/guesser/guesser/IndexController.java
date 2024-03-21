package com.guesser.guesser;

import Flag.modele.IpropFlag;
import Flag.modele.Iquizz;
import Flag.modele.Pays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;


@Controller
@RequestMapping("/toto")
public class IndexController {
    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    public Iquizz Quizz;
    @Autowired
    public IpropFlag proposition;


    @GetMapping
    public String home(final Model model) {
        Pays question = Quizz.Question();
        ArrayList<Pays> p = proposition.Proposition_Drapeau("fr");
        System.out.println("###########################");
        for (int i =0;i<p.size();i++){

            System.out.println("le nom est "+p.get(i).getNom());
            System.out.println(" Le code est "+p.get(i).getCode());
            System.out.println(" Le drapeau est "+p.get(i).getDrapeau());
            System.out.println("------------------------");
        }
        System.out.println("Reponse dans le controlleur " + question.getNom() + " Et le code du pays "+question.getCode() +" et l'URL du pays : "+question.getDrapeau());
        model.addAttribute("appName", appName);
        //model.addAttribute("Question",question);

        return "index";
    }
}
