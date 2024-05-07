package com.guesser.guesser;

import Flag.modele.Ipartie;
import Flag.modele.Partie;
import Flag.modele.Resultat;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.Normalizer;


@RestController
public class VerificationController {

    private final Ipartie Partie;


    public VerificationController(Ipartie Partie){
        this.Partie = Partie;
    }

    public static String removeAccent(String source) {
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }

    @GetMapping("/Verification")
    public Boolean Verif(Model model, HttpSession session,@RequestParam("difficulte")String difficulte, @RequestParam("Code_bon") String Bonne_reponse, @RequestParam("Code_joueur") String reponse_joueur){
        boolean isCorrect;
        Bonne_reponse= removeAccent(Bonne_reponse);
        reponse_joueur = removeAccent(reponse_joueur);
        int Score = (int) session.getAttribute("Score");
        int NB_QUESTION = (int) session.getAttribute("Nombre_Question");
        System.out.println("La bonne rep est "+Bonne_reponse);
        System.out.println("rep joueur"+reponse_joueur);
        System.out.println("Score" +Score);
        System.out.println("nb question "+NB_QUESTION);
        Resultat verification = Partie.Verification(Bonne_reponse,reponse_joueur,Score,NB_QUESTION);


        session.setAttribute("Nombre_Question",verification.nb_questions);
        session.setAttribute("Score", verification.score);
        return verification.estCorrect;

    }


}
