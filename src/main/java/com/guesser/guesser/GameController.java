package com.guesser.guesser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    @GetMapping("/bonne_reponse")
    public String bonneReponse() {
        return "bonne_reponse"; // Le nom de la vue pour la page "Bonne réponse"
    }
}

