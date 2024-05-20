package com.guesser.guesser;
import Flag.modele.Ipartie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;


@Controller
public class HomeController {
    @Autowired
    private Ipartie partie;
    private ArrayList<String> AA = new ArrayList<>();

    @GetMapping("/reprendre-partie")
    public String ReprendrePartie(HttpSession session) {
        return "reprendrePartie"; // Renvoie la page de choix de la difficulté
    }
    @GetMapping("/")
    public String Begin(HttpSession session) {

        return "debutJeu";
    }
    @GetMapping("/nouvelle-partie")
    public String home(HttpSession session) {
        session.setAttribute("Nombre_Question",0);
        if(session.getAttribute("Score") != null){
            session.setAttribute("Score",0);
        }
        return "accueil"; // Renvoie la page de choix de la difficulté
    }



    @PostMapping("/start-game")
    public String startGame(@RequestParam("difficulte") String difficulte, HttpSession session) {
        // Enregistrer la difficulté choisie dans la session
        session.setAttribute("difficulte", difficulte);
        session.setAttribute("Nombre_Question",0);
        // Rediriger vers la page de jeu
        return "redirect:/jeu";
    }
}
