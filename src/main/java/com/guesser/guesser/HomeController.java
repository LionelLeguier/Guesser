package com.guesser.guesser;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "accueil"; // Renvoie la page de choix de la difficulté
    }

    @PostMapping("/start-game")
    public String startGame(@RequestParam("difficulte") String difficulte, HttpSession session) {
        // Enregistrer la difficulté choisie dans la session
        session.setAttribute("difficulte", difficulte);
        // Rediriger vers la page de jeu
        return "redirect:/jeu";
    }
}
