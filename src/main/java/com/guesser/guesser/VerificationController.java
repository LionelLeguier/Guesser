package com.guesser.guesser;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.Normalizer;


@RestController
public class VerificationController {

    public static String removeAccent(String source) {
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }

    @GetMapping("/Verification")
    public Boolean Verif(Model model, HttpSession session,@RequestParam("difficulte")String difficulte, @RequestParam("Code_bon") String Bonne_reponse, @RequestParam("Code_joueur") String reponse_joueur){


        if (difficulte.equals("difficile")){
            Bonne_reponse= removeAccent(Bonne_reponse);
            if(Bonne_reponse.equalsIgnoreCase(reponse_joueur)) {
                int Score = (int) session.getAttribute("Score");
                int NB_QUESTION = (int) session.getAttribute("Nombre_Question");
                NB_QUESTION++;
                Score++;
                session.setAttribute("Nombre_Question",NB_QUESTION);
                session.setAttribute("Score", Score);
                System.out.println("C'est bon !!");
                System.out.println("Score : " + session.getAttribute("Score"));
                return true;
            }else{
                int NB_QUESTION = (int) session.getAttribute("Nombre_Question");
                NB_QUESTION++;
                session.setAttribute("Nombre_Question",NB_QUESTION);
                System.out.println("C'est mauvais");
                System.out.println("Score : "+session.getAttribute("Score"));
                return false;
            }
        }
        else if(Bonne_reponse.equals(reponse_joueur)){
            int Score = (int) session.getAttribute("Score");
            int NB_QUESTION = (int) session.getAttribute("Nombre_Question");
            NB_QUESTION++;
            Score++;
            session.setAttribute("Nombre_Question",NB_QUESTION);
            session.setAttribute("Score",Score);
            System.out.println("C'est bon !!");
            System.out.println("Score : "+session.getAttribute("Score"));
            return true;
        }else{
            int NB_QUESTION = (int) session.getAttribute("Nombre_Question");
            NB_QUESTION++;
            session.setAttribute("Nombre_Question",NB_QUESTION);
            System.out.println("C'est mauvais");
            System.out.println("Score : "+session.getAttribute("Score"));
            return false;
        }
    }
}
