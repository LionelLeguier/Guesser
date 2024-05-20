package com.guesser.guesser;

import Flag.modele.Ipartie;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class PauseController {


    private final Ipartie Partie;

    public PauseController(Ipartie partie) {
        Partie = partie;
    }

    @GetMapping("/pause")
    public String  Pause(final Model model, HttpSession session){
        String id=null;
        id = Partie.Pause(
                (int)session.getAttribute("Score"),
                (int)session.getAttribute("Nombre_Question"),
                (ArrayList<String>) session.getAttribute("liste_pays_sortie"),
                (String) session.getAttribute("difficulte"));
        System.out.println("voici l'id "+id);
        model.addAttribute("Id",id);
        return "pause";
    }
}
